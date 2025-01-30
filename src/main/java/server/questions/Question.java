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

    /**
     * Devuelve la opción correcta de la pregunta
     * @return String
     */
    public String getCorrectOption(){
        String correctOption = "";
        for(Answer answer: answers){
            if(answer.getCorrect() == 1){
                correctOption = answer.getAnswer();
            }
        }
        return correctOption;
    }

    @Override
    public String toString(){
        return "Pregunta de " + category.getName() + " --> " +
                question +
                "  ||  Aciertos: " + numCorrect +
                "  ||  Fallos: " + numFailure;
    }
}
