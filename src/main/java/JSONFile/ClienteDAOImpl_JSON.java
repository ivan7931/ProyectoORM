package JSONFile;

import Clases.Cliente;
import Interfaces.ClienteDAO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ClienteDAOImpl_JSON implements ClienteDAO {
    private final File archivoJSON = new File("Clientes.json");
    public ClienteDAOImpl_JSON() {
    }
    @Override
    public void agregarCliente(Cliente c) throws IOException {
        ArrayList<Cliente> listaEscrita = listarClientes();
        listaEscrita.add(c);
        Jsonb jsonb = JsonbBuilder.create();
        // Serializar la lista de jugadores y escribirla en un archivo
        try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
            jsonb.toJson(listaEscrita, fileWriter);
        }
    }

    @Override
    public void eliminarCliente(int id) throws IOException {
        ArrayList<Cliente> listaEscrita = listarClientes();
        Cliente aux = null;
        Iterator<Cliente> iterator = listaEscrita.iterator();
        boolean eliminado = false;
        while (iterator.hasNext() && !eliminado) {
            aux = iterator.next();
            if (aux.getId()==id) {
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
    public void actualizarCliente(Cliente c) throws IOException {
        ArrayList<Cliente> listaEscrita = listarClientes();
        boolean actualizado = false;
        for(int i =0;i<listaEscrita.size() && !actualizado;i++){
            if(listaEscrita.get(i).getId()==c.getId()){
                listaEscrita.set(i, c);
                actualizado = true;
            }
        }
        Jsonb jsonb = JsonbBuilder.create();
        try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
            jsonb.toJson(listaEscrita, fileWriter);
        }
    }

    @Override
    public ArrayList<Cliente> listarClientes() throws IOException {
        ArrayList<Cliente> resultado = new ArrayList<>();
        // Crear una instancia de Jsonb
        Jsonb jsonb = JsonbBuilder.create();
        if(archivoJSON.length()>0) {
            // Deserializar la lista de jugadores desde el archivo JSON
            try (FileReader fileReader = new FileReader(archivoJSON)) {
                resultado = jsonb.fromJson(fileReader, new ArrayList<Cliente>() {
                }.getClass().getGenericSuperclass());
            }
        }
        return resultado;
    }

    @Override
    public Cliente buscarPorId(int id) throws IOException {
        Cliente resultado = null;
        ArrayList<Cliente> listaEscrita = listarClientes();
        Iterator<Cliente> iterator = listaEscrita.iterator();
        boolean encontrado = false;
        while(iterator.hasNext() && !encontrado){
            resultado = iterator.next();
            if(resultado.getId()==id){
                encontrado = true;
            }
        }
        return resultado;
    }

    @Override
    public int contarClientes() throws IOException {
        ArrayList<Cliente> listaEscrita = listarClientes();
        return listaEscrita.size();
    }

    @Override
    public ArrayList<Cliente> buscarPorNombre(String nombre) throws IOException {
        /*Cliente resultado = null;
        ArrayList<Cliente> listaEscrita = listarClientes();
        Iterator<Cliente> iterator = listaEscrita.iterator();
        boolean encontrado = false;
        while(iterator.hasNext() && !encontrado){
            resultado = iterator.next();
            if(resultado.getNombre().equals(nombre)){
                encontrado = true;
            }
        }
        return resultado;*/
        return null;
    }
}
