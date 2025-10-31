package Interfaces;

import java.io.IOException;
import java.util.ArrayList;

public interface InterfazGenerica <T>{
    void agregar(T t) throws IOException;
    void eliminar(int id) throws IOException;
    void actualizar(T t) throws IOException;
    ArrayList<T> listar() throws IOException;
    T buscarPorId(int id) throws IOException;
    int contar() throws IOException;
}
