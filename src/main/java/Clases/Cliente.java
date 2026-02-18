package Clases;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_cliente", nullable = false,unique = true)
    private int id;

    @Column (name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column (name = "apellido", nullable = false, length = 50)
    private String apellido;

    public Cliente(){

    }

    public Cliente(String nombre, String apellido) {
        setNombre(nombre);
        setApellido(apellido);
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if(apellido.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.apellido = apellido;
    }

    public int getIdCliente() {
        return id;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ",ID= "+getIdCliente()+
                '}';
    }
}
