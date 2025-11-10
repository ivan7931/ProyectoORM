package Interfaces;
import Clases.*;
import Excepciones.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;

public interface ProveedorDAO {

    void agregarProveedor(Proveedor p) throws DataAccessException;

    void eliminarProveedor(int id) throws DataAccessException;

    void actualizarProveedor(Proveedor p) throws DataAccessException;

    ArrayList<Proveedor> listarProveedores() throws DataAccessException;

    Proveedor buscarPorId(int id) throws DataAccessException;

    int contarProveedores() throws DataAccessException;

    ArrayList<Producto> productosSuministrados(int idProveedor) throws DataAccessException;
}
