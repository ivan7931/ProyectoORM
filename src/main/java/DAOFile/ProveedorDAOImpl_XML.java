package DAOFile;

import Clases.Cliente;
import Clases.Producto;
import Clases.Proveedor;
import Interfaces.ProductoDAO;
import Interfaces.ProveedorDAO;
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

public class ProveedorDAOImpl_XML implements ProveedorDAO {
    private final File archivoXML = new File("Proveedores.xml");

    public ProveedorDAOImpl_XML() throws IOException {

    }

    @Override
    public void agregarProveedor(Proveedor p) throws IOException {
        ArrayList<Proveedor> listaProveedores = listarProveedores();
        int nuevoID;
        if (!listaProveedores.isEmpty()) {
            int maxID = 0;
            for (Proveedor proveedor : listaProveedores) {
                if (proveedor.getIdProveedor() > maxID) {
                    maxID = proveedor.getIdProveedor();
                }
            }
            nuevoID = maxID + 1;
            p.setIdProveedor(nuevoID);
        }
        listaProveedores.add(p);
        guardarXML(listaProveedores);
    }

    @Override
    public void eliminarProveedor(int id) throws IOException {
        ArrayList<Proveedor> listaProveedores = listarProveedores();
        for (Proveedor proveedor : listaProveedores) {
            if (proveedor.getIdProveedor() == id) {
                listaProveedores.remove(proveedor);
                break;
            }
        }
        guardarXML(listaProveedores);
    }

    @Override
    public void actualizarProveedor(Proveedor p) throws IOException {
        ArrayList<Proveedor> listaProveedores = listarProveedores();
        for (int i = 0; i < listaProveedores.size(); i++) {
            if (listaProveedores.get(i).getIdProveedor() == p.getIdProveedor()) {
                listaProveedores.set(i, p);
                break;
            }
        }
        guardarXML(listaProveedores);
    }

    @Override
    public ArrayList<Proveedor> listarProveedores() throws IOException {
        ArrayList<Proveedor> listaProveedores = new ArrayList<>();
        if (!archivoXML.exists() || archivoXML.length() == 0) {
            return listaProveedores;
        }

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            ProveedorHandler handler = new ProveedorHandler();

            parser.parse(archivoXML,handler);
            listaProveedores = handler.getListaProveedores();
        } catch (Exception e) {
            throw new IOException("Error al leer el XML", e);
        }
        return listaProveedores;
    }

    @Override
    public Proveedor buscarPorId(int id) throws IOException {
        ArrayList<Proveedor> listaProveedores = new ArrayList<>();
        listaProveedores = listarProveedores();
        for (Proveedor proveedor : listaProveedores) {
            if (proveedor.getIdProveedor() == id) {
                return proveedor;
            }
        }
        return null;
    }

    @Override
    public int contarProveedores() throws IOException {
        return listarProveedores().size();
    }

    @Override
    public ArrayList<Producto> productosSuministrados(int idProveedor) {
        return null;
    }

    public void guardarXML(ArrayList<Proveedor> ListaProveedores){
        /*try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoXML))) {
            bw.write("<proveedores>\n");
            for (Proveedor p : ListaProveedores) {
                bw.write("  <proveedor>\n");
                bw.write("      <nombre>" + p.getNombre() + "</nombre>\n");
                bw.write("      <empresa>" + p.getEmpresa() + "</empresa>\n");
                bw.write("      <id>" + p.getIdProveedor() + "</id>\n");
                bw.write("  </proveedor>\n");
            }
            bw.write("</proveedores>\n");
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
            Document documento = implementation.createDocument(null, "proveedores", null);
            // establecemos la version de XML
            documento.setXmlVersion("1.0");
            // Obtenemos el elemento raíz
            Element raiz = documento.getDocumentElement();
            //creamos nodos de productos y los añadimos al elemento raiz
            for(Proveedor p : ListaProveedores) {
                Element proveedorXML = crearProducto(p, documento);
                raiz.appendChild(proveedorXML);
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
            StreamResult file = new StreamResult(archivoXML);
            transf.transform(source, file);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //metodo auxiliar que convierte el objeto a XML
    public static Element crearProducto(Proveedor p, Document documento) {
        Element proveed = documento.createElement("proveedor");
        proveed.appendChild(crearElemento("nombre",p.getNombre(),documento));
        proveed.appendChild(crearElemento("empresa",""+p.getEmpresa(),documento));
        proveed.appendChild(crearElemento("id_proveedor",""+p.getIdProveedor(),documento));
        return proveed;
    }
    public static Element crearElemento(String nombre, String valor, Document documento) {
        Element elemento = documento.createElement(nombre);
        Text texto = documento.createTextNode(valor);
        elemento.appendChild(texto);
        return elemento;


    }
}
