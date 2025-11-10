package Interfaces;
import Clases.*;

import java.io.IOException;
import java.util.ArrayList;

public interface ProveedorDAO {

    void agregarProveedor(Proveedor p) throws IOException;

    void eliminarProveedor(int id) throws IOException;

    void actualizarProveedor(Proveedor p) throws IOException;

    ArrayList<Proveedor> listarProveedores() throws IOException;

    Proveedor buscarPorId(int id) throws IOException;

    int contarProveedores() throws IOException;

    ArrayList<Producto> productosSuministrados(int idProveedor) throws IOException;
}
