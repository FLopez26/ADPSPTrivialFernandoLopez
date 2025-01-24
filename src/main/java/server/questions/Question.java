package server.questions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
    private int numCorrect;
    private int numFailure;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question(String question, int numCorrect, int numFailure, Category category, List<Answer> answers) {
        this.question = question;
        this.numCorrect = numCorrect;
        this.numFailure = numFailure;
        this.category = category;
        this.answers = answers;
    }
}
