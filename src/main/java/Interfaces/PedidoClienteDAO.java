package Interfaces;
import Clases.*;
import java.util.ArrayList;

public interface PedidoClienteDAO {
    void agregarPedido(PedidoCliente p);

    void eliminarPedido(PedidoCliente p);

    void modificarPedido(PedidoCliente p);

    ArrayList<PedidoCliente> listarPedidos();

    PedidoCliente buscarPedidoPorId(int id);

    ArrayList<PedidoCliente> listarPedidosPorCliente(int id);

    double totalVendido();

    ArrayList<PedidoCliente> listarPedidosPorFecha(String fecha);
}
