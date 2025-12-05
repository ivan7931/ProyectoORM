package jdbc;

import Excepciones.DataAccessException;
import Excepciones.ConexionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBase {

    private static final String URL = "jdbc:postgresql://localhost:5433/proyecto_DAO";
    private static final String USER = "usuario_base";
    private static final String PASSWORD = "";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
