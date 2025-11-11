package JSONFile;

import Clases.Producto;
import Clases.Proveedor;
import Excepciones.DataAccessException;
import Excepciones.DataNotFoundException;
import Interfaces.ProveedorDAO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProveedorDAOImpl_JSON implements ProveedorDAO {
    private final File archivoJSON = new File("Proveedores.json");
    public ProveedorDAOImpl_JSON() {

    }
    @Override
    public void agregarProveedor(Proveedor p) throws DataAccessException {
        ArrayList<Proveedor> listaProveedores = listarProveedores();
        listaProveedores.add(p);
        try {
            guardarJSON(listaProveedores);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarProveedor(int id) throws DataAccessException {
        try {
            ArrayList<Proveedor> listaProveedores = listarProveedores();
            boolean eliminado = false;
            for (Proveedor proveedor : listaProveedores) {
                if (proveedor.getIdProveedor() == id) {
                    listaProveedores.remove(proveedor);
                    eliminado = true;
                    break;
                }
            }
            if (!eliminado) {
                throw new DataNotFoundException("No se encontro el proveedor");
            }

            guardarJSON(listaProveedores);
        } catch (DataNotFoundException e){
            throw e;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarProveedor(Proveedor p) throws DataAccessException {
        ArrayList<Proveedor> listaProveedores = listarProveedores();
        for (int i = 0; i < listaProveedores.size(); i++) {
            if (listaProveedores.get(i).getIdProveedor() == p.getIdProveedor()) {
                listaProveedores.set(i, p);
                break;
            }
        }
        try {
            guardarJSON(listaProveedores);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Proveedor> listarProveedores() throws DataAccessException {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        // Crear una instancia de Jsonb
        Jsonb jsonb = JsonbBuilder.create();
        if(archivoJSON.length()>0) {
            // Deserializar la lista de jugadores desde el archivo JSON
            try (FileReader fileReader = new FileReader(archivoJSON)) {
                proveedores = jsonb.fromJson(fileReader, new ArrayList<Proveedor>() {
                }.getClass().getGenericSuperclass());
            }
            catch(Exception e) {
                throw new DataAccessException("Error",e);
            }
        }
        return proveedores;
    }

    @Override
    public Proveedor buscarPorId(int id) throws DataAccessException {
        ArrayList<Proveedor> listaProveedores;
        listaProveedores = listarProveedores();
        for (Proveedor proveedor : listaProveedores) {
            if (proveedor.getIdProveedor() == id) {
                return proveedor;
            }
        }
        throw new DataNotFoundException("No se encontro el proveedor con ID" + id);
    }

    @Override
    public int contarProveedores() throws DataAccessException {
        return listarProveedores().size();
    }

    @Override
    public ArrayList<Producto> productosSuministrados(int idProveedor) {
        return null;
    }
    public void guardarJSON (ArrayList<Proveedor> proveedores) throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        try (FileWriter fileWriter = new FileWriter(archivoJSON)) {
            jsonb.toJson(proveedores, fileWriter);
        }
        catch (IOException e) {
            throw new IOException(e);
        }
    }
}
