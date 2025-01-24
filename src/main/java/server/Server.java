package server;

import server.dao.HibernateUtil;

public class Server {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
    }
}