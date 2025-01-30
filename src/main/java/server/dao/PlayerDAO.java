package server.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import server.games.Player;

import static server.dao.HibernateUtil.getSessionFactory;

public class PlayerDAO {
    /**
     * Crea un nuevo jugador en la base de datos. El nuevo jugador debe ser pasado como parámetro.
     * @param player Player
     */
    public static void create(Player player) {
        try (Session session = getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.persist(player);
            session.getTransaction().commit();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Devuelve un jugador de la base de datos con el nombre y contraseña pasados como parámetros.
     * @param name
     * @param pass
     * @return Player
     */
    public static Player getPlayer(String name, String pass) {
        try(Session session = getSessionFactory().openSession()){
            String hql = "select p from Player p where p.name = :name and p.pass = :pass";
            Query<Player> q = session.createQuery(hql, Player.class);
            q.setParameter("name", name);
            q.setParameter("pass", pass);
            return q.getSingleResult();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Comprueba si un jugador con el nombre y contraseña pasados como parámetros existe en la base de datos.
     * @param name
     * @param pass
     * @return boolean
     */
    public static boolean checkPlayer(String name, String pass) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "select p from Player p where p.name = :name and p.pass = :pass";
            Query<Player> q = session.createQuery(hql, Player.class);
            q.setParameter("name", name);
            q.setParameter("pass", pass);
            return !q.getResultList().isEmpty();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve el 'maxScore' de un jugador pasado como parámetro.
     * @return int
     */
    public static int getMaxScore(Player player){
        try(Session session = getSessionFactory().openSession()){
            String hql = "select p.maxScore from Player p where p.name = :name";
            Query<Integer> q = session.createQuery(hql, Integer.class);
            q.setParameter("name", player.getName());
            return q.getSingleResult();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Actualiza el 'maxScore' de un jugador pasado como parámetro.
     * @param points
     * @param player
     */
    public static void updateMaxScore(int points, Player player) {
        try (Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            player.setMaxScore(points);
            session.merge(player);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}