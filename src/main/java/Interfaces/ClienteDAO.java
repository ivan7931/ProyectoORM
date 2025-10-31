package Interfaces;
import Clases.*;

import java.io.IOException;
import java.util.ArrayList;

public interface ClienteDAO {

    void agregarCliente (Cliente c) throws IOException;

    void eliminarCliente (int id) throws IOException;

    void actualizarCliente (Cliente c) throws IOException;

    ArrayList<Cliente> listarClientes() throws IOException;

    Cliente buscarPorId (int id) throws IOException;

    int contarClientes() throws IOException;

    ArrayList<Cliente> buscarPorNombre(String nombre) throws IOException;
}
