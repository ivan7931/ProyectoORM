package JSONFile;

import Clases.Producto;
import Excepciones.DataAccessException;
import Excepciones.DataReadException;
import Excepciones.DataWriteException;
import Interfaces.ProductoDAO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ProductoDAOImpl_JSON implements ProductoDAO {
    private final File archivoJSON = new File("Productos.json");
    public ProductoDAOImpl_JSON() {

    }
    @Override
    public void agregarProducto(Producto p) throws DataAccessException {
        try {
            ArrayList<Producto> listaEscrita = listar();
            listaEscrita.add(p);
            Jsonb jsonb = JsonbBuilder.create();
            // Serializar la lista de jugadores y escribirla en un archivo
            try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
                jsonb.toJson(listaEscrita, fileWriter);
            }
        } catch (IOException e) {
            throw new DataWriteException("Error al guardar el producto en el archivo JSON", e);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void eliminarProducto(int id) throws DataAccessException {
        try {
            ArrayList<Producto> listaEscrita = listar();
            Producto aux;
            Iterator<Producto> iterator = listaEscrita.iterator();
            boolean eliminado = false;
            while (iterator.hasNext() && !eliminado) {
                aux = iterator.next();
                if (aux.getIdProducto() == id) {
                    iterator.remove();
                    eliminado = true;
                }
            }
            Jsonb jsonb = JsonbBuilder.create();
            try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
                jsonb.toJson(listaEscrita, fileWriter);
            }
        } catch (IOException e) {
            throw new DataWriteException("Error al eliminar el producto en el archivo JSON", e);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void actualizarProducto(Producto p) throws DataAccessException {
        try {
            ArrayList<Producto> listaEscrita = listar();
            boolean actualizado = false;
            for (int i = 0; i < listaEscrita.size() && !actualizado; i++) {
                if (listaEscrita.get(i).getIdProducto() == p.getIdProducto()) {
                    listaEscrita.set(i, p);
                    actualizado = true;
                }
            }
            Jsonb jsonb = JsonbBuilder.create();
            try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
                jsonb.toJson(listaEscrita, fileWriter);
            }
        } catch (IOException e) {
            throw new DataWriteException("Error al actualizar el cliente en el archivo JSON", e);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public ArrayList<Producto> listar() throws DataAccessException {
        ArrayList<Producto> resultado = new ArrayList<>();
        // Crear una instancia de Jsonb
        Jsonb jsonb = JsonbBuilder.create();
        if(archivoJSON.length()>0) {
            // Deserializar la lista de jugadores desde el archivo JSON
            try (FileReader fileReader = new FileReader(archivoJSON)) {
                resultado = jsonb.fromJson(fileReader, new ArrayList<Producto>() {
                }.getClass().getGenericSuperclass());
            } catch (Exception e) {
                throw new DataReadException("Error al leer el archivo JSON de productos", e);
            }
        }
        return resultado;
    }

    @Override
    public Producto buscarPorId(int id) throws DataAccessException {
        try {
            Producto resultado = null;
            ArrayList<Producto> listaEscrita = listar();
            for (Producto producto : listaEscrita) {
                if (producto.getIdProducto() == id) {
                    resultado = producto;
                }
            }
            return resultado;
        } catch (Exception e) {
            throw new DataReadException("Error al buscar el producto con ID" + id, e);
        }
    }

    @Override
    public double calcularValorInventario() throws DataAccessException {
        try {
            ArrayList<Producto> listaEscrita = listar();
            double inventario = 0;

            for (Producto producto : listaEscrita) {
                inventario += (producto.getPrecio() * producto.getCantidad());
            }
            return inventario;
        } catch (Exception e) {
            throw new DataAccessException("Error al calcular el valor de inventario", e);
        }
    }

    @Override
    public ArrayList<Producto> listarPorCategoria(String categoria) throws DataAccessException {
        try {
            ArrayList<Producto> listaEscrita = listar();
            ArrayList<Producto> resultado = new ArrayList<>();
            for (Producto producto : listaEscrita) {
                if (producto.getCategoria().name().equalsIgnoreCase(categoria)) {
                    resultado.add(producto);
                }
            }
            return resultado;
        } catch (Exception e) {
            throw new DataAccessException("Error al listar por categoria los productos",e);
        }
    }
}
