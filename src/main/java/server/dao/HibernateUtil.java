package server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import server.questions.Answer;
import server.questions.Category;
import server.questions.Question;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try (StandardServiceRegistry registry = new
                    StandardServiceRegistryBuilder().build();) {
                sessionFactory = new MetadataSources(registry)
                        .addAnnotatedClass(Category.class)
                        .addAnnotatedClass(Answer.class)
                        .addAnnotatedClass(Question.class)
                        .buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
