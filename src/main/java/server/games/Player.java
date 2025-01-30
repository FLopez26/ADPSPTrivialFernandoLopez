package server.games;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;

import java.time.Instant;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Player {
    @Id
    private String name;
    private String pass;
    private int maxScore;
    @CurrentTimestamp(event = EventType.INSERT)
    private Instant registrationDate;
    @OneToMany(mappedBy = "player")
    private List<Game> games;

    public Player(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    /**
     * Comprueba si el jugador ha superado su puntuación máxima, si es así la actualiza.
     * @param points int
     * @return 'true' si se ha actualizado la puntuación máxima, 'false' en caso contrario.
     */
    public boolean updateMaxScore(int points){
        if(points > maxScore){
            maxScore = points;
            return true;
        }
        return false;
    }
}
