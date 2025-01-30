package server.dao;

import org.hibernate.Session;
import server.questions.Answer;

import java.util.ArrayList;
import java.util.Collections;

public class AnswerDAO {
    /**
     * Devuelve todas las respuestas de una pregunta
     * @param id int
     * @return ArrayList<Answer>
     */
    public static ArrayList<Answer> allAnswersOfQuestion(int id) {
        ArrayList<Answer> answers = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            answers = (ArrayList<Answer>) session.createQuery("from Answer where question.id = :id").setParameter("id", id).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        //Se mezclan las respuestas para que no siempre estén en el mismo orden
        Collections.shuffle(answers);
        return answers;
    }
}
