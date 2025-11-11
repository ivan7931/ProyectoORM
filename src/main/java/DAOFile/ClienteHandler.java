package DAOFile;

import Clases.Cliente;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class ClienteHandler extends DefaultHandler {

    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private Cliente cliente;
    private final StringBuilder valores = new StringBuilder();

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("cliente")) {
            cliente = new Cliente();
        }
        valores.setLength(0);
    }

    public void characters(char[] ch, int start, int length) {
        valores.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) {
        if(cliente != null) {
            switch (qName.toLowerCase()) {
                case "nombre": cliente.setNombre(valores.toString()); break;
                case "apellido": cliente.setApellido(valores.toString()); break;
                case "id": cliente.setId(Integer.parseInt(valores.toString())); break;
                case "cliente": clientes.add(cliente); break;
            }
        }
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
}
