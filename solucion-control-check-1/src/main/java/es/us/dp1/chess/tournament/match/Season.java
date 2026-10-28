package es.us.dp1.chess.tournament.match;

import java.time.LocalDate;
import java.util.List;

import es.us.dp1.chess.tournament.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Season extends BaseEntity {

    @NotBlank
    @Size(min = 3, max = 50)
    String name;

    @NotNull
    LocalDate startDate;

    @NotNull
    LocalDate endDate;

    // Season (0..1) -> ChessMatch (0..n), unidireccional: la FK queda en chess_match
    @OneToMany
    @JoinColumn(name = "season_id")
    List<ChessMatch> matches;

    // Season (1) -> Rating (0..n), unidireccional: la FK queda en rating
    @OneToMany
    @JoinColumn(name = "season_id")
    List<Rating> ratings;
}
