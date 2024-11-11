package application.DAO;

import application.Model.Equipo;

import java.util.List;

public interface EquipoDAOImpl {

    void conectarBD();

    void insertarEquipo(Equipo equipo);

    void modificarEquipo(Equipo equipo, Boolean estado);

    void eliminarEquipo(Equipo equipo);

    List<Equipo> listarEquipos();

    Equipo getEquipo(int idEquipo);
}
