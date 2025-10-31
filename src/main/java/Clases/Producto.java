package Clases;

import java.io.*;

public class Producto implements Externalizable {

    public enum categorias{CATEGORIA1, CATEGORIA2, CATEGORIA3,CATEGORIA4,CATEGORIA5};
    private String nombre;
    private double precio;
    private categorias categoria;
    private int cantidad;
    private static int generadorID=0;
    private int idProducto;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(nombre);
        out.writeDouble(precio);
        out.writeObject(categoria.name());
        out.writeInt(cantidad);
        out.writeInt(idProducto);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nombre = (String)in.readObject();
        precio = in.readDouble();
        categoria = categorias.valueOf((String) in.readObject());
        cantidad = in.readInt();
        idProducto = in.readInt();
    }
    public Producto() {

    }
    public Producto(String nombre, double precio, categorias categoria,int cantidad) {
        setNombre(nombre);
        setPrecio(precio);
        this.categoria = categoria;
        this.cantidad=cantidad;
        generadorID++;
        idProducto = generadorID;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if(precio<0){
            throw new IllegalArgumentException();
        }
        this.precio = precio;
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

    public categorias getCategoria() {
        return categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCategoria(categorias categoria) {
        this.categoria = categoria;
    }

    public static void setGeneradorID(int valor) {
        generadorID = valor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre +
                ", precio=" + precio +
                ", categoria=" + categoria +
                ",cantidad=" + cantidad +
                ",idProducto=" + idProducto +
                '}';
    }
}