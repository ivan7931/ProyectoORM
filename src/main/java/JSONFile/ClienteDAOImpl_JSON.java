package JSONFile;

import Clases.Cliente;
import Excepciones.*;
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
    public void agregarCliente(Cliente c) throws DataAccessException {
        try {
            ArrayList<Cliente> ListaClientes = listarClientes();
            int nuevoID = 1;
            if (!ListaClientes.isEmpty()) {
                int maxID = 0;
                for (Cliente cliente : ListaClientes) {
                    if (cliente.getId() > maxID) {
                        maxID = cliente.getId();
                    }
                }
                nuevoID = maxID + 1;
                c.setId(nuevoID);
            }
            ArrayList<Cliente> listaEscrita = listarClientes();
            listaEscrita.add(c);
            Jsonb jsonb = JsonbBuilder.create();
            // Serializar la lista de jugadores y escribirla en un archivo
            try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
                jsonb.toJson(listaEscrita, fileWriter);
            }
        } catch (IOException e) {
            throw new DataWriteException("Error al guardar el cliente en el archivo JSON", e);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void eliminarCliente(int id) throws DataAccessException {
        try {
            ArrayList<Cliente> listaEscrita = listarClientes();
            Cliente aux;
            Iterator<Cliente> iterator = listaEscrita.iterator();
            boolean eliminado = false;
            while (iterator.hasNext() && !eliminado) {
                aux = iterator.next();
                if (aux.getId() == id) {
                    iterator.remove();
                    eliminado = true;
                    break;
                }
            }
            if (!eliminado) {
                throw new DataNotFoundException("No se encontro el cliente con id " + id);
            }
            Jsonb jsonb = JsonbBuilder.create();
            try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
                jsonb.toJson(listaEscrita, fileWriter);
            }
        } catch (DataNotFoundException e){
            throw e;
        }   catch (IOException e) {
            throw new DataWriteException("Error al eliminar el cliente en el archivo JSON", e);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void actualizarCliente(Cliente c) throws DataAccessException {
        try {
            ArrayList<Cliente> listaEscrita = listarClientes();
            boolean actualizado = false;
            for (int i = 0; i < listaEscrita.size() && !actualizado; i++) {
                if (listaEscrita.get(i).getId() == c.getId()) {
                    listaEscrita.set(i, c);
                    actualizado = true;
                }
            }
            if (!actualizado) {
                throw new DataNotFoundException("No se encontro el cliente");
            }
            Jsonb jsonb = JsonbBuilder.create();
            try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
                jsonb.toJson(listaEscrita, fileWriter);
            }
        } catch (DataNotFoundException e){
            throw e;
        }catch (IOException e) {
            throw new DataWriteException("Error al actualizar el cliente en el archivo JSON", e);
        }  catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public ArrayList<Cliente> listarClientes() throws DataAccessException{
        ArrayList<Cliente> resultado = new ArrayList<>();
        // Crear una instancia de Jsonb
        Jsonb jsonb = JsonbBuilder.create();
        if(archivoJSON.length()>0) {
            // Deserializar la lista de jugadores desde el archivo JSON
            try (FileReader fileReader = new FileReader(archivoJSON)) {
                resultado = jsonb.fromJson(fileReader, new ArrayList<Cliente>() {
                }.getClass().getGenericSuperclass());
            } catch (Exception e){
                throw new DataReadException("Error al leer el archivo JSON de clientes",e);
            }
        }
        return resultado;
    }

    @Override
    public Cliente buscarPorId(int id) throws DataAccessException {
        try {
            Cliente resultado = null;
            ArrayList<Cliente> listaEscrita = listarClientes();
            Iterator<Cliente> iterator = listaEscrita.iterator();
            boolean encontrado = false;
            while (iterator.hasNext() && !encontrado) {
                resultado = iterator.next();
                if (resultado.getId() == id) {
                    encontrado = true;
                }
            }
            if(encontrado) {
                return resultado;
            }
            else throw new DataNotFoundException("No se encontro el cliente con ID:" + id);
        } catch (DataNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new DataReadException("Error al buscar el cliente con ID" + id,e);
        }
    }

    @Override
    public int contarClientes() throws DataAccessException {
        return listarClientes().size();
    }

    @Override
    public ArrayList<Cliente> buscarPorNombre(String nombre) throws DataAccessException {
        try{
            ArrayList<Cliente> listaTemp = new ArrayList<>();
            ArrayList<Cliente> ListaClientesNombre = new ArrayList<>();
            if(archivoJSON.length()>0) {
                listaTemp = listarClientes();
            }
            for (Cliente cliente : listaTemp) {
                if (cliente.getNombre().equals(nombre)) {
                    ListaClientesNombre.add(cliente);
                }
            }
            return ListaClientesNombre;
        } catch (Exception e) {
            throw new DataReadException("Error al buscar el cliente con nombre " + nombre,e);
        }
    }
}
