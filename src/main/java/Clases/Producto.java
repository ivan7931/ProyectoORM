package Clases;

import java.io.*;

public class Producto implements Externalizable {

    public enum categorias{CATEGORIA1, CATEGORIA2, CATEGORIA3,CATEGORIA4,CATEGORIA5}
    private String nombre;
    private double precio;
    private categorias categoria;
    private int cantidad;
    private static int generadorID=0;
    private int idProducto;
    private int idProveedor;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(nombre);
        out.writeDouble(precio);
        out.writeObject(categoria.name());
        out.writeInt(cantidad);
        out.writeInt(idProducto);
        out.writeInt(idProveedor);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nombre = (String)in.readObject();
        precio = in.readDouble();
        categoria = categorias.valueOf((String) in.readObject());
        cantidad = in.readInt();
        idProducto = in.readInt();
        idProveedor = in.readInt();
    }
    public Producto() {

    }
    public Producto(String nombre, double precio, categorias categoria,int cantidad, int idProveedor) {
        setNombre(nombre);
        setPrecio(precio);
        this.categoria = categoria;
        this.cantidad=cantidad;
        generadorID++;
        idProducto = generadorID;
        this.idProveedor = idProveedor;
    }
    public Producto(int idProducto,String nombre, double precio, categorias categoria,int cantidad, int idProveedor) {
        this.idProducto = idProducto;
        setNombre(nombre);
        setPrecio(precio);
        this.categoria = categoria;
        this.cantidad=cantidad;
        generadorID++;
        idProducto = generadorID;
        this.idProveedor = idProveedor;
    }
    public Producto(String nombre, double precio, categorias categoria, int cantidad) {
        this(nombre,precio,categoria,cantidad,-1);
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
        if(cantidad<0){
            throw new IllegalArgumentException();
        }
        this.cantidad = cantidad;
    }

    public void setCategoria(categorias categoria) {
        if(categoria==null){
            throw new IllegalArgumentException();
        }
        this.categoria = categoria;
    }
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        if(idProducto<=0){
            throw new IllegalArgumentException();
        }
        this.idProducto = idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        if(idProveedor<=0){
            throw new IllegalArgumentException();
        }
        this.idProveedor = idProveedor;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre +
                ", precio=" + precio +
                ", categoria=" + categoria +
                ",cantidad=" + cantidad +
                ",idProducto=" + idProducto +
                ", idProveedor=" + idProveedor +
                '}';
    }
}