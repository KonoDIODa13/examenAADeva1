package application.Controller;

import application.DAO.JugadorDAO;
import application.Model.Equipo;
import application.Model.Jugador;
import application.Utils.AlertUtils;
import application.Utils.CambioEscenas;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JugadoresController {
    public AnchorPane paneJugadores;

    @FXML
    private ListView<Jugador> lvJugadores;

    @FXML
    private TextField tfIdEquipo, tfAlias, tfFechaNacimiento;

    public List<Jugador> jugadores;

    public JugadorDAO dao;

    public Jugador jugadorSeleccionado;


    Equipo equipoSeleccionado;

    public JugadoresController() {
        dao = new JugadorDAO();
    }

    @FXML
    void atras(ActionEvent event) {
        CambioEscenas.cambioEscena("gestionEquipos.fxml", paneJugadores);
    }

    @FXML
    void cambiarDeEquipo(ActionEvent event) {
        if (jugadorSeleccionado != null) {
            int idEquipoNuevo = Integer.parseInt(tfIdEquipo.getText());
            dao.modificarEquipo(jugadorSeleccionado, idEquipoNuevo);
            cargarLista();
            limpiarCampos();
        } else {
            AlertUtils.mostrarError("Seleccione primero el jugador.");
        }

    }

    @FXML
    void cumpleannos(ActionEvent event) {
        List<Jugador> cumpleJugador = new ArrayList<>();
        List<Integer> edades = new ArrayList<>();
        LocalDate fechaHoy = LocalDate.now();
        dao.listarJugadoresTodos().forEach(jugador -> {
                    if (fechaHoy.getDayOfMonth() == jugador.getFechaNacimiento().getDayOfMonth() &&
                            fechaHoy.getMonthValue() == jugador.getFechaNacimiento().getMonthValue()
                    ) {
                        int edad = fechaHoy.getYear() - jugador.getFechaNacimiento().getYear();
                        cumpleJugador.add(jugador);
                        edades.add(edad);
                    }
                }
        );
        if (cumpleJugador.isEmpty()) {
            AlertUtils.mostrarConfirmacion("No hay ningun jugador de dicho equipo que cumpla hoy años.");
        } else {
            for (int i = 0; i < cumpleJugador.size(); i++) {
                AlertUtils.mostrarConfirmacion("El jugador "
                        + cumpleJugador.get(i).getAliasJugador() + " cumple hoy " + edades.get(i) + " años");
            }
        }
    }

    public void setEquipo(Equipo equipo) {
        equipoSeleccionado = equipo;
        cargarLista();
        tfIdEquipo.setText(String.valueOf(equipoSeleccionado.getIdEquipo()));
    }

    public void cargarLista() {
        jugadores = dao.listarJugadores(equipoSeleccionado);
        lvJugadores.setItems(FXCollections.observableList(jugadores));
    }


    public void seleccionarJugador(MouseEvent mouseEvent) {
        jugadorSeleccionado = lvJugadores.getSelectionModel().getSelectedItem();
        cargarData();
    }

    public void cargarData() {
        tfAlias.setText(jugadorSeleccionado.getAliasJugador());
        tfFechaNacimiento.setText(jugadorSeleccionado.getFechaNacimiento().toString());
    }

    public void limpiarCampos() {
        tfIdEquipo.setText(null);
        tfAlias.setText(null);
        tfFechaNacimiento.setText(null);
    }
}
