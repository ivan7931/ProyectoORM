package Clases;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Cliente implements Externalizable{
    private String nombre;
    private String apellido;
    private int id;
    private static int generadorID=0;
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(nombre);
        out.writeObject(apellido);
        out.writeInt(id);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nombre = (String) in.readObject();
        apellido = (String) in.readObject();
        id = in.readInt();
    }
    public Cliente(String nombre, String apellido) {
        setNombre(nombre);
        setApellido(apellido);
        generadorID++;
        setId(generadorID);
    }
    public Cliente(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if(apellido.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id <=0){
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ",ID= "+getId()+
                '}';
    }
}
