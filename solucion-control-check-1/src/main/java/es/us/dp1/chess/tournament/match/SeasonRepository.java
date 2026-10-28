package es.us.dp1.chess.tournament.match;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SeasonRepository extends CrudRepository<Season, Integer> {

    List<Season> findAll();

    @Query("SELECT r FROM Season s JOIN s.ratings r WHERE s.id = :seasonId ORDER BY r.elo DESC")
    List<Rating> findStandings(@Param("seasonId") Integer seasonId);
}
