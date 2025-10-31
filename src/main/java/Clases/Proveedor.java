package Clases;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Proveedor implements Externalizable {

    private String nombre;
    private String empresa;
    private static int generadorID=0;
    private int idProveedor;

    public Proveedor(String nombre, String empresa) {
        setNombre(nombre);
        setEmpresa(empresa);
        generadorID++;
        setIdProveedor(generadorID);
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(nombre);
        out.writeObject(empresa);
        out.writeInt(idProveedor);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nombre = (String) in.readObject();
        empresa = (String) in.readObject();
        idProveedor = in.readInt();
    }

    public Proveedor() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        if(empresa.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.empresa = empresa;
    }
    public static void setGeneradorID(int valor) {
        generadorID = valor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }
    public  void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    @Override
    public String toString() {
        return "Proveedor{" +
                "nombre='" + nombre + '\'' +
                ", empresa='" + empresa + '\'' +
                ", idProveedor=" + idProveedor +
                '}';
    }
}
