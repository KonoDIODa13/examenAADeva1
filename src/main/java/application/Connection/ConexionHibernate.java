package application.Connection;

import application.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ConexionHibernate {
    static SessionFactory factory = null;
    static Session session = null;

    // Realizo la conexión con la BD.
    public static void conexion() {
        factory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();
    }

    // Desconecto de la BD
    public static void desconectar() {
        factory.close();
        session.close();
    }

    //retorno la sesionFactory
    public static SessionFactory getFactory() {
        return factory;
    }

    //retorno la session
    public static Session getSession() {
        return session;
    }
}