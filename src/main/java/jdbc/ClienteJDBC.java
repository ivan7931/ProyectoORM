package jdbc;

import Clases.Cliente;
import Excepciones.*;
import Interfaces.ClienteDAO;

import java.sql.*;
import java.util.ArrayList;

public class ClienteJDBC implements ClienteDAO {
    @Override
    public void agregarCliente(Cliente c) throws DataAccessException {
        int ultimoID=0;
        try(Connection con = ConexionBase.getConexion()){
            String consulta = "select id_cliente from cliente order by id_cliente desc limit 1;";
            try{
                PreparedStatement ps = con.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    ultimoID = rs.getInt("id_cliente")+1;
                }
                else {
                    ultimoID = 1;
                }
                rs.close();
                ps.close();
            }
            catch(SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
            String consultaAgregar = "insert into cliente(id_cliente,nombre, apellido) values (?,?,?);";
            try(PreparedStatement ps = con.prepareStatement(consultaAgregar)){
                ps.setInt(1, ultimoID);
                ps.setString(2, c.getNombre());
                ps.setString(3, c.getApellido());
                int filasAfectadas=ps.executeUpdate();
                if(filasAfectadas==0){
                    throw new DataWriteException("No se pudo agregar el cliente");
                }
            }
            catch(SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
        }
        catch(SQLException e){
            throw new ConexionException("Conexion fallida ",e);
        }
    }

    @Override
    public void eliminarCliente(int id) throws DataAccessException {
        try(Connection con = ConexionBase.getConexion()){
            String consulta = "delete from cliente where id_cliente = ?;";
            try(PreparedStatement ps = con.prepareStatement(consulta)){
                ps.setInt(1,id);
                int filasAfectadas=ps.executeUpdate();
                if(filasAfectadas == 0){
                    throw new DataNotFoundException("No existe el cliente con el id : "+id);
                }
            }
            catch(SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
        }
        catch(SQLException e){
            throw new ConexionException("Conexion fallida",e);
        }
    }

    @Override
    public void actualizarCliente(Cliente c) throws DataAccessException {
        try(Connection con = ConexionBase.getConexion()){
            String consulta = "update cliente set nombre = ?, apellido = ? where id_cliente = ?;";
            try(PreparedStatement ps = con.prepareStatement(consulta)){
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getApellido());
                ps.setInt(3,c.getId());
                int filasAfectadas = ps.executeUpdate();
                if(filasAfectadas == 0){
                    throw new DataNotFoundException("No existe el cliente que se quiere actualizar");
                }
            }
            catch(SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
        }
        catch (SQLException e){
            throw new ConexionException("Conexion fallida",e);
        }
    }

    @Override
    public ArrayList<Cliente> listarClientes() throws DataAccessException {
        try(Connection con = ConexionBase.getConexion()){
            ArrayList<Cliente> listaClientes = new ArrayList<>();
            String consulta = "select id_cliente, nombre , apellido from cliente;";
            try(PreparedStatement ps = con.prepareStatement(consulta)){
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        Cliente cliente = new Cliente();
                        cliente.setId(rs.getInt("id_cliente"));
                        cliente.setNombre(rs.getString("nombre"));
                        cliente.setApellido(rs.getString("apellido"));
                        listaClientes.add(cliente);
                    }
                }
                return listaClientes;
            }
            catch(SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
        }
        catch(SQLException e){
            throw new ConexionException("Conexion fallida",e);
        }
    }

    @Override
    public Cliente buscarPorId(int id) throws DataAccessException {
        try(Connection con = ConexionBase.getConexion()){
            String consulta = "select id_cliente, nombre, apellido from cliente where id_cliente = ?;";
            try(PreparedStatement ps = con.prepareStatement(consulta)){
                ps.setInt(1, id);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        Cliente cliente = new Cliente();
                        cliente.setId(rs.getInt("id_cliente"));
                        cliente.setNombre(rs.getString("nombre"));
                        cliente.setApellido(rs.getString("apellido"));
                        return cliente;
                    }
                    else {
                        throw new DataNotFoundException("No existe el cliente con el id : "+id);
                    }
                }
            }
            catch (SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
        }
        catch(SQLException e){
            throw new ConexionException("Conexion fallida",e);
        }
    }

    @Override
    public int contarClientes() throws DataAccessException {
        try(Connection con = ConexionBase.getConexion()) {
            int total = 0;
            String consulta = "select count(id_cliente) as total_clientes from cliente;";
            try(PreparedStatement ps = con.prepareStatement(consulta)){
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    total = rs.getInt("total_clientes");
                }
                return total;
            }
            catch(SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
        }
        catch(SQLException e){
            throw new ConexionException("Conexion fallida",e);
        }
    }

    @Override
    public ArrayList<Cliente> buscarPorNombre(String nombre) throws DataAccessException {
        try(Connection con = ConexionBase.getConexion()){
            ArrayList<Cliente> listaClientes = new ArrayList<>();
            String consulta = "select id_cliente, nombre,apellido from cliente where nombre =?;";
            try(PreparedStatement ps = con.prepareStatement(consulta)){
                ps.setString(1, nombre);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        Cliente cliente = new Cliente();
                        cliente.setId(rs.getInt("id_cliente"));
                        cliente.setNombre(rs.getString("nombre"));
                        cliente.setApellido(rs.getString("apellido"));
                        listaClientes.add(cliente);
                    }
                }
                return  listaClientes;
            }
            catch (SQLException e){
                throw new QueryException("Error en la consulta",e);
            }
        }
        catch(SQLException e){
            throw new ConexionException("Conexion fallida",e);
        }
    }

}
