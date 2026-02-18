package Interfaces;
import Clases.*;
import Excepciones.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 *
 */
public interface ClienteDAO {
    /***
     * Añade un nuevo registro al fichero
     * @param c el cliente que se agrega al fichero
     * @throws DataWriteException si ha habido algún error escribiendo el nuevo registro
     */
    void agregarCliente (Cliente c) throws DataWriteException;

    /***
     * Elimina un registro del fichero
     * @param id el campo por que el vamos a buscar el registro a eliminar
     * @throws DataWriteException si ha habido algún error eliminando el registro
     * @throws DataNotFoundException si no existe el cliente que se quiere borrar
     */
    void eliminarCliente (int id) throws DataWriteException, DataNotFoundException;

    /***
     * Actualiza un registro existente con nuevos datos
     * @param c el cliente qye vamos a actualizar
     * @throws DataWriteException si ha habido algún error actualizando el registro
     * @throws DataNotFoundException si no existe el cliente que se quiere actualizar
     */
    void actualizarCliente (Cliente c) throws DataWriteException, DataNotFoundException;

    /***
     * Lee los registros del fichero y los devuelve una lista en memoria
     * @return ArrayList con los registros del fichero
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     */
    List<Cliente> listarClientes() throws DataReadException, QueryException;

    /***
     * Busca un registro concreto en el fichero
     * @param id el campo por el que buscamos el registro
     * @return si encuentra el registro lo devuelve como un objeto Cliente en memoria
     * @throws DataReadException si ha habido algún error accediendo al fichero
     * @throws QueryException si hay algun fallo con la consulta
     * @throws DataNotFoundException si no existe ningun cliente con ese id
     */
    Cliente buscarPorId (int id) throws DataNotFoundException, QueryException, DataReadException;

    /***
     *
     * @return el numero de registros que hay en el fichero
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     */
    int contarClientes() throws QueryException, DataReadException;

    /***
     * Busca en el fichero los registros que tienen un nombre determinado
     * @param nombre el campo por el cual buscamos los registros
     * @return una lista con los clientes que tienen el mismo nombre
     * @throws DataReadException si ha habido algún error al acceder al fihcero
     * @throws QueryException si hay algun fallo con la consulta
     * @throws DataNotFoundException si no existe ningun cliente con ese nombre
     */
    List<Cliente> buscarPorNombre(String nombre) throws DataNotFoundException, QueryException, DataReadException;
}
