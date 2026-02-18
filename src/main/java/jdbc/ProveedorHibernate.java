package jdbc;

import Clases.Producto;
import Clases.Proveedor;
import Excepciones.*;
import Interfaces.ProveedorDAO;
import jakarta.persistence.*;

import java.util.List;

public class ProveedorHibernate implements ProveedorDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionTienda");

    @Override
    public void agregarProveedor(Proveedor p) throws DataWriteException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DataWriteException("Error al agregar el proveedor", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarProveedor(int id) throws DataWriteException, DataNotFoundException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Proveedor p = em.find(Proveedor.class, id);
            if(p != null){
                em.remove(p);
            } else{
                throw new DataNotFoundException("No existe el proveedor");
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DataWriteException("Error al eliminar el proveedor", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarProveedor(Proveedor p) throws DataWriteException, DataNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Proveedor existente = em.find(Proveedor.class, p.getIdProveedor());
            if (existente == null) {
                throw new DataNotFoundException("Proveedor no encontrado para actualizar: " + p.getIdProveedor());
            }
            em.merge(p);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DataWriteException("Error al actualizar el proveedor", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Proveedor> listarProveedores() throws DataReadException, QueryException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("FROM Proveedor",Proveedor.class).getResultList();
        } catch (PersistenceException e){
            throw new DataReadException("Error al listar proveedores", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para listar proveedores", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Proveedor buscarPorId(int id) throws DataNotFoundException, DataReadException, QueryException {
        EntityManager em = emf.createEntityManager();
        try{
            Proveedor p = em.find(Proveedor.class, id);
            if(p == null){
                throw new DataNotFoundException("No se encontro el proveedor con id: " + id);
            }
            return p;
        } catch (PersistenceException e){
            throw new DataReadException("Error al buscar proveedores con id: " + id, e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para buscar proveedores con id" + id, e);
        } finally {
            em.close();
        }
    }

    @Override
    public int contarProveedores() throws QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            Long total = em.createQuery("SELECT COUNT(p) FROM Proveedor p",Long.class).getSingleResult();
            return total.intValue();
        } catch (PersistenceException e){
            throw new DataReadException("Error al contar los proveedores", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para contar los proveedores", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> productosSuministrados(int idProveedor) throws DataNotFoundException, QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            List<Producto> lista = em.createQuery("FROM Producto p WHERE p.proveedor.idProveedor = :id", Producto.class).setParameter("id", idProveedor).getResultList();
            if(lista.isEmpty()){
                throw new DataNotFoundException("No se encontro los productos suministrados por ese proveedor");
            }
            return lista;
        } catch (PersistenceException e){
            throw new DataReadException("Error al listar productos suministrados por el proveedor", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para listar productos suministrados por el proveedor", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Proveedor proveedorMayorFacturacion() throws DataNotFoundException, DataReadException, QueryException{
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT pr FROM Proveedor pr JOIN pr.productos p GROUP BY pr ORDER BY SUM((p.precio * (1 - (p.descuento/100))) * p.cantidad) DESC",
                            Proveedor.class)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new DataNotFoundException("No hay proveedores con productos para calcular facturación", e);
        } catch (IllegalArgumentException e) {
            throw new QueryException("Error en la query para saber el proveedor con mayor facturacion", e);
        } catch (PersistenceException e) {
            throw new DataReadException("Error al acceder a la base de datos para saber el proveedor con mayor facturacion", e);
        } finally {
            em.close();
        }
    }
}
