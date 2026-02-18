package Clases;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table (name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_proveedor", nullable = false, unique = true)
    private int idProveedor;

    @Column (name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column (name = "empresa", nullable = false, length = 50)
    private String empresa;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Producto> productos;

    public Proveedor(String nombre, String empresa) {
        setNombre(nombre);
        setEmpresa(empresa);
    }

    public Proveedor(int idProveedor,  String nombre, String empresa) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.empresa = empresa;
    }

    public Proveedor() {}


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
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
    public int getIdProveedor() {
        return idProveedor;
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
