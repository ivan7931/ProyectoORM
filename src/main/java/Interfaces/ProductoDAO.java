package Interfaces;

import Clases.*;

import java.io.IOException;
import java.util.ArrayList;

public interface ProductoDAO {

    void agregarProducto(Producto p) throws IOException;

    void eliminarProducto(int id) throws IOException;

    void actualizarProducto(Producto p) throws IOException;

    ArrayList<Producto> listar() throws IOException;

    Producto buscarPorId(int id) throws IOException;

    double calcularValorInventario() throws IOException;

    ArrayList<Producto> listarPorCategoria(String categoria) throws IOException;


}
