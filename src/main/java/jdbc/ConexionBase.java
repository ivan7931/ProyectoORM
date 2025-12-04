package jdbc;

import Excepciones.DataAccessException;
import Excepciones.ConexionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBase {

    private static final String URL = "jdbc:postgresql://localhost:5433/proyecto_DAO";
    private static final String USER = "usuario_base";
    private static final String PASSWORD = "usuario_base";

    public static Connection getConexion() throws DataAccessException {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            return con;
        }  catch (SQLException ex) {
            throw new ConexionException("Error al generar la conexion con la base de datos", ex);
        }
    }

}
