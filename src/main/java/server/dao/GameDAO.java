package server.dao;

import org.hibernate.Session;
import server.games.Game;
import server.games.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public static ArrayList<Game> verTodo(){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            return (ArrayList<Game>) session.createQuery("from Game", Game.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Game> top10(){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            return (ArrayList<Game>) session.createQuery("from Game order by score desc", Game.class)
                    .setMaxResults(10)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static int gamesPerPlayer(Player player){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            return session.createQuery("from Game where player = :player", Game.class)
                    .setParameter("player", player)
                    .getResultList().size();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }
}
