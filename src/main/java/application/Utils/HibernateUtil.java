package application.Utils;

import application.Model.Equipo;
import application.Model.Jugador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory factory = null;

    static {
        Configuration cfg = new Configuration();
        cfg.configure(R.getCfg("hibernate.cfg.xml"));
        cfg.addAnnotatedClass(Equipo.class);
        cfg.addAnnotatedClass(Jugador.class);
        factory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }

}
