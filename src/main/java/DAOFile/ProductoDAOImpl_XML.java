package DAOFile;

import Clases.Producto;
import Excepciones.DataAccessException;
import Excepciones.DataNotFoundException;
import Excepciones.DataReadException;
import Excepciones.DataWriteException;
import Interfaces.ProductoDAO;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProductoDAOImpl_XML implements ProductoDAO {
    private final File archivoXML = new File("Productos.xml");

    public ProductoDAOImpl_XML() {

    }

    @Override
    public void agregarProducto(Producto p) throws DataAccessException {
        try {
            ArrayList<Producto> listaProductos = listar();
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
        } catch (Exception e) {
            throw new DataWriteException("Error al agregar el producto", e);
        }
    }

    @Override
    public void eliminarProducto(int id) throws DataAccessException {
        try {
            ArrayList<Producto> listaProductos = listar();
            boolean eliminado = false;
            for (int i = 0; i < listaProductos.size(); i++) {
                if (listaProductos.get(i).getIdProducto() == id) {
                    listaProductos.remove(i);
                    eliminado = true;
                    break;
                }
            }
            if (!eliminado) {
                throw new DataNotFoundException("No se encontró el producto con ID" + id);
            }
            guardarXML(listaProductos);
        } catch (DataNotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new DataWriteException("Error al eliminar el producto con ID" + id,e);
        }
    }

    @Override
    public void actualizarProducto(Producto p) throws DataAccessException {
        try {
            ArrayList<Producto> listaProductos = listar();
            boolean actualizado = false;
            for (int i = 0; i < listaProductos.size(); i++) {
                if (listaProductos.get(i).getIdProducto() == p.getIdProducto()) {
                    listaProductos.set(i, p);
                    actualizado = true;
                    break;
                }
            }
            if (!actualizado) {
                throw new DataNotFoundException("No se encontro el producto con ID" + p.getIdProducto());
            }
            guardarXML(listaProductos);
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DataWriteException("Error al actualizar el producto con ID " + p.getIdProducto(), e);
        }
    }

    @Override
    public ArrayList<Producto> listar() throws DataAccessException {
        ArrayList<Producto> listaProductos = new ArrayList<>();
        if (!archivoXML.exists()||archivoXML.length() == 0) {
            return listaProductos;
        }
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            //creamos una instancia del handler creado
            ProductoHandler handler = new ProductoHandler();
            //el parser procese el archivo con el handler
            parser.parse(archivoXML, handler);
            listaProductos = handler.getProductos();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DataReadException("Error al leer el XML de productos",e);
        }
        return listaProductos;
    }

    @Override
    public Producto buscarPorId(int id) throws DataAccessException {
        ArrayList<Producto> listaProductos = listar();
        for( Producto producto : listaProductos ) {
            if (producto.getIdProducto() == id) {
                return producto;
            }
        }
        throw new DataNotFoundException("No se encontro el producto con ID" + id);
    }

    @Override
    public double calcularValorInventario() throws DataAccessException {
        ArrayList<Producto> listaProductos = listar();
        double ValorInventario = 0;
        for( Producto producto : listaProductos ) {
            ValorInventario += producto.getPrecio() * producto.getCantidad();
        }
        return ValorInventario;
    }

    @Override
    public ArrayList<Producto> listarPorCategoria(String categoria) throws DataAccessException {
        ArrayList<Producto> listaProductos = listar();
        ArrayList<Producto> productosPorCategoria = new ArrayList<>();
        for( Producto producto : listaProductos ) {
            if (producto.getCategoria().equals(Producto.categorias.valueOf(categoria))) {
                productosPorCategoria.add(producto);
            }
        }
        return productosPorCategoria;
    }

    public void guardarXML(ArrayList<Producto> ListaProductos) throws DataAccessException {
        try {
            // creamos el builder que nos permiterá crear documentos, etc.
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //obtenemos una implementación del DOM
            DOMImplementation implementation = builder.getDOMImplementation();
            //creamos el documento en memoria
            // Creamos el documento
            //la siguiente linea crea el elemento raiz --> <productos>...van los atributos de diferentes productos...</productos>
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
        } catch (ParserConfigurationException e) {
            throw new DataWriteException("Error al configurar el parser de XML", e);
        } catch (TransformerException e) {
            throw new DataWriteException("Error al transformar o guardar el XML", e);
        } catch (Exception e){
            throw new DataAccessException("Error inesperado:", e);
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
