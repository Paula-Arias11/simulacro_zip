package es.us.dp1.chess.tournament.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.us.dp1.chess.tournament.model.BaseEntity;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rating extends BaseEntity {

    @NotNull
    @Min(100)
    Integer elo;

    @NotNull
    @Min(0)
    Integer wins;

    @NotNull
    @Min(0)
    Integer losses;

    @NotNull
    @Min(0)
    Integer draws;

    // Atributo derivado /winRate: volátil, no se persiste
    @Transient
    public Double getWinRate() {
        if (wins == null || losses == null) {
            return 0.0;
        }
        int total = wins + losses;
        if (total == 0) {
            return 0.0;
        }
        return (double) wins / total;
    }

    // Rating (0..n) -> User (1): obligatorio
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id")
    @JsonIgnoreProperties("password")
    User player;
}
