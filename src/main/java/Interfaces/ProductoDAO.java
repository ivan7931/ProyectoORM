package Interfaces;

import Clases.*;
import Excepciones.DataAccessException;
import Excepciones.DataWriteException;

import java.io.IOException;
import java.util.ArrayList;

public interface ProductoDAO {

    void agregarProducto(Producto p) throws DataAccessException;

    void eliminarProducto(int id) throws DataAccessException;

    void actualizarProducto(Producto p) throws DataAccessException;

    ArrayList<Producto> listar() throws DataAccessException;

    Producto buscarPorId(int id) throws DataAccessException;

    double calcularValorInventario() throws DataAccessException;

    ArrayList<Producto> listarPorCategoria(String categoria) throws DataAccessException;


}
