package jdbc;

import Clases.Cliente;
import Excepciones.DataAccessException;

import java.util.ArrayList;

public class MainJDBC {
    public static void main(String[] args) throws DataAccessException {
        Cliente c1 = new Cliente("prueba1","prueba1");
        ClienteHibernate cJDBC = new ClienteHibernate();
        /*ArrayList<Cliente> listaClientes = cJDBC.listarClientes();
        for(Cliente c:listaClientes){
            System.out.println(c);
        }*/
        Cliente cBuscado = cJDBC.buscarPorId(2);
        System.out.println(cBuscado);
        System.out.println(cJDBC.contarClientes());
        /*ArrayList<Cliente> listaMismoNombre = cJDBC.buscarPorNombre("ejemplo");
        for (Cliente c : listaMismoNombre) {
            System.out.println(c);
        }
*/
    }
}
