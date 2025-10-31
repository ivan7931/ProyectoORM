package Menu;

import Clases.Cliente;
import Clases.Producto;
import Clases.Proveedor;
import DAOFile.ClienteDAOImpl_XML;
import DAOFile.ProductoDAOImpl_XML;
import DAOFile.ProveedorDAOImpl_XML;
import Interfaces.ClienteDAO;
import Interfaces.ProductoDAO;
import Interfaces.ProveedorDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        int opcion = 0;
        do{
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("Selecciona una opcion:");
            System.out.println("1. Gestionar Clientes");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Proveedores");
            System.out.println("4. Salir");
            opcion = in.nextInt();
            in.nextLine();
            switch(opcion){
                case 1:
                    ClienteDAO clienteDAO = new ClienteDAOImpl_XML();
                    menuClientes(clienteDAO);
                case 2:
                    ProductoDAO productoDAO = new ProductoDAOImpl_XML();
                    menuProductos(productoDAO);
                case 3:
                    ProveedorDAO proveedorDAO = new ProveedorDAOImpl_XML();
                    menuProveedores(proveedorDAO);
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
            opcion = in.nextInt();
            in.nextLine();
            switch(opcion){
                case 1:  //agregar cliente
                    System.out.print("Nombre: ");
                    String nombre = in.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = in.nextLine();
                    clienteDAO.agregarCliente(new Cliente(nombre, apellido));
                    System.out.println("Cliente agregado correctamente.");
                 //listar todos los clientes del archivo
                case 2: clienteDAO.listarClientes().forEach(System.out::println);
                case 3:  //buscar un cliente por su id
                    System.out.print("ID del cliente: ");
                    int id = in.nextInt();
                    Cliente c = clienteDAO.buscarPorId(id);
                    System.out.println(c != null ? c : "No encontrado");

                case 4: //actualizar un cliente del que sabemos el id
                    System.out.print("ID del cliente a actualizar: ");
                    int id2 = in.nextInt();
                    in.nextLine();
                    Cliente c2 = clienteDAO.buscarPorId(id2);
                    if (c2 != null) {
                        System.out.print("Nuevo nombre: ");
                        c2.setNombre(in.nextLine());
                        System.out.print("Nuevo apellido: ");
                        c2.setApellido(in.nextLine());
                        clienteDAO.actualizarCliente(c2);
                        System.out.println("Actualizado correctamente.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }

                case 5:  //eliminar un cliente
                    System.out.print("ID del cliente a eliminar: ");
                    int id3 = in.nextInt();
                    clienteDAO.eliminarCliente(id3);
                    System.out.println("Cliente eliminado.");

                case 6: System.out.println("Volviendo...");

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
            opcion = in.nextInt();
            in.nextLine();
            switch(opcion){
                case 1 :
                    System.out.print("Nombre: ");
                    String nombre = in.nextLine();
                    System.out.print("Precio: ");
                    double precio = in.nextDouble();
                    System.out.print("Cantidad: ");
                    int cantidad = in.nextInt();
                    in.nextLine();
                    System.out.print("Categoría (TECNOLOGIA, ALIMENTACION, LIMPIEZA, HOGAR): ");
                    String cat = in.nextLine();
                    productoDAO.agregarProducto(new Producto(nombre, precio, Producto.categorias.valueOf(cat), cantidad));
                    System.out.println("Producto agregado correctamente.");

                case 2 : productoDAO.listar().forEach(System.out::println);
                case 3:
                    System.out.print("ID del producto: ");
                    int id = in.nextInt();
                    productoDAO.eliminarProducto(id);
                    System.out.println("Producto eliminado.");

                case 4: System.out.println("Valor total inventario: " + productoDAO.calcularValorInventario());
                case 5:
                    System.out.println("Volviendo...");
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
            opcion = in.nextInt();
            in.nextLine();
            switch(opcion){
                case 1 -> {
                    System.out.print("Nombre del proveedor: ");
                    String nombre = in.nextLine();
                    System.out.print("Empresa: ");
                    String empresa = in.nextLine();
                    proveedorDAO.agregarProveedor(new Proveedor(nombre, empresa));
                    System.out.println("Proveedor agregado correctamente.");
                }
                case 2 -> proveedorDAO.listarProveedores().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID del proveedor: ");
                    int id = in.nextInt();
                    in.nextLine();
                    Proveedor p = proveedorDAO.buscarPorId(id);
                    System.out.println(p != null ? p : "Proveedor no encontrado.");
                }
                case 4 -> {
                    System.out.print("ID del proveedor a actualizar: ");
                    int id = in.nextInt();
                    in.nextLine();
                    Proveedor p = proveedorDAO.buscarPorId(id);
                    if (p != null) {
                        System.out.print("Nuevo nombre: ");
                        p.setNombre(in.nextLine());
                        System.out.print("Nueva empresa: ");
                        p.setEmpresa(in.nextLine());
                        proveedorDAO.actualizarProveedor(p);
                        System.out.println("Proveedor actualizado correctamente.");
                    } else {
                        System.out.println("Proveedor no encontrado.");
                    }
                }
                case 5 -> {
                    System.out.print("ID del proveedor a eliminar: ");
                    int id = in.nextInt();
                    in.nextLine();
                    proveedorDAO.eliminarProveedor(id);
                    System.out.println("Proveedor eliminado.");
                }
                case 6 -> {
                    System.out.print("ID del proveedor: ");
                    int id = in.nextInt();
                    in.nextLine();
                    // Usamos productosSuministrados
                    ArrayList<Producto> productos = proveedorDAO.productosSuministrados(id);
                    if (productos == null || productos.isEmpty()) {
                        System.out.println("No hay productos suministrados por este proveedor.");
                    } else {
                        System.out.println("Productos suministrados:");
                        productos.forEach(System.out::println);
                    }
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
            }
        } while (opcion != 7);
    }
}
