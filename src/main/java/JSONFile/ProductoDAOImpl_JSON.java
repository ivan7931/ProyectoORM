package JSONFile;

import Clases.Producto;
import Interfaces.ProductoDAO;

import java.io.IOException;
import java.util.ArrayList;

public class ProductoDAOImpl_JSON implements ProductoDAO {
    @Override
    public void agregarProducto(Producto p) throws IOException {

    }

    @Override
    public void eliminarProducto(int id) throws IOException {

    }

    @Override
    public void actualizarProducto(Producto p) throws IOException {

    }

    @Override
    public ArrayList<Producto> listar() throws IOException {
        return null;
    }

    @Override
    public Producto buscarPorId(int id) throws IOException {
        return null;
    }

    @Override
    public double calcularValorInventario() throws IOException {
        return 0;
    }

    @Override
    public ArrayList<Producto> listarPorCategoria(String categoria) throws IOException {
        return null;
    }
}
