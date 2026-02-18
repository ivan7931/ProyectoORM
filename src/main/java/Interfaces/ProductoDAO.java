package Interfaces;

import Clases.*;
import Excepciones.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductoDAO {

    /***
     * Añade un nuevo registro al fichero
     * @param p el producto que se agrega al fichero
     * @throws DataWriteException si ha habido algún error escribiendo el nuevo registro
     */
    void agregarProducto(Producto p) throws DataWriteException;

    /***
     * Elimina un registro del fichero
     * @param id el campo por que el vamos a buscar el registro a eliminar
     * @throws DataWriteException si ha habido algún error eliminando el registro
     * @throws DataNotFoundException si no existe el producto que se quiere borrar
     */
    void eliminarProducto(int id) throws DataWriteException, DataNotFoundException;

    /***
     * Actualiza un registro existente con nuevos datos
     * @param p el producto que vamos a actualizar
     * @throws DataWriteException si ha habido algún error actualizando el registro
     * @throws DataNotFoundException si no existe el producto que se quiere actualizar
     */
    void actualizarProducto(Producto p) throws DataWriteException, DataNotFoundException;

    /***
     * Lee los registros del fichero y los devuelve una lista en memoria
     * @return ArrayList con los registros del fichero
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     */
    List<Producto> listar() throws DataReadException, QueryException;

    /***
     * Busca un registro concreto en el fichero
     * @param id el campo por el que buscamos el registro
         * @return si encuentra el registro lo devuelve como un objeto Producto en memoria
     * @throws DataReadException si ha habido algún error accediendo al fichero
     * @throws QueryException si hay algun fallo con la consulta
     * @throws DataNotFoundException si no existe ningun producto con ese id
     */
    Producto buscarPorId(int id) throws DataNotFoundException, QueryException, DataReadException;

    /***
     *
     * @return el valor total del inventario
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     */
    double calcularValorInventario() throws QueryException, DataReadException;

    /***
     * Agrupa los productos por categoria
     * @param categoria la categoria por la que vamos a agrupar los productos
     * @return un ArrayList con los productos agrupados por la misma categoría
     * @throws DataReadException si ha habido algún error al acceder al fichero
     * @throws QueryException si hay algun fallo con la consulta
     * @throws DataNotFoundException si no existe ningun producto con esa categoria
     */
    List<Producto> listarPorCategoria(String categoria) throws DataNotFoundException, QueryException, DataReadException;


    List<Producto> productosUltimosDias(int dias) throws DataNotFoundException, DataReadException, QueryException;
}
