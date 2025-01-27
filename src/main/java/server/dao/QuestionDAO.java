package server.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import server.questions.Category;
import server.questions.Question;

import java.util.ArrayList;

public class QuestionDAO {

    public static ArrayList<Question> getSixQuestions(){
        ArrayList<Question> questions = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Category artAndLiterature = (Category) session.createQuery("from Category where name = 'Art and Literature'").uniqueResult();
            Category entertainment = (Category) session.createQuery("from Category where name = 'Entertainment'").uniqueResult();
            Category geography = (Category) session.createQuery("from Category where name = 'Geography'").uniqueResult();
            Category history = (Category) session.createQuery("from Category where name = 'History'").uniqueResult();
            Category scienceAndNature = (Category) session.createQuery("from Category where name = 'Science and Nature'").uniqueResult();
            Category sportsAndLeisure = (Category) session.createQuery("from Category where name = 'Sports and Leisure'").uniqueResult();

            questions.add((Question) session.createQuery("from Question where category = :category order by rand()").setParameter("category", artAndLiterature).setMaxResults(1).uniqueResult());
            questions.add((Question) session.createQuery("from Question where category = :category order by rand()").setParameter("category", entertainment).setMaxResults(1).uniqueResult());
            questions.add((Question) session.createQuery("from Question where category = :category order by rand()").setParameter("category", geography).setMaxResults(1).uniqueResult());
            questions.add((Question) session.createQuery("from Question where category = :category order by rand()").setParameter("category", history).setMaxResults(1).uniqueResult());
            questions.add((Question) session.createQuery("from Question where category = :category order by rand()").setParameter("category", scienceAndNature).setMaxResults(1).uniqueResult());
            questions.add((Question) session.createQuery("from Question where category = :category order by rand()").setParameter("category", sportsAndLeisure).setMaxResults(1).uniqueResult());

            for (Question question : questions) {
                Hibernate.initialize(question.getAnswers());
            }

            session.getTransaction().commit();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        return questions;
    }
}
