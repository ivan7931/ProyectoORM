package jdbc;

import Clases.Producto;
import Excepciones.*;
import Interfaces.ProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

import java.time.LocalDate;
import java.util.List;

public class ProductoHibernate implements ProductoDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionTienda");

    @Override
    public void agregarProducto(Producto p) throws DataWriteException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            throw new DataWriteException("Error al insertar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarProducto(int id) throws DataWriteException, DataNotFoundException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Producto p = em.find(Producto.class, id);
            if(p == null){
                throw new DataNotFoundException("No se encontro el producto con id: " + id);
            }
            em.remove(p);
            em.getTransaction().commit();
        }catch (PersistenceException e){
            throw new DataWriteException("Error al eliminar producto", e);
        } finally {
            em.close();
        }

    }

    @Override
    public void actualizarProducto(Producto p) throws DataWriteException, DataNotFoundException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Producto existente = em.find(Producto.class, p.getIdProducto());
            if (existente == null) {
                throw new DataNotFoundException("Producto no encontrado para actualizar: " + p.getIdProducto());
            }
            em.merge(p);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            throw new DataWriteException("Error al actualizar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> listar() throws DataReadException, QueryException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("FROM Producto", Producto.class).getResultList();
        } catch (PersistenceException e){
            throw new DataReadException("Error al listar productos", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para listar productos", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Producto buscarPorId(int id) throws DataNotFoundException, QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            Producto p = em.find(Producto.class, id);
            if(p == null){
                throw new DataNotFoundException("No se encontro el producto con id: " + id);
            }
            return p;
        } catch (PersistenceException e){
            throw new DataReadException("Error al buscar productos con id: " + id, e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para buscar productos con id" + id, e);
        } finally {
            em.close();
        }
    }

    @Override
    public double calcularValorInventario() throws QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            Double total = em.createQuery("SELECT SUM(precio * cantidad) FROM Producto", Double.class).getSingleResult();
            return total != null ? total : 0.0;
        } catch (PersistenceException e){
            throw new DataReadException("Error al calcular el valor del inventario", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para calcular el valor de inventario", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> listarPorCategoria(String categoria) throws DataNotFoundException, QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            Producto.categorias cat = Producto.categorias.valueOf(categoria);
            List<Producto> lista = em.createQuery("FROM Producto WHERE categoria = :cat", Producto.class).setParameter("cat", cat).getResultList();
            if(lista.isEmpty()){
                throw new DataNotFoundException("No se encontro el producto de esa categoria");
            }
            return lista;
        } catch (PersistenceException e){
            throw new DataReadException("Error al listar productos por categoria", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para listar productos por categoria", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> productosUltimosDias(int dias) throws DataNotFoundException, DataReadException, QueryException {
        EntityManager em = emf.createEntityManager();
        try {
            LocalDate fechaLimite = LocalDate.now().minusDays(dias);

            List<Producto> lista = em.createQuery(
                            "SELECT p FROM Producto p WHERE p.fechaAlta >= :fecha",
                            Producto.class)
                    .setParameter("fecha", fechaLimite)
                    .getResultList();
            if(lista.isEmpty()){
                throw new DataNotFoundException("No se encontraron productos de los ultimos: "+ dias +" dias");
            }
            return lista;
        }catch (PersistenceException e){
            throw new DataReadException("Error al listar los productos de los ultimos dias",e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para mostrar los productos de los ultimos dias",e);
        }
        finally {
            em.close();
        }
    }
}
