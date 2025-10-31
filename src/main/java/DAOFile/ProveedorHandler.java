package DAOFile;

import Clases.Proveedor;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class ProveedorHandler extends DefaultHandler {
    ArrayList<Proveedor> ListaProveedores = new ArrayList<>();
    private Proveedor proveedor;
    private StringBuilder valores = new StringBuilder();

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("proveedor")) {
            proveedor = new Proveedor();
        }
        valores.setLength(0);
    }

    public void characters(char[] ch, int start, int length) {
        valores.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) {
        if (proveedor != null) {
            switch (qName.toLowerCase()) {
                case "nombre": proveedor.setNombre(valores.toString()); break;
                case "empresa": proveedor.setEmpresa(valores.toString()); break;
                case "id_proveedor": proveedor.setIdProveedor(Integer.parseInt(valores.toString())); break;
                case "proveedor": ListaProveedores.add(proveedor); break;
            }
        }
    }

    public ArrayList<Proveedor> getListaProveedores() {
        return ListaProveedores;
    }
}
