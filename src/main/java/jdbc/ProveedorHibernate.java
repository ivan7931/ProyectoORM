package jdbc;

import Clases.Producto;
import Clases.Proveedor;
import Excepciones.DataAccessException;
import Excepciones.DataNotFoundException;
import Interfaces.ProveedorDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class ProveedorHibernate implements ProveedorDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionTienda");

    @Override
    public void agregarProveedor(Proveedor p) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DataAccessException("Error al agregar el proveedor", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarProveedor(int id) throws DataAccessException {
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
        } catch (Exception e) {
            throw new DataAccessException("Error al eliminar el proveedor", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarProveedor(Proveedor p) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DataAccessException("Error al actualizar el proveedor", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Proveedor> listarProveedores() throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("FROM Proveedor",Proveedor.class).getResultList();
        } catch (Exception e){
            throw new DataAccessException("Error al listar proveedores", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Proveedor buscarPorId(int id) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int contarProveedores() throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            Long total = em.createQuery("SELECT COUNT(p) FROM Proveedor p",Long.class).getSingleResult();
            return total.intValue();
        } catch (Exception e){
            throw new DataAccessException("Error al contar proveedores", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> productosSuministrados(int idProveedor) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("FROM Producto p WHERE p.proveedor.idProveedor = :id", Producto.class).setParameter("id", idProveedor).getResultList();
        } finally {
            em.close();
        }
    }
}
