package server.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import server.games.Player;

import static server.dao.HibernateUtil.getSessionFactory;

public class PlayerDAO {
    public static void create(Player player) {
        try (Session session = getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.persist(player);
            session.getTransaction().commit();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static Player getPlayer(String name, String password) {
        try(Session session = getSessionFactory().openSession()){
            String hql = "select p from Player p where p.name = :name and p.pass = :pass";
            Query<Player> q = session.createQuery(hql, Player.class);
            q.setParameter("name", name);
            q.setParameter("pass", password);
            return q.getSingleResult();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

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

    public static int getMaxScore(){
        try(Session session = getSessionFactory().openSession()){
            String hql = "select max(p.maxScore) from Player p";
            Query<Integer> q = session.createQuery(hql, Integer.class);
            return q.getSingleResult();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

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