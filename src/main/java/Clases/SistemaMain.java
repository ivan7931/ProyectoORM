package Clases;

import Excepciones.*;

import java.io.*;

public class SistemaMain {
    private static File archivo = new File(".\\Clientes.json");
    private static File archivo2 = new File(".\\Productos.json");
    private static File archivo3 = new File(".\\Proveedores.json");
    private static File archivo4 = new File(".\\Clientes.xml");
    private static File archivo5 = new File(".\\Productos.xml");
    private static File archivo6 = new File(".\\Proveedores.xml");
    private static File directorio = new File(".\\archivos_XML_JSON");

    public static File getArchivo2() {
        return archivo2;
    }

    public static File getArchivo() {
        return archivo;
    }

    public static File getArchivo3() {
        return archivo3;
    }

    public static File getDirectorio() {
        return directorio;
    }

    public static File getArchivo4() {
        return archivo4;
    }

    public static File getArchivo5() {
        return archivo5;
    }

    public static File getArchivo6() {
        return archivo6;
    }

    public static void copiaSeguridad(File archivo, File directorio) throws DataAccessException, IOException {
        if(!directorio.exists()) {
            directorio.mkdir();
        }
        try(BufferedReader br = new BufferedReader(new FileReader(archivo));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(directorio,archivo.getName()+"_BACKUP")))){
            String linea;
            while((linea = br.readLine()) != null){
                bw.write(linea);
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new DataReadException("No se encontró el archivo: " + archivo.getName(), e);
        } catch (IOException e) {
            throw new DataWriteException("Error al escribir la copia de seguridad", e);
        } catch (Exception e) {
            throw new DataAccessException("Error inesperado durante la copia de seguridad", e);
        }
    }
    public static void main(String[] args) throws DataAccessException, IOException {
            copiaSeguridad(archivo,directorio);
            copiaSeguridad(archivo2,directorio);
            copiaSeguridad(archivo3,directorio);
        copiaSeguridad(archivo4,directorio);
        copiaSeguridad(archivo5,directorio);
        copiaSeguridad(archivo6,directorio);
    }
}
