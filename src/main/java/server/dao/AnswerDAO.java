package server.dao;

import org.hibernate.Session;
import server.questions.Answer;

import java.util.ArrayList;

public class AnswerDAO {
    public static ArrayList<Answer> allAnswersOfQuestion(int id){
        ArrayList<Answer> answers = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            answers = (ArrayList<Answer>) session.createQuery("from Answer where question_id = :id").setParameter("id", id).list();
            session.getTransaction().commit();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        return answers;
    }
}
