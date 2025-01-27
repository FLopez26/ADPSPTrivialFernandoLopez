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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean comprobarPlayer(String name, String pass) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "select p from Player p where p.name = :name and p.pass = :pass";
            Query q = session.createQuery(hql, Player.class);
            q.setParameter("name", name);
            q.setParameter("pass", pass);
            return q.getResultList().size() > 0;
        }
    }
}