package Interfaces;
import Clases.*;
import Excepciones.DataAccessException;
import Excepciones.DataWriteException;

import java.io.IOException;
import java.util.ArrayList;

public interface ClienteDAO {

    void agregarCliente (Cliente c) throws DataAccessException;

    void eliminarCliente (int id) throws DataAccessException;

    void actualizarCliente (Cliente c) throws DataAccessException, IOException;

    ArrayList<Cliente> listarClientes() throws DataAccessException;

    Cliente buscarPorId (int id) throws DataAccessException;

    int contarClientes() throws DataAccessException;

    ArrayList<Cliente> buscarPorNombre(String nombre) throws DataAccessException;
}
