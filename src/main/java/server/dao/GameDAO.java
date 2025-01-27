package server.dao;

import org.hibernate.Session;
import server.games.Game;

public class GameDAO {
    public static void create(Game game){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            session.getTransaction().begin();
            session.persist(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void eliminarTodo(){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            session.getTransaction().begin();
            session.createQuery("delete from Game").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
