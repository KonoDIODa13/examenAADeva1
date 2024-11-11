package application.DAO;

import application.Model.Equipo;
import application.Model.Jugador;

import java.util.List;

public interface JugadorDAOImpl {
    void conectarBD();

    void modificarEquipo(Jugador jugador, int idEquipo);

    List<Jugador> listarJugadores(Equipo equipo);
}
