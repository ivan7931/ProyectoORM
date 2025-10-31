package DAOFile;

import Clases.Producto;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class ProductoHandler extends DefaultHandler {
    private ArrayList<Producto> productos = new ArrayList<>();
    private Producto producto;
    private StringBuilder valores = new StringBuilder();

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("producto")) {
            producto = new Producto();
        }
        valores.setLength(0);
    }

    public void characters(char[] ch, int start, int length) {
        valores.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) {
        if (producto != null) {
            switch (qName.toLowerCase()) {
                case "nombre": producto.setNombre(valores.toString()); break;
                case "precio": producto.setPrecio(Float.parseFloat(valores.toString())); break;
                case "categoria": producto.setCategoria(Producto.categorias.valueOf(valores.toString())); break;
                case "cantidad": producto.setCantidad(Integer.parseInt(valores.toString())); break;
                case "id_producto": producto.setIdProducto(Integer.parseInt(valores.toString())); break;
                case "producto": productos.add(producto); break;
            }
        }
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

}
