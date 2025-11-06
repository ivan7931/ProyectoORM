package JSONFile;

import Clases.Producto;
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
    @Override
    public void agregarProducto(Producto p) throws IOException {
        ArrayList<Producto> listaEscrita = listar();
        listaEscrita.add(p);
        Jsonb jsonb = JsonbBuilder.create();
        // Serializar la lista de jugadores y escribirla en un archivo
        try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
            jsonb.toJson(listaEscrita, fileWriter);
        }

    }

    @Override
    public void eliminarProducto(int id) throws IOException {
        ArrayList<Producto> listaEscrita = listar();
        Producto aux = null;
        Iterator<Producto> iterator = listaEscrita.iterator();
        boolean eliminado = false;
        while (iterator.hasNext() && !eliminado) {
            aux = iterator.next();
            if (aux.getIdProducto()==id) {
                iterator.remove();
                eliminado = true;
            }
        }
        Jsonb jsonb = JsonbBuilder.create();
        try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
            jsonb.toJson(listaEscrita, fileWriter);
        }
    }

    @Override
    public void actualizarProducto(Producto p) throws IOException {
        ArrayList<Producto> listaEscrita = listar();
        boolean actualizado = false;
        for(int i =0;i<listaEscrita.size() && !actualizado;i++){
            if(listaEscrita.get(i).getIdProducto()==p.getIdProducto()){
                listaEscrita.set(i, p);
                actualizado = true;
            }
        }
        Jsonb jsonb = JsonbBuilder.create();
        try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
            jsonb.toJson(listaEscrita, fileWriter);
        }
    }

    @Override
    public ArrayList<Producto> listar() throws IOException {
        ArrayList<Producto> resultado = new ArrayList<>();
        // Crear una instancia de Jsonb
        Jsonb jsonb = JsonbBuilder.create();
        if(archivoJSON.length()>0) {
            // Deserializar la lista de jugadores desde el archivo JSON
            try (FileReader fileReader = new FileReader(archivoJSON)) {
                resultado = jsonb.fromJson(fileReader, new ArrayList<Producto>() {
                }.getClass().getGenericSuperclass());
            }
        }
        return resultado;
    }

    @Override
    public Producto buscarPorId(int id) throws IOException {
        Producto resultado = null;
        ArrayList<Producto> listaEscrita = listar();
        for(int i =0;i<listaEscrita.size();i++){
            if(listaEscrita.get(i).getIdProducto()==id){
                resultado = listaEscrita.get(i);
            }
        }
        return resultado;
    }

    @Override
    public double calcularValorInventario() throws IOException {
        ArrayList<Producto> listaEscrita = listar();
        double inventario = 0;

        for (int i = 0; i < listaEscrita.size(); i++) {
            inventario += (listaEscrita.get(i).getPrecio() * listaEscrita.get(i).getCantidad());
        }

        return inventario;
    }

    @Override
    public ArrayList<Producto> listarPorCategoria(String categoria) throws IOException {
        ArrayList<Producto> listaEscrita = listar();
        ArrayList<Producto> resultado = new ArrayList<>();
        for (int i = 0; i < listaEscrita.size(); i++) {
            if(listaEscrita.get(i).getCategoria().name().equalsIgnoreCase(categoria)){
                resultado.add(listaEscrita.get(i));
            }
        }
        return resultado;
    }
}
