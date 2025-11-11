package Interfaces;
import Clases.*;
import Excepciones.DataAccessException;
import Excepciones.DataWriteException;
import Excepciones.DataNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

/***
 *
 */
public interface ClienteDAO {
    /***
     * Añade un nuevo registro al fichero
     * @param c el cliente que se agrega al fichero
     * @throws DataAccessException si ha habido algún error escribiendo el nuevo registro
     */
    void agregarCliente (Cliente c) throws DataAccessException;

    /***
     * Elimina un registro del fichero
     * @param id el campo por que el vamos a buscar el registro a eliminar
     * @throws DataAccessException si ha habido algún error eliminando el registro
     */
    void eliminarCliente (int id) throws DataAccessException;

    /***
     * Actualiza un registro existente con nuevos datos
     * @param c el cliente qye vamos a actualizar
     * @throws DataAccessException si ha habido algún error actualizando el registro
     */
    void actualizarCliente (Cliente c) throws DataAccessException;

    /***
     * Lee los registros del fichero y los devuelve una lista en memoria
     * @return ArrayList con los registros del fichero
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    ArrayList<Cliente> listarClientes() throws DataAccessException;

    /***
     * Busca un registro concreto en el fichero
     * @param id el campo por el que buscamos el registro
     * @return si encuentra el registro lo devuelve como un objeto Cliente en memoria
     * @throws DataAccessException si ha habido algún error accediendo al fichero
     */
    Cliente buscarPorId (int id) throws DataAccessException;

    /***
     *
     * @return el numero de registros que hay en el fichero
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    int contarClientes() throws DataAccessException;

    /***
     * Busca en el fichero los registros que tienen un nombre determinado
     * @param nombre el campo por el cual buscamos los registros
     * @return una lista con los clientes que tienen el mismo nombre
     * @throws DataAccessException si ha habido algún error al acceder al fihcero
     */
    ArrayList<Cliente> buscarPorNombre(String nombre) throws DataAccessException;
}
