package DAOFile;

import Clases.Cliente;
import Clases.Producto;
import Clases.Proveedor;
import Interfaces.ProductoDAO;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProductoDAOImpl_XML implements ProductoDAO {
    private final File archivoXML = new File("Productos.xml");

    public ProductoDAOImpl_XML() throws IOException {

    }

    @Override
    public void agregarProducto(Producto p) throws IOException {
        ArrayList <Producto> listaProductos = listar();
        int nuevoID;
        if (!listaProductos.isEmpty()) {
            int maxID = 0;
            for (Producto producto : listaProductos) {
                if (producto.getIdProducto() > maxID) {
                    maxID = producto.getIdProducto();
                }
            }
            nuevoID = maxID + 1;
            p.setIdProducto(nuevoID);
        }
        listaProductos.add(p);
        guardarXML(listaProductos);
    }

    @Override
    public void eliminarProducto(int id) throws IOException {
        ArrayList <Producto> listaProductos = listar();
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getIdProducto() == id) {
                listaProductos.remove(i);
                break;
            }
        }
        guardarXML(listaProductos);
    }

    @Override
    public void actualizarProducto(Producto p) throws IOException {
        ArrayList <Producto> listaProductos = listar();
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getIdProducto() == p.getIdProducto()) {
                listaProductos.set(i, p);
                break;
            }
        }
        guardarXML(listaProductos);
    }

    @Override
    public ArrayList<Producto> listar() throws IOException {
        ArrayList<Producto> listaProductos = new ArrayList<>();
        if (!archivoXML.exists()||archivoXML.length() == 0) {
            return listaProductos;
        }

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            ProductoHandler handler = new ProductoHandler();
            parser.parse(archivoXML, handler);
            listaProductos = handler.getProductos();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error al leer el XML",e);
        }
        return listaProductos;
    }

    @Override
    public Producto buscarPorId(int id) throws IOException {
        ArrayList<Producto> listaProductos = listar();
        for( Producto producto : listaProductos ) {
            if (producto.getIdProducto() == id) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public double calcularValorInventario() throws IOException {
        ArrayList<Producto> listaProductos = listar();
        double ValorInventario = 0;
        for( Producto producto : listaProductos ) {
            ValorInventario += producto.getPrecio() * producto.getCantidad();
        }
        return ValorInventario;
    }

    @Override
    public ArrayList<Producto> listarPorCategoria(String categoria) throws IOException {
        ArrayList<Producto> listaProductos = listar();
        ArrayList<Producto> productosPorCategoria = new ArrayList<>();
        for( Producto producto : listaProductos ) {
            if (producto.getCategoria().equals(Producto.categorias.valueOf(categoria))) {
                productosPorCategoria.add(producto);
            }
        }
        return productosPorCategoria;
    }

    public void guardarXML(ArrayList<Producto> ListaProductos) throws IOException {
        /*try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoXML))) {
            bw.write("<productos>\n");
            for (Producto p : ListaProductos) {
                bw.write("  <producto>\n");
                bw.write("      <nombre>" + p.getNombre() + "</nombre>\n");
                bw.write("      <precio>" + p.getPrecio() + "</precio>\n");
                bw.write("      <categoria>" + p.getCategoria() + "</categoria>\n");
                bw.write("      <cantidad>" + p.getCantidad() + "</cantidad>\n");
                bw.write("      <id>" + p.getIdProducto() + "</id>\n");
                bw.write("  </producto>\n");
            }
            bw.write("</productos>\n");
        }*/
        try {
            // creamos el builder que nos permiterá crear documentos, etc.
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //obtenemos una implementación del DOM
            DOMImplementation implementation = builder.getDOMImplementation();
            //creamos el documento en memoria
            // Creamos el documento
            //la siguiente linea crea el elemento raiz --> <prodcutos>...van los atributos de diferentes productos...</productos>
            Document documento = implementation.createDocument(null, "productos", null);
            // establecemos la version de XML
            documento.setXmlVersion("1.0");
            // Obtenemos el elemento raíz
            Element raiz = documento.getDocumentElement();
            //creamos nodos de productos y los añadimos al elemento raiz
            for(Producto p : ListaProductos) {
                Element productoXML = crearProducto(p, documento);
                raiz.appendChild(productoXML);
            }
            //la parte de arriba crea la estructura en memoria
            //generamos/escribimos el fichero
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transf = transformerFactory.newTransformer();
            // establecemos codificación y pedimos que indente.
            transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transf.setOutputProperty(OutputKeys.INDENT, "yes");
            transf.setOutputProperty("{http:///xml.apache.org/xslt}indent-amount", "2");
            // crea el árbol para una transformación.
            DOMSource source = new DOMSource(documento);
            // Mostrar los resultados por consola y escribirlos en el archivo.
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(archivoXML);
            transf.transform(source, console);
            transf.transform(source, file);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //metodo auxiliar que convierte el objeto a XML
    public static Element crearProducto(Producto p, Document documento) {
        Element prod = documento.createElement("producto");
        prod.appendChild(crearElemento("nombre",p.getNombre(),documento));
        prod.appendChild(crearElemento("precio",""+p.getPrecio(),documento));
        prod.appendChild(crearElemento("categoria",""+p.getCategoria(),documento));
        prod.appendChild(crearElemento("cantidad",""+p.getCantidad(),documento));
        prod.appendChild(crearElemento("id_producto",""+p.getIdProducto(),documento));
        return prod;
    }
    public static Element crearElemento(String nombre, String valor, Document documento) {
        Element elemento = documento.createElement(nombre);
        Text texto = documento.createTextNode(valor);
        elemento.appendChild(texto);
        return elemento;

    }
}
