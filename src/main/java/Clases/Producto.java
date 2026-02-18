package Clases;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table (name = "producto")
public class Producto{

    public enum categorias{CATEGORIA1, CATEGORIA2, CATEGORIA3,CATEGORIA4,CATEGORIA5}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_producto", nullable = false, unique = true)
    private int idProducto;

    @Column (name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column (name = "precio", nullable = false)
    private double precio;

    @Enumerated(EnumType.STRING)
    @Column (name = "categoria", nullable = false, length = 20)
    private categorias categoria;

    @Column (name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "descuento")
    private Double descuento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    public Producto() {

    }
    public Producto(String nombre, double precio, categorias categoria,int cantidad, Proveedor proveedor, Double descuento) {
        setNombre(nombre);
        setPrecio(precio);
        this.categoria = categoria;
        this.cantidad=cantidad;
        this.proveedor = proveedor;
        this.fechaAlta = LocalDate.now();
        this.descuento = (descuento != null) ? descuento : 0.0;
    }
    public Producto(String nombre, double precio, categorias categoria,
                    int cantidad, Proveedor proveedor) {
        this(nombre, precio, categoria, cantidad, proveedor, 0.0);
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
        if(nombre == null || nombre.isBlank()){
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


    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Double getDescuento() {
        return descuento;
    }

    public double getPrecioFinal() {
        return precio * (1 - descuento / 100);
    }

    public double getValorInventario() {
        return getPrecioFinal() * cantidad;
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