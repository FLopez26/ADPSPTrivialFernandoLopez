package server.dao;

import org.hibernate.Session;
import server.games.Game;
import server.games.Player;

import java.util.ArrayList;

public class GameDAO {
    /**
     * Crea una nueva partida en la base de datos. La nueva partida debe ser pasada como parámetro.
     * @param game Game
     */
    public static void create(Game game){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            session.getTransaction().begin();
            session.persist(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina todas las partidas de la base de datos.
     */
    public static void eliminarTodo(){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            session.getTransaction().begin();
            session.createQuery("delete from Game").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Devuelve todas las partidas de la base de datos.
     * @return ArrayList<Game>
     */
    public static ArrayList<Game> verTodo(){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            return (ArrayList<Game>) session.createQuery("from Game", Game.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve las 10 partidas con mayor puntuación de la base de datos.
     * @return ArrayList<Game>
     */
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

    /**
     * Devuelve el número de partidas jugadas por un jugador, el jugador es pasado por parámetro.
     * @param player Player
     * @return int
     */
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
