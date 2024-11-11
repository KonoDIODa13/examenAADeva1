package application.DAO;

import application.Connection.ConexionHibernate;
import application.Model.Equipo;
import application.Model.Jugador;
import application.Utils.AlertUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class JugadorDAO implements JugadorDAOImpl {
    private SessionFactory factory;
    private Session session;

    public JugadorDAO() {
        conectarBD();
    }

    @Override
    public void conectarBD() { // solo conexion Hibernate
        ConexionHibernate.conexion();
        factory = ConexionHibernate.getFactory();
        session = ConexionHibernate.getSession();
    }


    @Override
    public void modificarEquipo(Jugador jugador, int idEquipo) { //hibernate solo
        try {
            EquipoDAO equipoDAO = new EquipoDAO();
            Equipo equipo = equipoDAO.getEquipo(idEquipo);
            if (equipo != null) {
                jugador.setId_equipo(equipo);
                session.beginTransaction();
                session.update(jugador);
                session.getTransaction().commit();
                AlertUtils.mostrarConfirmacion("Cambio de equipo realizado con exito");
            } else {
                AlertUtils.mostrarError("No existe un equipo con dicho id.");
            }
        } catch (
                Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.clear();
        }
    }

    @Override
    public List<Jugador> listarJugadores(Equipo equipo) { // hibernate solo
        List<Jugador> jugadores = new ArrayList<>();
        try {
            session.beginTransaction();
            jugadores = session.createQuery("from Jugador where id_equipo=:id_equipo", Jugador.class)
                    .setParameter("id_equipo", equipo)
                    .getResultList();
            session.getTransaction().commit();
        } catch (
                Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.clear();
        }
        return jugadores;
    }


    /*
    este metodo lo hice antes de que nos comentaras que lo de los cumpleaños no fuera con todos los jugadores,
    sino con los del equipo.
    public List<Jugador> listarJugadoresTodos() {
        List<Jugador> jugadores = new ArrayList<>();
        try {
            session.beginTransaction();
            jugadores = session.createQuery("from Jugador", Jugador.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (
                Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.clear();
        }
        return jugadores;
    }
    */
}

