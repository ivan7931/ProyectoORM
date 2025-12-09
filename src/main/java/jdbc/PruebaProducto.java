package jdbc;

import Clases.Producto;
import Clases.Proveedor;
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

        //Producto p1 = new Producto("Ejemplo",120, Producto.categorias.CATEGORIA1,20,3);
        //p1.setIdProducto(11);

        //productoJDBC.actualizarProducto(p);
        //productoJDBC.agregarProducto(p);
        //productoJDBC.eliminarProducto(1);
        //productoJDBC.eliminarProducto(2);
        //productoJDBC.eliminarProducto(3);
        //productoJDBC.eliminarProducto(4);
        //productoJDBC.eliminarProducto(5);
        //productoJDBC.eliminarProducto(6);
        //productoJDBC.eliminarProducto(7);
        //productoJDBC.eliminarProducto(8);
        //productoJDBC.eliminarProducto(9);

        //System.out.println(productoJDBC.buscarPorId(13));
        //System.out.println(productoJDBC.calcularValorInventario());
        /*for (Producto producto: productoJDBC.listarPorCategoria("")) {
            System.out.println(producto);
        }
        for (Producto producto: productoJDBC.listar()){
            System.out.println(producto);
        }*/

        Proveedor prov = new Proveedor(7,"Paco","PacoSL");
        ProveedorJDBC proveedorJDBC = new ProveedorJDBC();
        proveedorJDBC.agregarProveedor(prov);
        //proveedorJDBC.eliminarProveedor(7);
        //proveedorJDBC.actualizarProveedor(prov);
        /*for(Proveedor p1:proveedorJDBC.listarProveedores()){
            System.out.println(p1);
        }*/
        System.out.println("");
        System.out.println(proveedorJDBC.buscarPorId(2));
        System.out.println("");
        System.out.println(proveedorJDBC.contarProveedores());
        System.out.println("");
        for(Producto p1:proveedorJDBC.productosSuministrados(1)){
            System.out.println(p1);
        }


    }
}
