package jdbc;

import Clases.Producto;
import Excepciones.ConexionException;
import Excepciones.DataAccessException;
import Excepciones.DataNotFoundException;
import Excepciones.QueryException;
import Interfaces.ProductoDAO;

import java.sql.*;
import java.util.ArrayList;

public class ProductoJDBC implements ProductoDAO {
    @Override
    public void agregarProducto(Producto p) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()) {

            try(PreparedStatement pst = conexion.prepareStatement("select id_producto from producto order by id_producto desc limit 1;")){
                ResultSet rs = pst.executeQuery();
                rs.next();
                int id_producto = rs.getInt("id_producto");
                p.setIdProducto(id_producto + 1);
                rs.close();
            } catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }

            try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO producto (id_producto, nombre, precio, categoria, cantidad, id_proveedor) VALUES (?,?, ?, ?, ?, ?);")){
                ps.setInt(1, p.getIdProducto());
                ps.setString(2, p.getNombre());
                ps.setDouble(3, p.getPrecio());
                ps.setString(4, String.valueOf(p.getCategoria()));
                ps.setInt(5, p.getCantidad());
                ps.setInt(6, p.getIdProveedor());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new QueryException("Error al agregar el producto", e);
            }
        } catch (SQLException e) {
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public void eliminarProducto(int id) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            String sql = "DELETE FROM producto WHERE id_producto = ?";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, id);
                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas == 0) {
                    throw new DataNotFoundException("No existe un producto con el ID especificado");
                }

            } catch (SQLException e) {
                throw new QueryException("Error en la consulta", e);
            }
        }
        catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }

    }

    @Override
    public void actualizarProducto(Producto p) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            String sql = "UPDATE producto SET nombre = ?, precio = ?, categoria = ?, cantidad = ?, id_proveedor = ? WHERE id_producto = ?;";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, p.getNombre());
                ps.setDouble(2, p.getPrecio());
                ps.setString(3, String.valueOf(p.getCategoria()));
                ps.setInt(4, p.getCantidad());
                ps.setInt(5, p.getIdProveedor());
                ps.setInt(6, p.getIdProducto());
                int  filasAfectadas = ps.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new DataNotFoundException("No existe el producto que se desea actualizar");
                }
            } catch (SQLException e) {
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new QueryException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public ArrayList<Producto> listar() throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            String sql = "SELECT id_producto,nombre, precio, categoria, cantidad, id_proveedor FROM gestion_tienda.producto;";
            ArrayList<Producto> productos = new ArrayList<>();
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int  id_producto = rs.getInt("id_producto");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String categoria = rs.getString("categoria");
                    Producto.categorias categoria1 = Producto.categorias.valueOf(categoria);
                    int cantidad = rs.getInt("cantidad");
                    int id_proveedor = rs.getInt("id_proveedor");
                    Producto producto = new Producto (nombre, precio, categoria1,cantidad, id_proveedor);
                    producto.setIdProducto(id_producto);
                    productos.add(producto);
                }
                return productos;
            } catch (SQLException e) {
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public Producto buscarPorId(int id) throws DataAccessException {
        Producto producto = new Producto();
        try(Connection conexion = ConexionBase.getConexion()){
            String sql = "SELECT nombre, precio, categoria, cantidad, id_proveedor FROM producto WHERE id_producto = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setCategoria(Producto.categorias.valueOf(rs.getString("categoria")));
                    producto.setCantidad(rs.getInt("cantidad"));
                    producto.setIdProveedor(rs.getInt("id_proveedor"));
                    producto.setIdProducto(id);
                    return producto;
                }
            } catch (SQLException e) {
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
        throw new DataNotFoundException("No existe un producto con el id especificado");
    }

    @Override
    public double calcularValorInventario() throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            String sql = "SELECT precio, cantidad FROM producto;";
            try(PreparedStatement ps = conexion.prepareStatement(sql)) {
                double total = 0;
                        ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    total += (rs.getDouble("precio") * rs.getDouble("cantidad"));
                }
                return total;
            }catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public ArrayList<Producto> listarPorCategoria(String categoria) throws DataAccessException {
        ArrayList<Producto> lista = new ArrayList<>();
        try(Connection conexion = ConexionBase.getConexion()){
            String sql = "SELECT id_producto, nombre, precio, categoria, cantidad, id_proveedor FROM producto WHERE categoria = ?;";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, categoria);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int  id_producto = rs.getInt("id_producto");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    Producto.categorias categoria1 = Producto.categorias.valueOf(categoria);
                    int cantidad = rs.getInt("cantidad");
                    int id_proveedor = rs.getInt("id_proveedor");
                    Producto producto = new Producto (id_producto,nombre, precio, categoria1,cantidad, id_proveedor);
                    lista.add(producto);
                }
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
        if (lista.isEmpty()){
            throw new DataNotFoundException("No se ha encontrado ningun producto de esta categoria");
        } else{
            return lista;
        }
    }
}
