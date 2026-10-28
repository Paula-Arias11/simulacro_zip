package es.us.dp1.chess.tournament.match;

import java.util.List;

import es.us.dp1.chess.tournament.model.NamedEntity;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Community extends NamedEntity {

    String location;
    Integer maxPlayers;

    @ManyToMany
    List<User> members;

    // Community (1) -> Season (1..n), unidireccional: la FK queda en season
    @NotEmpty
    @OneToMany
    @JoinColumn(name = "community_id")
    List<Season> seasons;
    
}
