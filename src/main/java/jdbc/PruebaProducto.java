package jdbc;

import Clases.Producto;
import Excepciones.DataAccessException;

import java.sql.SQLException;

public class PruebaProducto {
    public static void main(String[] args) throws SQLException, DataAccessException {
        Producto p = new Producto(11,"Pepe",500, Producto.categorias.CATEGORIA1,20,3);
        //Producto p1 = new Producto("Ejemplo",120, Producto.categorias.CATEGORIA1,20,3);
        //p1.setIdProducto(11);


        ProductoJDBC productoJDBC = new ProductoJDBC();
        //productoJDBC.actualizarProducto(p);
        //productoJDBC.agregarProducto(p);
        //productoJDBC.eliminarProducto(11);
        for (Producto producto: productoJDBC.listar()){
            System.out.println(producto);
        }
    }
}
