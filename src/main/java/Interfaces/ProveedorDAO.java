package Interfaces;
import Clases.*;
import Excepciones.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ProveedorDAO {

    /***
     * Añade un nuevo registro al fichero
     * @param p el proveedor que se agrega al fichero
     * @throws DataAccessException si ha habido algún error escribiendo el nuevo registro
     */
    void agregarProveedor(Proveedor p) throws DataAccessException;

    /***
     * Elimina un registro del fichero
     * @param id el campo por que el vamos a buscar el registro a eliminar
     * @throws DataAccessException si ha habido algún error eliminando el registro
     */
    void eliminarProveedor(int id) throws DataAccessException;

    /***
     * Actualiza un registro existente con nuevos datos
     * @param p el proveedor que vamos a actualizar
     * @throws DataAccessException si ha habido algún error actualizando el registro
     */
    void actualizarProveedor(Proveedor p) throws DataAccessException;

    /***
     * Lee los registros del fichero y los devuelve una lista en memoria
     * @return ArrayList con los registros del fichero
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    List<Proveedor> listarProveedores() throws DataAccessException;

    /***
     * Busca un registro concreto en el fichero
     * @param id el campo por el que buscamos el registro
     * @return si encuentra el registro lo devuelve como un objeto Proveedor en memoria
     * @throws DataAccessException si ha habido algún error accediendo al fichero
     */
    Proveedor buscarPorId(int id) throws DataAccessException;

    /***
     *
     * @return el número total de proveedores guardados en el fichero
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    int contarProveedores() throws DataAccessException;

    /***
     * Primero obtiene una lista con los productos, luego agrupa los productos obtenidos por su id_proveedor
     * @param idProveedor campom por el que vamos a agrupar
     * @return una lista con los productos agrupados por el mismo id_proveedor
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    List<Producto> productosSuministrados(int idProveedor) throws DataAccessException;
}
