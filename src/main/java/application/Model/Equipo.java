package application.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Equipos")
public class Equipo {
    @Id
    @Column(name = "idEquipo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEquipo;

    @Column(name = "nombreEquipo")
    private String nombreEquipo;

    @Column(name = "patrocinador")
    private String patrocinador;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "sancionado")
    private Boolean sancionado;

    @OneToMany(mappedBy = "idJugador", cascade = CascadeType.ALL)
    private List<Jugador> jugadores;

    public Equipo() {
    }

    public Equipo(int idEquipo, String nombreEquipo, String patrocinador, String categoria, Boolean sancionado) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.patrocinador = patrocinador;
        this.categoria = categoria;
        this.sancionado = sancionado;
    }

    public Equipo( String nombreEquipo, String patrocinador, String categoria, Boolean sancionado) {
        this.nombreEquipo = nombreEquipo;
        this.patrocinador = patrocinador;
        this.categoria = categoria;
        this.sancionado = sancionado;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getSancionado() {
        return sancionado;
    }

    public void setSancionado(Boolean sancionado) {
        this.sancionado = sancionado;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                ", patrocinador='" + patrocinador + '\'' +
                ", categoria='" + categoria + '\'' +
                ", sancionado=" + sancionado +
                '}';
    }
}
