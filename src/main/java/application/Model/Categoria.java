package application.Model;

public class Categoria {
    private String nombre;
    private String abreviatura;
    private String edad;
    private String carnet;
    private String apellidos;

    @Override
    public String toString() {
        return super.toString();
    }

    public Categoria() {

    }

    public Categoria(String nombre, String abreviatura, String edad, String carnet, String apellidos) {
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.edad = edad;
        this.carnet = carnet;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
