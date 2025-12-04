package jdbc;

import Clases.Producto;
import Excepciones.ConexionException;
import Excepciones.DataAccessException;
import Excepciones.QueryException;
import Interfaces.ProductoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoJDBC implements ProductoDAO {
    @Override
    public void agregarProducto(Producto p) throws DataAccessException {
        Connection conexion = ConexionBase.getConexion();
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO gestion_tienda.producto (nombre, precio, categoria, cantidad, id_proveedor) VALUES (?, ?, ?, ?, ?);");
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, String.valueOf(p.getCategoria()));
            ps.setInt(4, p.getCantidad());
            ps.setInt(5, p.getIdProducto());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new QueryException("Error al agregar el cliente", e);
        }
        try {
            conexion.close();
        } catch (SQLException e) {
            throw new ConexionException("Error al cerrar la conexion", e);
        }
    }

    @Override
    public void eliminarProducto(int id) throws DataAccessException {
        Connection conexion = ConexionBase.getConexion();

        String sql = "DELETE FROM gestion_tienda.producto WHERE id_producto = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new QueryException("No existe un producto con el ID especificado");
            }

        } catch (SQLException e) {
            throw new QueryException("Error al eliminar el producto", e);
        }
    }

    @Override
    public void actualizarProducto(Producto p) throws DataAccessException {

    }

    @Override
    public ArrayList<Producto> listar() throws DataAccessException {
        return null;
    }

    @Override
    public Producto buscarPorId(int id) throws DataAccessException {
        return null;
    }

    @Override
    public double calcularValorInventario() throws DataAccessException {
        return 0;
    }

    @Override
    public ArrayList<Producto> listarPorCategoria(String categoria) throws DataAccessException {
        return null;
    }
}
