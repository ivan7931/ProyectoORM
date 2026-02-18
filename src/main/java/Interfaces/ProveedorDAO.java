package Interfaces;
import Clases.*;
import Excepciones.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ProveedorDAO {

    /***
     * Añade un nuevo registro al fichero
     * @param p el proveedor que se agrega al fichero
     * @throws DataWriteException si ha habido algún error escribiendo el nuevo registro
     */
    void agregarProveedor(Proveedor p) throws DataWriteException;

    /***
     * Elimina un registro del fichero
     * @param id el campo por que el vamos a buscar el registro a eliminar
     * @throws DataWriteException si ha habido algún error eliminando el registro
     * @throws DataNotFoundException si no existe ningun proveedor con ese id
     */
    void eliminarProveedor(int id) throws DataWriteException, DataNotFoundException;

    /***
     * Actualiza un registro existente con nuevos datos
     * @param p el proveedor que vamos a actualizar
     * @throws DataWriteException si ha habido algún error actualizando el registro
     * @throws DataNotFoundException si no existe ningun proveedor con ese id
     */
    void actualizarProveedor(Proveedor p) throws DataWriteException, DataNotFoundException;

    /***
     * Lee los registros del fichero y los devuelve una lista en memoria
     * @return ArrayList con los registros del fichero
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     */
    List<Proveedor> listarProveedores() throws DataReadException, QueryException;

    /***
     * Busca un registro concreto en el fichero
     * @param id el campo por el que buscamos el registro
     * @return si encuentra el registro lo devuelve como un objeto Proveedor en memoria
     * @throws DataReadException si ha habido algún error accediendo al fichero
     * @throws QueryException si hay algun fallo con la consulta
     * @throws DataNotFoundException si no existe ningun proveedor con ese id
     */
    Proveedor buscarPorId(int id) throws DataNotFoundException, DataReadException, QueryException;

    /***
     *
     * @return el número total de proveedores guardados en el fichero
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     */
    int contarProveedores() throws QueryException, DataReadException;

    /***
     * Primero obtiene una lista con los productos, luego agrupa los productos obtenidos por su id_proveedor
     * @param idProveedor campom por el que vamos a agrupar
     * @return una lista con los productos agrupados por el mismo id_proveedor
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     * @throws DataNotFoundException si no existe ningun proveedor con ese id
     */
    List<Producto> productosSuministrados(int idProveedor) throws DataNotFoundException, QueryException, DataReadException;

    Proveedor proveedorMayorFacturacion() throws DataNotFoundException, DataReadException, QueryException;
}
