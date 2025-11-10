package Menu;

import Clases.Cliente;
import Clases.Producto;
import Clases.Proveedor;
import DAOFile.*;
import Interfaces.ClienteDAO;
import Interfaces.ProductoDAO;
import Interfaces.ProveedorDAO;
import JSONFile.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int opcion = 0;
        int modo = 1;
        do {
            System.out.println("===== MENÚ INICIAL =====");
            System.out.println("Selecciona una opcion:");
            System.out.println("1. XML");
            System.out.println("2. JSON");
            modo = leerEntero();
        } while (modo != 1 && modo != 2);
        do{
            try {
                System.out.println("===== MENÚ PRINCIPAL =====");
                System.out.println("Selecciona una opcion:");
                System.out.println("1. Gestionar Clientes");
                System.out.println("2. Gestionar Productos");
                System.out.println("3. Gestionar Proveedores");
                System.out.println("4. Salir");
                opcion = leerEntero();//llamamos a la funcion leeEntero para controlar que el dato introducido sea valido
                switch (opcion) {
                    case 1:
                        ClienteDAO clienteDAO;
                        if (modo == 1){ //comprobamos que modo habia elegido para saber que interfaz crear
                            clienteDAO = new ClienteDAOImpl_XML();
                        } else{
                            clienteDAO = new ClienteDAOImpl_JSON();
                        }
                        menuClientes(clienteDAO);
                        break;
                    case 2:
                        ProductoDAO productoDAO;
                        if (modo == 1){ //comprobamos que modo habia elegido para saber que interfaz crear
                            productoDAO = new ProductoDAOImpl_XML();
                        } else{
                            productoDAO = new ProductoDAOImpl_JSON();
                        }
                        menuProductos(productoDAO);
                        break;
                    case 3:
                        ProveedorDAO proveedorDAO;
                        if (modo == 1){ //comprobamos que modo habia elegido para saber que interfaz crear
                            proveedorDAO = new ProveedorDAOImpl_XML();
                        } else{
                            proveedorDAO = new ProveedorDAOImpl_JSON();
                        }
                        menuProveedores(proveedorDAO);
                        break;
                    case 4:
                        System.out.println("Saliendo..."); break;
                    default: System.out.println("Error, elija una opcion correcta"); break;
                }
            } catch (InputMismatchException | IOException ex) {
                System.err.println("Error: " +  ex.getMessage());
            }
        } while (opcion != 4);
    }

    public static void menuClientes(ClienteDAO clienteDAO) throws IOException {
        int opcion = 0;
        do{
            System.out.println("\n===== MENÚ CLIENTES =====");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Volver");
            opcion = leerEntero();
            switch(opcion){
                case 1:  //agregar cliente
                    System.out.print("Nombre: ");
                    String nombre = in.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = in.nextLine();
                    clienteDAO.agregarCliente(new Cliente(nombre, apellido));
                    System.out.println("Cliente agregado correctamente.");
                    break;
                 //listar todos los clientes del archivo
                case 2: clienteDAO.listarClientes().forEach(System.out::println); break;
                case 3:  //buscar un cliente por su id
                    System.out.print("ID del cliente: ");
                    int id = leerEntero();
                    Cliente c = clienteDAO.buscarPorId(id);
                    System.out.println(c != null ? c : "No encontrado"); break;

                case 4: //actualizar un cliente del que sabemos el id
                    System.out.print("ID del cliente a actualizar: ");
                    int id2 = leerEntero();
                    Cliente c2 = clienteDAO.buscarPorId(id2);
                    if (c2 != null) {
                        System.out.print("Nuevo nombre: ");
                        c2.setNombre(in.nextLine());
                        System.out.print("Nuevo apellido: ");
                        c2.setApellido(in.nextLine());
                        clienteDAO.actualizarCliente(c2);
                        System.out.println("Actualizado correctamente."); break;
                    } else {
                        System.out.println("Cliente no encontrado."); break;
                    }

                case 5:  //eliminar un cliente
                    System.out.print("ID del cliente a eliminar: ");
                    int id3 = leerEntero();
                    clienteDAO.eliminarCliente(id3);
                    System.out.println("Cliente eliminado."); break;

                case 6: System.out.println("Volviendo..."); break;
                default: System.out.println("Error, elija una opcion correcta"); break;

            }
        } while (opcion != 6);
    }
    public static void menuProductos(ProductoDAO productoDAO) throws IOException {
        int opcion = 0;
        do{
            System.out.println("\n===== MENÚ PRODUCTOS =====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Calcular valor inventario");
            System.out.println("5. Volver");
            opcion = leerEntero();
            switch(opcion){
                case 1 : //Agregamos un producto
                    System.out.print("Nombre: ");
                    String nombre = in.nextLine();
                    System.out.print("Precio: ");
                    double precio = leerDouble();
                    System.out.print("Cantidad: ");
                    int cantidad = leerEntero();
                    System.out.print("Categoría (CATEGORIA1, CATEGORIA2, CATEGORIA3, CATEGORIA4, CATEGORIA5): ");
                    String cat = in.nextLine().toUpperCase();
                    System.out.print("ID del proveedor: ");
                    int idProveedor = leerEntero();
                    try {
                        productoDAO.agregarProducto(
                                new Producto(nombre, precio, Producto.categorias.valueOf(cat), cantidad, idProveedor)
                        );
                        System.out.println("Producto agregado correctamente");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: categoría no válida");
                    }
                    break;
                case 2 :
                    for (Producto producto : productoDAO.listar()){
                        System.out.println(producto);
                    }
                    break;
                case 3:
                    System.out.print("ID del producto: ");
                    int id = leerEntero();
                    productoDAO.eliminarProducto(id);
                    System.out.println("Producto eliminado."); break;

                case 4: System.out.println("Valor total inventario: " + productoDAO.calcularValorInventario()); break;
                case 5:
                    System.out.println("Volviendo..."); break;
                default:
                    System.out.println("Error, elija una opcion correcta"); break;
            }
        } while (opcion != 5);

    }
    public static void menuProveedores(ProveedorDAO proveedorDAO) throws IOException {
        int opcion = 0;
        do{
            System.out.println("\n===== MENÚ PROVEEDORES =====");
            System.out.println("1. Agregar proveedor");
            System.out.println("2. Listar proveedores");
            System.out.println("3. Buscar proveedor por ID");
            System.out.println("4. Actualizar proveedor");
            System.out.println("5. Eliminar proveedor");
            System.out.println("6. Listar productos suministrados por un proveedor");
            System.out.println("7. Volver");
            opcion = leerEntero();
            switch(opcion){
                case 1:
                    System.out.print("Nombre del proveedor: ");
                    String nombre = in.nextLine();
                    System.out.print("Empresa: ");
                    String empresa = in.nextLine();
                    proveedorDAO.agregarProveedor(new Proveedor(nombre, empresa));
                    System.out.println("Proveedor agregado correctamente."); break;
                case 2 : proveedorDAO.listarProveedores().forEach(System.out::println); break;
                case 3 :
                    System.out.print("ID del proveedor: ");
                    int id = leerEntero();
                    Proveedor p = proveedorDAO.buscarPorId(id);
                    System.out.println(p != null ? p : "Proveedor no encontrado."); break;

                case 4 :
                    System.out.print("ID del proveedor a actualizar: ");
                    int id1 = leerEntero();
                    Proveedor p1 = proveedorDAO.buscarPorId(id1);
                    if (p1 != null) {
                        System.out.print("Nuevo nombre: ");
                        p1.setNombre(in.nextLine());
                        System.out.print("Nueva empresa: ");
                        p1.setEmpresa(in.nextLine());
                        proveedorDAO.actualizarProveedor(p1);
                        System.out.println("Proveedor actualizado correctamente."); break;
                    } else {
                        System.out.println("Proveedor no encontrado."); break;
                    }

                case 5 :
                    System.out.print("ID del proveedor a eliminar: ");
                    int id2 = leerEntero();
                    proveedorDAO.eliminarProveedor(id2);
                    System.out.println("Proveedor eliminado."); break;

                case 6 :
                    System.out.print("ID del proveedor: ");
                    int id3 = leerEntero();
                    // Usamos productosSuministrados
                    ArrayList<Producto> productos = proveedorDAO.productosSuministrados(id3);
                    if (productos == null || productos.isEmpty()) {
                        System.out.println("No hay productos suministrados por este proveedor."); break;
                    } else {
                        System.out.println("Productos suministrados:");
                        for (Producto producto : productos) {
                            System.out.println(producto);
                        }
                         break;
                    }

                case 7 : System.out.println("Volviendo al menú principal..."); break;
                default:
                    System.out.println("Error, elija una opcion correcta"); break;
            }
        } while (opcion != 7);
    }
    private static int leerEntero() {
        while (true) {
            try {
                int valor = Integer.parseInt(in.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error se debe ingresar un número entero. Intente otra vez: ");
            }
        }
    }
    private static double leerDouble() {
        while (true) {
            try {
                double valor = Double.parseDouble(in.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error se debe ingresar un número decimal. Intente otra vez: ");
            }
        }
    }
}
