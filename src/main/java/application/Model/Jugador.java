package application.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Jugadores")
public class Jugador {

    @Id
    @Column(name = "idJugador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idJugador;

    @Column(name = "aliasJugador")
    private String aliasJugador;

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "id_equipo", referencedColumnName = "idEquipo")
    private Equipo id_equipo;

    public Jugador() {

    }

    public Jugador(String aliasJugador, LocalDate fechaNacimiento, Equipo id_equipo) {
        this.aliasJugador = aliasJugador;
        this.fechaNacimiento = fechaNacimiento;
        this.id_equipo = id_equipo;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getAliasJugador() {
        return aliasJugador;
    }

    public void setAliasJugador(String aliasJugador) {
        this.aliasJugador = aliasJugador;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Equipo getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(Equipo id_equipo) {
        this.id_equipo = id_equipo;
    }

    @Override
    public String toString() {
        return "aliasJugador=" + aliasJugador +
                ", fechaNacimiento=" + fechaNacimiento +
                ", idEquipo=" + id_equipo;
    }
}
