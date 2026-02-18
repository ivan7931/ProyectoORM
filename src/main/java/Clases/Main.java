package Clases;

import Excepciones.DataAccessException;
import jdbc.ClienteHibernate;
import jdbc.ProductoHibernate;
import jdbc.ProveedorHibernate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)throws DataAccessException {

        ClienteHibernate clienteDAO = new ClienteHibernate();
        ProveedorHibernate proveedorDAO = new ProveedorHibernate();
        ProductoHibernate productoDAO = new ProductoHibernate();
        /*
        try {
            System.out.println("=== PRUEBAS DE PROVEEDORES ===");
            // Agregar proveedores
            Proveedor p1 = new Proveedor("Proveedor1", "EmpresaA");
            Proveedor p2 = new Proveedor("Proveedor2", "EmpresaB");
            proveedorDAO.agregarProveedor(p1);
            proveedorDAO.agregarProveedor(p2);

            // Listar proveedores
            List<Proveedor> listaProv = proveedorDAO.listarProveedores();
            System.out.println("Lista de proveedores: " + listaProv);

            // Buscar proveedor por ID
            Proveedor buscado = proveedorDAO.buscarPorId(p1.getIdProveedor());
            System.out.println("Proveedor buscado por ID: " + buscado);

            // Contar proveedores
            int totalProv = proveedorDAO.contarProveedores();
            System.out.println("Total de proveedores: " + totalProv);

            // Actualizar proveedor
            p1.setEmpresa("EmpresaA_Modificada");
            proveedorDAO.actualizarProveedor(p1);
            System.out.println("Proveedor actualizado: " + proveedorDAO.buscarPorId(p1.getIdProveedor()));

            // === PRUEBAS DE CLIENTES ===
            Cliente c1 = new Cliente("Ana", "Perez");
            Cliente c2 = new Cliente("Juan", "Gomez");
            clienteDAO.agregarCliente(c1);
            clienteDAO.agregarCliente(c2);

            // Listar clientes
            List<Cliente> listaClientes = clienteDAO.listarClientes();
            System.out.println("Lista de clientes: " + listaClientes);

            // Buscar por ID
            Cliente clienteBuscado = clienteDAO.buscarPorId(c1.getIdCliente());
            System.out.println("Cliente buscado: " + clienteBuscado);

            // Contar clientes
            System.out.println("Total de clientes: " + clienteDAO.contarClientes());

            // Buscar por nombre
            List<Cliente> clientesNombre = clienteDAO.buscarPorNombre("Ana");
            System.out.println("Clientes con nombre Ana: " + clientesNombre);

            // Actualizar cliente
            c1.setApellido("Perez-Modificado");
            clienteDAO.actualizarCliente(c1);
            System.out.println("Cliente actualizado: " + clienteDAO.buscarPorId(c1.getIdCliente()));

            // === PRUEBAS DE PRODUCTOS ===
            Producto prod1 = new Producto("Camiseta", 20.5, Producto.categorias.CATEGORIA1, 10, p1);
            Producto prod2 = new Producto("Pantalón", 35.0, Producto.categorias.CATEGORIA2, 5, p2);
            productoDAO.agregarProducto(prod1);
            productoDAO.agregarProducto(prod2);

            // Listar productos
            List<Producto> listaProd = productoDAO.listar();
            System.out.println("Lista de productos: " + listaProd);

            // Buscar por ID
            Producto productoBuscado = productoDAO.buscarPorId(prod1.getIdProducto());
            System.out.println("Producto buscado: " + productoBuscado);

            // Calcular valor de inventario
            double valorInventario = productoDAO.calcularValorInventario();
            System.out.println("Valor total inventario: " + valorInventario);

            // Listar productos por categoría
            List<Producto> prodCat = productoDAO.listarPorCategoria("CATEGORIA1");
            System.out.println("Productos categoría CATEGORIA1: " + prodCat);

            // Actualizar producto
            prod1.setPrecio(22.0);
            productoDAO.actualizarProducto(prod1);
            System.out.println("Producto actualizado: " + productoDAO.buscarPorId(prod1.getIdProducto()));

            // Productos suministrados por un proveedor
            List<Producto> productosProv = proveedorDAO.productosSuministrados(p1.getIdProveedor());
            System.out.println("Productos suministrados por " + p1.getNombre() + ": " + productosProv);

            // === ELIMINACIONES ===
            productoDAO.eliminarProducto(prod2.getIdProducto());
            clienteDAO.eliminarCliente(c2.getIdCliente());
            proveedorDAO.eliminarProveedor(p2.getIdProveedor());

            System.out.println("Después de eliminar:");
            System.out.println("Clientes: " + clienteDAO.listarClientes());
            System.out.println("Proveedores: " + proveedorDAO.listarProveedores());
            System.out.println("Productos: " + productoDAO.listar());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        */
        try {

            System.out.println("===== INSERTANDO PROVEEDORES =====");

            Proveedor pr1 = new Proveedor("ProveedorA", "EmpresaA");
            Proveedor pr2 = new Proveedor("ProveedorB", "EmpresaB");

            proveedorDAO.agregarProveedor(pr1);
            proveedorDAO.agregarProveedor(pr2);

            System.out.println("Proveedores insertados correctamente\n");


            System.out.println("===== INSERTANDO PRODUCTOS =====");

            // Producto sin descuento (hoy)
            Producto p1 = new Producto(
                    "ProductoNormal",
                    100,
                    Producto.categorias.CATEGORIA1,
                    10,
                    pr1,
                    0.0
            );

            // Producto con 10% descuento (hoy)
            Producto p2 = new Producto(
                    "ProductoDescuento10",
                    200,
                    Producto.categorias.CATEGORIA2,
                    5,
                    pr1,
                    10.0
            );

            // Producto con 20% descuento (fecha antigua)
            Producto p3 = new Producto(
                    "ProductoAntiguo",
                    150,
                    Producto.categorias.CATEGORIA3,
                    8,
                    pr2,
                    20.0
            );

            // Forzamos fecha antigua manualmente
            p3.setFechaAlta(LocalDate.now().minusDays(15));

            productoDAO.agregarProducto(p1);
            productoDAO.agregarProducto(p2);
            productoDAO.agregarProducto(p3);

            System.out.println("Productos insertados correctamente\n");


            System.out.println("===== PRODUCTOS CON PRECIO FINAL =====");
            List<Producto> todos = productoDAO.listar();

            for (Producto p : todos) {
                System.out.println(
                        p.getNombre() +
                                " | Precio base: " + p.getPrecio() +
                                " | Descuento: " + p.getDescuento() + "%" +
                                " | Precio final: " + p.getPrecioFinal()
                );
            }


            System.out.println("\n===== PRODUCTOS ÚLTIMOS 7 DÍAS =====");

            List<Producto> recientes = productoDAO.productosUltimosDias(7);

            for (Producto p : recientes) {
                System.out.println(p.getNombre() +
                        " | Fecha alta: " + p.getFechaAlta());
            }


            System.out.println("\n===== PROVEEDOR CON MAYOR FACTURACIÓN =====");

            Proveedor top = proveedorDAO.proveedorMayorFacturacion();

            System.out.println("Proveedor con mayor facturación: "
                    + top.getNombre() +
                    " (" + top.getEmpresa() + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
