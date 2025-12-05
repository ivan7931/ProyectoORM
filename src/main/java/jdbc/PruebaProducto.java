package jdbc;

import Clases.Producto;
import Excepciones.DataAccessException;

import java.sql.SQLException;

public class PruebaProducto {
    public static void main(String[] args) throws SQLException, DataAccessException {
        Producto p = new Producto("Pepe",120, Producto.categorias.CATEGORIA1,20,3);
        ProductoJDBC productoJDBC = new ProductoJDBC();
        productoJDBC.agregarProducto(p);
        //productoJDBC.eliminarProducto(11);
    }
}
