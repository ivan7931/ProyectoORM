package Interfaces;

import Clases.*;
import Excepciones.DataAccessException;
import Excepciones.DataNotFoundException;
import Excepciones.DataWriteException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductoDAO {

    /***
     * Añade un nuevo registro al fichero
     * @param p el producto que se agrega al fichero
     * @throws DataAccessException si ha habido algún error escribiendo el nuevo registro
     */
    void agregarProducto(Producto p) throws DataAccessException;

    /***
     * Elimina un registro del fichero
     * @param id el campo por que el vamos a buscar el registro a eliminar
     * @throws DataAccessException si ha habido algún error eliminando el registro
     */
    void eliminarProducto(int id) throws DataAccessException;

    /***
     * Actualiza un registro existente con nuevos datos
     * @param p el producto que vamos a actualizar
     * @throws DataAccessException si ha habido algún error actualizando el registro
     */
    void actualizarProducto(Producto p) throws DataAccessException;

    /***
     * Lee los registros del fichero y los devuelve una lista en memoria
     * @return ArrayList con los registros del fichero
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    ArrayList<Producto> listar() throws DataAccessException;

    /***
     * Busca un registro concreto en el fichero
     * @param id el campo por el que buscamos el registro
         * @return si encuentra el registro lo devuelve como un objeto Producto en memoria
     * @throws DataAccessException si ha habido algún error accediendo al fichero
     */
    Producto buscarPorId(int id) throws DataAccessException;

    /***
     *
     * @return el valor total del inventario
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    double calcularValorInventario() throws DataAccessException;

    /***
     * Agrupa los productos por categoria
     * @param categoria la categoria por la que vamos a agrupar los productos
     * @return un ArrayList con los productos agrupados por la misma categoría
     * @throws DataAccessException si ha habido algún error al acceder al fichero
     */
    ArrayList<Producto> listarPorCategoria(String categoria) throws DataAccessException;


}
