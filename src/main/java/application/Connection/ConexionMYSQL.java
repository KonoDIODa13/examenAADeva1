package application.Connection;

import application.Utils.R;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionMYSQL {

    // funcion para conectar a la BD
    public static Connection conectar() throws ClassNotFoundException, SQLException, IOException {
        try {
            Properties configuration = new Properties();
            configuration.load(R.getProperties("mysqlDatabase.properties"));
            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String name = configuration.getProperty("name");
            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                    username, password);
            // System.out.println("Conectada correctamente a la BD");
            return conexion;
        } catch (Exception e) {
            System.out.println("Conexion Fallida");
            System.out.println(e);
            return null;
        }
    }

    public static void desconectar(Connection con) throws SQLException {
        con.close();
    }
}
