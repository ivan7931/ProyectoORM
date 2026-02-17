package jdbc;

import Clases.Producto;
import Excepciones.DataAccessException;
import Excepciones.DataNotFoundException;
import Interfaces.ProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class ProductoHibernate implements ProductoDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionTienda");

    @Override
    public void agregarProducto(Producto p) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e){
            throw new DataAccessException("Error al agregar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarProducto(int id) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Producto p = em.find(Producto.class, id);
            if(p == null){
                throw new DataNotFoundException("No se encontro el producto con id: " + id);
            }
            em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e){
            throw new DataAccessException("Error al eliminar producto", e);
        } finally {
            em.close();
        }

    }

    @Override
    public void actualizarProducto(Producto p) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e){
            throw new DataAccessException("Error al actualizar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> listar() throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("FROM Producto", Producto.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Producto buscarPorId(int id) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            Producto p = em.find(Producto.class, id);
            if(p == null){
                throw new DataNotFoundException("No se encontro el producto con id: " + id);
            }
            return p;
        } finally {
            em.close();
        }
    }

    @Override
    public double calcularValorInventario() throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            Double total = em.createQuery("SELECT SUM(precio * cantidad) FROM Producto", Double.class).getSingleResult();
            return total != null ? total : 0.0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> listarPorCategoria(String categoria) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            Producto.categoria cat = Producto.categorias.valueOf(categoria);
            List<Producto> lista = em.createQuery("FROM Producto WHERE categoria = :cat", Producto.class).setParameter("cat", cat).getResultList();
            if(lista.isEmpty()){
                throw new DataNotFoundException("No se encontro el producto de esa categoria");
            }
            return lista;
        } finally {
            em.close();
        }
    }
}
