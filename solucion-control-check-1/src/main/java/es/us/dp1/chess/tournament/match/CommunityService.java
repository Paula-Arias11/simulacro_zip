package es.us.dp1.chess.tournament.match;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.chess.tournament.exceptions.MalformedCommunityException;
import es.us.dp1.chess.tournament.user.User;

@Service
public class CommunityService {
    CommunityRepository repo;

    @Autowired
    public CommunityService(CommunityRepository repo) {
        this.repo = repo;
    }


    @Transactional(readOnly = true)
    public Optional<Community> getCommunityById(Integer id) {
        return repo.findById(id);
    }

    @Transactional(rollbackFor = MalformedCommunityException.class)
    public Community save(Community m) throws MalformedCommunityException {
        List<User> members = m.getMembers() == null ? List.of() : m.getMembers();

        // Regla 2: no se supera el maximo de jugadores
        if (m.getMaxPlayers() != null && members.size() > m.getMaxPlayers()) {
            throw new MalformedCommunityException(
                    "The community has more members than the maximum allowed (" + m.getMaxPlayers() + ")");
        }

        // Regla 1: cada miembro ha jugado al menos 2 partidas de las temporadas de la comunidad
        List<ChessMatch> matches = m.getSeasons() == null ? List.of()
                : m.getSeasons().stream()
                        .filter(s -> s.getMatches() != null)
                        .flatMap(s -> s.getMatches().stream())
                        .toList();

        for (User member : members) {
            long played = matches.stream()
                    .filter(cm -> sameUser(cm.getWhitePlayer(), member) || sameUser(cm.getBlackPlayer(), member))
                    .count();
            if (played < 2) {
                throw new MalformedCommunityException(
                        "Member " + member.getUsername() + " has played fewer than 2 matches in the seasons of the community");
            }
        }
        return repo.save(m);
    }

    private boolean sameUser(User a, User b) {
        if (a == null || b == null) {
            return false;
        }
        return a == b || (a.getId() != null && a.getId().equals(b.getId()));
    }

    @Transactional(readOnly = true)
    public List<Community> getCommunities() {
        return repo.findAll();
    }    
    }
