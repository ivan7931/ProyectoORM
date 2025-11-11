package Clases;

import Excepciones.*;

import java.io.*;

public class SistemaMain {
    public static void copiaSeguridad(File archivo, File directorio) throws DataAccessException {
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
    public static void main(String[] args) throws DataAccessException {
        File archivo = new File(".\\Clientes.json");
        File archivo2 = new File(".\\Productos.json");
        File archivo3 = new File(".\\Proveedores.json");
        File directorio = new File(".\\archivos_XML_JSON");
        directorio.mkdir();
        if(directorio.exists()){
            copiaSeguridad(archivo,directorio);
            copiaSeguridad(archivo2,directorio);
            copiaSeguridad(archivo3,directorio);
        }
    }
}
