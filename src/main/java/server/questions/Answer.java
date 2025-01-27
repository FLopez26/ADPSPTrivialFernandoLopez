package server.questions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int correct;
    @ManyToOne
    private Question question;
    private String answer;

    public Answer(int correct, Question question, String answer) {
        this.correct = correct;
        this.question = question;
        this.answer = answer;
    }
}
