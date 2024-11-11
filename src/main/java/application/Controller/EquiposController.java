package application.Controller;

import application.DAO.EquipoDAO;
import application.Model.Categoria;
import application.Model.Equipo;
import application.Utils.AlertUtils;
import application.Utils.CambioEscenas;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EquiposController implements Initializable {

    public AnchorPane paneEquipo;
    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private TextField tfIdEquipo, tfNombreEquipo, tfPatrocinador;

    public TableView<Equipo> tvEquipos;

    public TableColumn<Equipo, String> tcIDEquipo, tcNombreEquipo, tcPatrocinador, tcCategoria, tcSancionado;

    public ToggleGroup tgSancionado;

    public RadioButton rbSancionado, rbNoSancionado;

    EquipoDAO dao;
    List<Equipo> equipos;
    Equipo equipoSeleccionado;

    public EquiposController() {
        dao = new EquipoDAO();
    }

    @FXML
    void AltaEquipo(ActionEvent event) {
        String nombreEquipo = tfNombreEquipo.getText();
        String patrocinador = tfPatrocinador.getText();
        String categoria = cbCategoria.getValue();
        Boolean sancionado = Boolean.parseBoolean(tgSancionado.getSelectedToggle().getUserData().toString());
        int id;
        Equipo equipo;
        if (tfIdEquipo.getText() == null || tfIdEquipo.getText().isEmpty()) {
            equipo = new Equipo(nombreEquipo, patrocinador, categoria, sancionado);
        } else {
            id = Integer.parseInt(tfIdEquipo.getText());
            equipo = new Equipo(id, nombreEquipo, patrocinador, categoria, sancionado);
        }
        dao.insertarEquipo(equipo);
        AlertUtils.mostrarConfirmacion("Equipo insertado con exito");
        limpiarCampos(event);
        cargarTabla();
    }

    @FXML
    void eliminarEquipo(ActionEvent event) {
        if (equipoSeleccionado != null) {
            //Boolean sancionado = Boolean.parseBoolean(tgSancionado.getSelectedToggle().getUserData().toString());
            dao.eliminarEquipo(equipoSeleccionado);
            limpiarCampos(event);
            cargarTabla();
        } else {
            AlertUtils.mostrarError("Seleccione primero un equipo");
        }
    }

    @FXML
    void limpiarCampos(ActionEvent event) {
        tfIdEquipo.setText(null);
        tfNombreEquipo.setText(null);
        tfPatrocinador.setText(null);
        tgSancionado.selectToggle(null);
        cbCategoria.getItems().clear();
        cargarCB();
    }

    @FXML
    void modificarEstadoEquipo(ActionEvent event) {
        if (equipoSeleccionado != null) {
            Boolean sancionado = Boolean.parseBoolean(tgSancionado.getSelectedToggle().getUserData().toString());
            dao.modificarEquipo(equipoSeleccionado, sancionado);
            cargarTabla();
            AlertUtils.mostrarConfirmacion("Equipo modificado con exito");
        } else {
            AlertUtils.mostrarError("Seleccione primero un equipo");
        }
    }

    @FXML
    void mostrarFormularioJugadores(ActionEvent event) {
        if (equipoSeleccionado != null) {
            CambioEscenas.cambioEscena("gestionJugadores.fxml", paneEquipo, equipoSeleccionado);
        } else {
            AlertUtils.mostrarError("Seleccione primero un equipo");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcIDEquipo.setCellValueFactory(new PropertyValueFactory<>("idEquipo"));
        tcNombreEquipo.setCellValueFactory(new PropertyValueFactory<>("nombreEquipo"));
        tcPatrocinador.setCellValueFactory(new PropertyValueFactory<>("patrocinador"));
        tcCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tcSancionado.setCellValueFactory(new PropertyValueFactory<>("sancionado"));
        cargarCB();
        cargarTabla();
    }

    public void cargarCB() {
        List<String> nombreCategoria = new ArrayList<>();
        ObjectMapper JSON_MAPPER = new ObjectMapper();
        cbCategoria.getItems().clear();
        try {
            // Me he creado un objeto Categoria porque es la unica manera que se de recoger los datos con esta libreria.
            List<Categoria> categoriasTotales = JSON_MAPPER.readValue(new File("src/main/resources/data/Categorias.json"),
                    JSON_MAPPER.getTypeFactory().constructCollectionType
                            (ArrayList.class, Categoria.class));

            categoriasTotales.forEach(categoria -> nombreCategoria.add(categoria.getNombre()));
            cbCategoria.getItems().addAll(FXCollections.observableArrayList(nombreCategoria));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cargarTabla() {
        tvEquipos.getItems().clear();
        equipos = dao.listarEquipos();
        tvEquipos.setItems(FXCollections.observableList(equipos));
    }

    public void seleccionarEquipo(MouseEvent mouseEvent) {
        try {
            equipoSeleccionado = tvEquipos.getSelectionModel().getSelectedItem();
            cargarData();
        } catch (NullPointerException e) {
            AlertUtils.mostrarError("No has seleccionado ningun dato.");
        }
    }

    public void cargarData() {
        tfIdEquipo.setText(String.valueOf(equipoSeleccionado.getIdEquipo()));
        tfNombreEquipo.setText(equipoSeleccionado.getNombreEquipo());
        tfPatrocinador.setText(equipoSeleccionado.getPatrocinador());
        cbCategoria.setValue(equipoSeleccionado.getCategoria());
        // Para seleccionar el radioButton para cada caso
        if (equipoSeleccionado.getSancionado()) {
            tgSancionado.selectToggle(rbSancionado);
        } else {
            tgSancionado.selectToggle(rbNoSancionado);
        }
    }
}

