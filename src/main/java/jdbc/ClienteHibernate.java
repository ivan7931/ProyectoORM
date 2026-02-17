package jdbc;

import Clases.Cliente;
import Excepciones.*;
import Interfaces.ClienteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class ClienteHibernate implements ClienteDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionTienda");

    @Override
    public void agregarCliente(Cliente c) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception e){
            throw new DataAccessException("Error al agregar el cliente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarCliente(int id) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Cliente c = em.find(Cliente.class, id);
            if(c == null){
                throw new DataNotFoundException("No existe el cliente con id: " + id);
            }
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e){
            throw new DataAccessException("Error al eliminar el cliente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarCliente(Cliente c) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
        } catch (Exception e){
            throw new DataAccessException("Error al actualizar el cliente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> listarClientes() throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("FROM Cliente", Cliente.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscarPorId(int id) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            Cliente c = em.find(Cliente.class, id);
            if(c == null){
                throw new DataNotFoundException("No existe el cliente con id: " + id);
            }
            return c;
        } finally {
            em.close();
        }
    }

    @Override
    public int contarClientes() throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            Long total = em.createQuery("SELECT COUNT(*) FROM Cliente", Long.class).getSingleResult();
            return total.intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> buscarPorNombre(String nombre) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        try{
            List<Cliente> lista = em.createQuery("FROM Cliente c Where c.nombre = :nombre", Cliente.class).setParameter("nombre", nombre).getResultList();
            if (lista.isEmpty()) {
                throw new DataNotFoundException("No existen clientes con ese nombre");
            } else {
                return lista;
            }
        } finally {
            em.close();
        }
    }

}
