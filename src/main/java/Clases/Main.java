package Clases;

import DAOFile.ClienteDAOImpl_XML;
import DAOFile.ProductoDAOImpl_XML;
import DAOFile.ProveedorDAOImpl_XML;
import Excepciones.DataAccessException;
import JSONFile.ClienteDAOImpl_JSON;
import JSONFile.ProductoDAOImpl_JSON;
import JSONFile.ProveedorDAOImpl_JSON;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args)throws IOException, DataAccessException {
        Producto p1 = new Producto("Manzanas", 2.50, Producto.categorias.CATEGORIA2, 100);
        Producto p2 = new Producto("Laptop", 1200.50, Producto.categorias.CATEGORIA1, 10);
        Producto p3 = new Producto("Silla de oficina", 85.99, Producto.categorias.CATEGORIA3, 20);
        Producto p4 = new Producto("Camiseta", 15.50, Producto.categorias.CATEGORIA5, 50);
        Producto p5 = new Producto("Manzanas", 2.50, Producto.categorias.CATEGORIA2, 100);
        Producto p6 = new Producto("Auriculares", 59.90, Producto.categorias.CATEGORIA4, 30);
        Producto p7 = new Producto("Smartphone", 899.99, Producto.categorias.CATEGORIA1, 25);
        Producto p8 = new Producto("Tablet", 499.50, Producto.categorias.CATEGORIA1, 15);
        Producto p9 = new Producto("Peras", 3.10, Producto.categorias.CATEGORIA2, 80);
        Producto p10 = new Producto("Plátanos", 2.20, Producto.categorias.CATEGORIA2, 120);
        Producto p11 = new Producto("Mesa de comedor", 250.00, Producto.categorias.CATEGORIA3, 8);
        Producto p12 = new Producto("Estantería", 120.75, Producto.categorias.CATEGORIA3, 12);
        Producto p13 = new Producto("Teclado inalámbrico", 45.99, Producto.categorias.CATEGORIA4, 25);
        Producto p14 = new Producto("Ratón ergonómico", 35.50, Producto.categorias.CATEGORIA4, 40);
        Producto p15 = new Producto("Pantalones", 29.90, Producto.categorias.CATEGORIA5, 60);
        Producto p16 = new Producto("Chaqueta", 79.99, Producto.categorias.CATEGORIA5, 20);
        ProductoDAOImpl_JSON productoDAO = new ProductoDAOImpl_JSON();
        productoDAO.agregarProducto(p1);
        productoDAO.agregarProducto(p2);
        productoDAO.agregarProducto(p3);
        productoDAO.agregarProducto(p4);
        productoDAO.agregarProducto(p5);
        productoDAO.agregarProducto(p6);
        productoDAO.agregarProducto(p7);
        productoDAO.agregarProducto(p8);
        productoDAO.agregarProducto(p9);
        productoDAO.agregarProducto(p10);
        productoDAO.agregarProducto(p11);
        productoDAO.agregarProducto(p12);
        productoDAO.agregarProducto(p13);
        productoDAO.agregarProducto(p14);
        productoDAO.agregarProducto(p15);
        productoDAO.agregarProducto(p16);
        System.out.println(productoDAO.calcularValorInventario());
        //productoDAO.eliminarProducto(6);
        System.out.println(productoDAO.buscarPorId(4));
        System.out.println(productoDAO.buscarPorId(6));
        System.out.println(productoDAO.calcularValorInventario());
        ArrayList<Producto> listaprod = new ArrayList<>();
        listaprod= productoDAO.listar();
        listaprod = productoDAO.listarPorCategoria("categoria2");
        for(Producto p : listaprod){
            System.out.println(p);
        }
        ArrayList<Producto> lista1 = new ArrayList<>();
        ProductoDAOImpl_XML prodDAO = new ProductoDAOImpl_XML();
        prodDAO.agregarProducto(p1);
        prodDAO.agregarProducto(p2);
        prodDAO.agregarProducto(p3);
        prodDAO.agregarProducto(p4);
        prodDAO.agregarProducto(p5);
        prodDAO.agregarProducto(p6);
        prodDAO.agregarProducto(p7);
        prodDAO.agregarProducto(p8);
        prodDAO.agregarProducto(p9);
        prodDAO.agregarProducto(p10);
        prodDAO.agregarProducto(p11);
        prodDAO.agregarProducto(p12);
        prodDAO.agregarProducto(p13);
        prodDAO.agregarProducto(p14);
        prodDAO.agregarProducto(p15);
        prodDAO.agregarProducto(p16);
        lista1 =prodDAO.listar();
        for(Producto p : lista1){
            System.out.println(p.toString());
        }
        System.out.println(prodDAO.buscarPorId(7));
        System.out.println(prodDAO.calcularValorInventario());
        ArrayList<Producto> porCategoria = new ArrayList<>();
        porCategoria = prodDAO.listarPorCategoria("CATEGORIA3");
        for (Producto p : porCategoria) {
            System.out.println(p.toString());
        }
        Cliente c1 = new Cliente("Ana", "Gómez");
        Cliente c2 = new Cliente("Luis", "Pérez");
        Cliente c3 = new Cliente("María", "Rodríguez");
        Cliente c4 = new Cliente("Carlos", "Sánchez");
        Cliente c5 = new Cliente("Laura", "Fernández");
        Cliente c6 = new Cliente("Javier", "Martínez");
        Cliente c7 = new Cliente("Sofía", "López");
        Cliente c8 = new Cliente("Miguel", "Hernández");
        Cliente c9 = new Cliente("Elena", "García");
        Cliente c10 = new Cliente("Diego", "Ramírez");
        Cliente c11 = new Cliente("Valeria", "Torres");
        Cliente c12 = new Cliente("Andrés", "Vega");
        //CLIENTES TO JSON
        ClienteDAOImpl_JSON clienteJSON = new ClienteDAOImpl_JSON();
        ArrayList<Cliente> listaJSON = new ArrayList<>();
        listaJSON= clienteJSON.listarClientes();
        clienteJSON.eliminarCliente(5);
        listaJSON= clienteJSON.listarClientes();
        System.out.println(clienteJSON.contarClientes());
        clienteJSON.agregarCliente(c1);
        clienteJSON.agregarCliente(c2);
        clienteJSON.agregarCliente(c3);
        clienteJSON.agregarCliente(c4);
        clienteJSON.agregarCliente(c5);
        clienteJSON.agregarCliente(c6);
        clienteJSON.agregarCliente(c7);
        clienteJSON.agregarCliente(c8);
        clienteJSON.agregarCliente(c9);
        clienteJSON.agregarCliente(c10);
        clienteJSON.agregarCliente(c11);
        clienteJSON.agregarCliente(c12);
        listaJSON= clienteJSON.listarClientes();
        for(Cliente c : listaJSON) {
            System.out.println(c);
        }
        ClienteDAOImpl_XML clienteDAO = new ClienteDAOImpl_XML();
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        clienteDAO.agregarCliente(c1);
        clienteDAO.agregarCliente(c2);
        clienteDAO.agregarCliente(c3);
        clienteDAO.agregarCliente(c4);
        clienteDAO.agregarCliente(c5);
        clienteDAO.agregarCliente(c6);
        clienteDAO.agregarCliente(c7);
        clienteDAO.agregarCliente(c8);
        clienteDAO.agregarCliente(c9);
        clienteDAO.agregarCliente(c10);
        clienteDAO.agregarCliente(c11);
        clienteDAO.agregarCliente(c12);
        clienteDAO.eliminarCliente(12);
        listaClientes=clienteDAO.listarClientes();
        for(Cliente c : listaClientes) {
            System.out.println(c);
        }
        Proveedor prov1 = new Proveedor("Juan Pérez", "Frutas y Verduras S.A.");
        Proveedor prov2 = new Proveedor("Laura Gómez", "Electrónica Global");
        Proveedor prov3 = new Proveedor("Carlos Ramírez", "Muebles y Hogar S.L.");
        Proveedor prov4 = new Proveedor("Ana Torres", "Ropa y Moda S.A.");
        Proveedor prov5 = new Proveedor("Miguel Sánchez", "Tecnología Avanzada");
        Proveedor prov6 = new Proveedor("Lucía Fernández", "Alimentos Naturales");
        ArrayList<Proveedor> listaProveedor = new ArrayList<>();
        ProveedorDAOImpl_JSON proveedorJSON = new ProveedorDAOImpl_JSON();
        proveedorJSON.agregarProveedor(prov1);
        proveedorJSON.agregarProveedor(prov2);
        proveedorJSON.agregarProveedor(prov3);
        proveedorJSON.agregarProveedor(prov4);
        proveedorJSON.agregarProveedor(prov5);
        proveedorJSON.agregarProveedor(prov6);
        proveedorJSON.eliminarProveedor(4);
        System.out.println(proveedorJSON.contarProveedores());
        System.out.println(proveedorJSON.buscarPorId(3));
        System.out.println(proveedorJSON.buscarPorId(4));
        listaProveedor= proveedorJSON.listarProveedores();
        for(Proveedor p :  listaProveedor){
            System.out.println(p);
        }
        ProveedorDAOImpl_XML proveedorDAO = new ProveedorDAOImpl_XML();
        proveedorDAO.agregarProveedor(prov1);
        proveedorDAO.agregarProveedor(prov2);
        proveedorDAO.agregarProveedor(prov3);
        proveedorDAO.agregarProveedor(prov4);
        proveedorDAO.agregarProveedor(prov5);
        proveedorDAO.agregarProveedor(prov6);
        listaProveedor=proveedorDAO.listarProveedores();
        for(Proveedor p : listaProveedor) {
            System.out.println(p.toString());
        }

    }
}
