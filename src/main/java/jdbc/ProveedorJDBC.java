package jdbc;

import Clases.Producto;
import Clases.Proveedor;
import Excepciones.ConexionException;
import Excepciones.DataAccessException;
import Excepciones.DataNotFoundException;
import Excepciones.QueryException;
import Interfaces.ProveedorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProveedorJDBC implements ProveedorDAO {
    @Override
    public void agregarProveedor(Proveedor p) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            try(PreparedStatement ps = conexion.prepareStatement("select id_proveedor from proveedor order by id_proveedor desc limit 1;")) {
                ResultSet rs = ps.executeQuery();
                rs.next();
                int id_proveedor = rs.getInt("id_proveedor");
                p.setIdProveedor(id_proveedor + 1);
            } catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }
            try(PreparedStatement ps = conexion.prepareStatement("INSERT INTO proveedor (id_proveedor, nombre, empresa) VALUES (?, ?, ?);")) {
                ps.setInt(1,p.getIdProveedor());
                ps.setString(2,p.getNombre());
                ps.setString(3,p.getEmpresa());
                ps.executeUpdate();
            } catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public void eliminarProveedor(int id) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            try(PreparedStatement ps = conexion.prepareStatement("DELETE FROM proveedor WHERE id_proveedor = ?")) {
                ps.setInt(1,id);
                int filas = ps.executeUpdate();
                if (filas == 0){
                    throw new DataNotFoundException("Error, no existe un proveedor con el ID especificado");
                }
            } catch (SQLException e){
                if (e.getSQLState().equals("23503")) {
                    throw new DataAccessException("Error, no se puede eliminar el proveedor ya que tiene productos asociados", e);
                }
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public void actualizarProveedor(Proveedor p) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            try(PreparedStatement ps = conexion.prepareStatement("update proveedor set nombre=?, empresa=? where id_proveedor=?;")) {
                ps.setString(1,p.getNombre());
                ps.setString(2,p.getEmpresa());
                ps.setInt(3,p.getIdProveedor());
                int filas = ps.executeUpdate();
                if (filas == 0){
                    throw new DataNotFoundException("Error, no existe un proveedor con el ID especificado");
                }
            } catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }
        }catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public ArrayList<Proveedor> listarProveedores() throws DataAccessException {
        ArrayList<Proveedor> lista = new ArrayList<>();
        try(Connection conexion = ConexionBase.getConexion()){
            try(PreparedStatement ps = conexion.prepareStatement("select id_proveedor, nombre, empresa from proveedor;")){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id_proveedor = rs.getInt("id_proveedor");
                    String nombre = rs.getString("nombre");
                    String empresa = rs.getString("empresa");
                    lista.add(new Proveedor(id_proveedor, nombre, empresa));
                }
                if (lista.isEmpty()){
                    throw new DataNotFoundException("No hay ningun proveedor en la base de datos");
                } else{
                    return lista;
                }

            }catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public Proveedor buscarPorId(int id) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            try(PreparedStatement ps = conexion.prepareStatement("SELECT nombre, empresa FROM proveedor WHERE id_proveedor=?;")) {
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String nombre = rs.getString("nombre");
                    String empresa = rs.getString("empresa");
                    return new Proveedor(id, nombre, empresa);
                }
            } catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }
        } catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
        throw new DataNotFoundException("No existe el proveedor");
    }

    @Override
    public int contarProveedores() throws DataAccessException {
        try(Connection conexion =ConexionBase.getConexion()){
            try(PreparedStatement ps = conexion.prepareStatement("SELECT count(id_proveedor) FROM proveedor;")){
                ResultSet rs = ps.executeQuery();
                rs.next();
                int total = rs.getInt("count");
                return total;
            } catch (SQLException e){
                throw new QueryException("Error en la consulta", e);
            }
        }catch (SQLException e){
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public ArrayList<Producto> productosSuministrados(int idProveedor) throws DataAccessException {
        try(Connection conexion = ConexionBase.getConexion()){
            String sql = "SELECT id_producto,nombre, precio, categoria, cantidad, id_proveedor FROM producto WHERE id_proveedor=?;";
            ArrayList<Producto> productos = new ArrayList<>();
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1,idProveedor);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int  id_producto = rs.getInt("id_producto");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String categoria = rs.getString("categoria");
                    Producto.categorias categoria1 = Producto.categorias.valueOf(categoria);
                    int cantidad = rs.getInt("cantidad");
                    int id_proveedor = rs.getInt("id_proveedor");
                    Producto producto = new Producto (id_producto,nombre, precio, categoria1,cantidad, id_proveedor);
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
}
