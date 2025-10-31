package Interfaces;
import Clases.*;

import java.util.ArrayList;

public interface PedidoProveedorDAO {

    void agregarPedido(PedidoProveedor p);

    void eliminarPedido(PedidoProveedor p);

    void actualizarPedido(PedidoProveedor p);

    ArrayList<PedidoProveedor> listarPedidos();

    PedidoProveedor buscarPorId(int id);

    ArrayList<PedidoProveedor> listarPorProveedor(int idProveedor);

    double totalComprado();

    ArrayList<PedidoProveedor> listarPorFecha(String fecha);


}
