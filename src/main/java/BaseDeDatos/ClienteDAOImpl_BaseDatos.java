package BaseDeDatos;

import Clases.Cliente;
import Excepciones.DataAccessException;
import Interfaces.ClienteDAO;
import jdbc.ConexionBase;

import java.sql.Connection;
import java.util.ArrayList;

public class ClienteDAOImpl_BaseDatos implements ClienteDAO {

    @Override
    public void agregarCliente(Cliente c) throws DataAccessException {
        Connection conexion = ConexionBase.getConexion();


    }

    @Override
    public void eliminarCliente(int id) throws DataAccessException {

    }

    @Override
    public void actualizarCliente(Cliente c) throws DataAccessException {

    }

    @Override
    public ArrayList<Cliente> listarClientes() throws DataAccessException {
        return null;
    }

    @Override
    public Cliente buscarPorId(int id) throws DataAccessException {
        return null;
    }

    @Override
    public int contarClientes() throws DataAccessException {
        return 0;
    }

    @Override
    public ArrayList<Cliente> buscarPorNombre(String nombre) throws DataAccessException {
        return null;
    }
}
