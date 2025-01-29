package server.games;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;
    @CurrentTimestamp(event = {EventType.INSERT, EventType.UPDATE})
    private LocalDateTime date;
    @ManyToOne
    private Player player;

    public Game(int score, Player player) {
        this.score = score;
        this.player = player;
    }

    @Override
    public String toString() {
        return "Partida " + id + " --> " +
                "  Puntuación: " + score +
                "  ||  Fecha: " + date +
                "  ||  Jugador: " + player.getName();
    }
}
