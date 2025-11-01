package DAOFile;

import Clases.Cliente;
import Clases.Producto;
import Interfaces.ClienteDAO;
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
import java.io.*;
import java.util.ArrayList;

public class ClienteDAOImpl_XML implements ClienteDAO {
    private final File archivoXML = new File("Clientes.xml");

    public ClienteDAOImpl_XML() {

    }

    @Override
    public void agregarCliente(Cliente c) throws IOException {
        ArrayList<Cliente> ListaClientes = listarClientes();
        int nuevoID = 1;
        if (!ListaClientes.isEmpty()) {
            int maxID = 0;
            for (Cliente cliente : ListaClientes) {
                if (cliente.getId() > maxID) {
                    maxID = cliente.getId();
                }
            }
            nuevoID = maxID + 1;
            c.setId(nuevoID);
        }
        ListaClientes.add(c);
        guardarXML(ListaClientes);
    }

    @Override
    public void eliminarCliente(int id) throws IOException {
        ArrayList<Cliente> ListaClientes = listarClientes();
        for (int i = 0; i < ListaClientes.size(); i++) {
            if (ListaClientes.get(i).getId() == id) {
                ListaClientes.remove(i);
                break;
            }
        }
        guardarXML(ListaClientes);
    }

    @Override
    public void actualizarCliente(Cliente c) throws IOException {
        ArrayList<Cliente> listaClientes = listarClientes();
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getId() == c.getId()) {
                listaClientes.set(i, c);
                break;
            }
        }
        guardarXML(listaClientes);
    }

    @Override
    public ArrayList<Cliente> listarClientes() throws IOException {
        ArrayList<Cliente> ListaClientes = new ArrayList<>();
        if (!archivoXML.exists() || archivoXML.length() == 0) {
            return ListaClientes;
        }

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            ClienteHandler handler = new ClienteHandler();
            saxParser.parse(archivoXML, handler);
            ListaClientes = handler.getClientes();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error al leer el XML", e);
        }

        if (!ListaClientes.isEmpty()) {

        }
        return ListaClientes;
    }

    @Override
    public Cliente buscarPorId(int id) throws IOException {
        for (Cliente cliente : listarClientes()) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public int contarClientes() throws IOException {
        return listarClientes().size();
    }

    @Override
    public ArrayList<Cliente> buscarPorNombre(String nombre) throws IOException {
        ArrayList<Cliente> ListaClientesNombre = new ArrayList<>();
        for (Cliente cliente: listarClientes()){
            if (cliente.getNombre().equals(nombre)){
                ListaClientesNombre.add(cliente);
            }
        }
        return ListaClientesNombre;
    }

    public void guardarXML(ArrayList<Cliente> ListaClientes) throws IOException {
        /*try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoXML))) {
            bw.write("<clientes>\n");
            for (Cliente c : ListaClientes) {
                bw.write("  <cliente>\n");
                bw.write("      <nombre>" + c.getNombre() + "</nombre>\n");
                bw.write("      <apellido>" + c.getApellido() + "</apellido>\n");
                bw.write("      <id>" + c.getId() + "</id>\n");
                bw.write("  </cliente>\n");
            }
            bw.write("</clientes>\n");
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
            Document documento = implementation.createDocument(null, "clientes", null);
            // establecemos la version de XML
            documento.setXmlVersion("1.0");
            // Obtenemos el elemento raíz
            Element raiz = documento.getDocumentElement();
            //creamos nodos de productos y los añadimos al elemento raiz
            for(Cliente c : ListaClientes) {
                Element clienteXML = crearProducto(c, documento);
                raiz.appendChild(clienteXML);
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
    public static Element crearProducto(Cliente c, Document documento) {
        Element client = documento.createElement("cliente");
        client.appendChild(crearElemento("nombre",c.getNombre(),documento));
        client.appendChild(crearElemento("apellido",""+c.getApellido(),documento));
        client.appendChild(crearElemento("id",""+c.getId(),documento));
        return client;
    }
    public static Element crearElemento(String nombre, String valor, Document documento) {
        Element elemento = documento.createElement(nombre);
        Text texto = documento.createTextNode(valor);
        elemento.appendChild(texto);
        return elemento;

    }

}
