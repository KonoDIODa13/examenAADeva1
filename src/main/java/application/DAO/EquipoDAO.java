package application.DAO;

import application.Connection.ConexionHibernate;
import application.Connection.ConexionMYSQL;
import application.Connection.ConexionMongo;
import application.Model.Equipo;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO implements EquipoDAOImpl {
    private SessionFactory factory;
    private Session session;

    private Connection conexion;

    MongoClient con;
    Gson gson = new Gson();
    MongoCollection<Document> collection = null;

    public EquipoDAO() {
        conectarBD();

    }

    @Override
    public void conectarBD() {
        conectarHibernate();
        conectarMYSQl();
        conectarMongo();
    }

    public void conectarHibernate() {
        ConexionHibernate.conexion();
        factory = ConexionHibernate.getFactory();
        session = ConexionHibernate.getSession();
        System.out.println("Conexión con HIBERNATE correcta.");
    }

    public void conectarMYSQl() {
        try {
            conexion = ConexionMYSQL.conectar();
            System.out.println("Conexión con MYSQL correcta.");
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void conectarMongo() {
        try {
            con = ConexionMongo.conectar();
            MongoDatabase database = con.getDatabase("ExamenEquipos");
            database.createCollection("Equipos");
            collection = database.getCollection("Equipos");
            System.out.println("Conexión con MONGO correcta.");

        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }

    @Override
    public void insertarEquipo(Equipo equipo) { //tanmto en mysql como en mongo
        insertarMYSQL(equipo);
        if (equipo.getIdEquipo() == 0) {
            int id = buscaId(equipo.getNombreEquipo());
            insertarMongo(equipo, id);
        } else {
            insertarMongo(equipo);
        }
    }

    public void insertarMYSQL(Equipo equipo) {
        try {
            String insertEquipo = "INSERT INTO Equipos (idEquipo, nombreEquipo, patrocinador,categoria,sancionado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(insertEquipo);

            sentencia.setInt(1, equipo.getIdEquipo());
            sentencia.setString(2, equipo.getNombreEquipo());
            sentencia.setString(3, equipo.getPatrocinador());
            sentencia.setString(4, equipo.getCategoria());
            sentencia.setBoolean(5, equipo.getSancionado());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarMongo(Equipo equipo, int id) {
        collection.insertOne(new Document("id", id).append("nombreEquipo", equipo.getNombreEquipo())
                .append("patrocinador", equipo.getPatrocinador()).append("categoria", equipo.getCategoria()).append("sancion", equipo.getSancionado())
        );
    }

    public void insertarMongo(Equipo equipo) {
        collection.insertOne(new Document("id", equipo.getIdEquipo()).append("nombreEquipo", equipo.getNombreEquipo())
                .append("patrocinador", equipo.getPatrocinador()).append("categoria", equipo.getCategoria()).append("sancion", equipo.getSancionado())
        );
    }


    @Override
    public void modificarEquipo(Equipo equipo, Boolean estado) { //hibernate
        try {
            equipo.setSancionado(estado);
            session.beginTransaction();
            session.update(equipo);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.clear();
        }
    }

    @Override
    public void eliminarEquipo(Equipo equipo) { // hibernate y mongo
        eliminarHibernate(equipo);
        eliminarMongo(equipo);

    }

    public void eliminarHibernate(Equipo equipo) {
        try {
            session.beginTransaction();
            session.delete(equipo);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.clear();
        }
    }

    public void eliminarMongo(Equipo equipo) {
        collection.deleteOne(new Document("id", equipo.getIdEquipo()));
    }

    @Override
    public List<Equipo> listarEquipos() {// solo con hibernate
        List<Equipo> equipos = new ArrayList<>();
        try {
            session.beginTransaction();
            equipos = session.createQuery("from Equipo", Equipo.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (
                Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.clear();
        }
        return equipos;
    }

    @Override
    public Equipo getEquipo(int idEquipo) {// para saber si existe el equipo al cambiar el equipo del jugador
        Equipo equipo = null;
        try {
            session.beginTransaction();
            equipo = session.createQuery("from Equipo where idEquipo=:idEquipo", Equipo.class)
                    .setParameter("idEquipo", idEquipo)
                    .stream().findFirst().orElse(null);

            session.getTransaction().commit();
        } catch (
                Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.clear();
        }
        return equipo;
    }

    /*public int creaId() { // metodo para crear el id del equipo
        return listarEquipos().size() + 1;
    }*/

    public int buscaId(String nombreEquipo) { // en mysql ya que se ejecutará una vez metido el equipo nuevo
        int id = 0;
        try {
            String selectEquipo = "select idEquipo FROM Equipos WHERE nombreEquipo=?";
            PreparedStatement sentencia = conexion.prepareStatement(selectEquipo);

            sentencia.setString(1, nombreEquipo);

            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                id = resultado.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
