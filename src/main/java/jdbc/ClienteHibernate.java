package jdbc;

import Clases.Cliente;
import Excepciones.*;
import Interfaces.ClienteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class ClienteHibernate implements ClienteDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionTienda");

    @Override
    public void agregarCliente(Cliente c) throws DataWriteException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            throw new DataWriteException("Error al agregar el cliente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarCliente(int id) throws DataWriteException, DataNotFoundException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Cliente c = em.find(Cliente.class, id);
            if(c == null){
                throw new DataNotFoundException("No existe el cliente con id: " + id);
            }
            em.remove(c);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            throw new DataWriteException("Error al eliminar el cliente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarCliente(Cliente c) throws DataWriteException, DataNotFoundException {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Cliente existente = em.find(Cliente.class, c.getIdCliente());
            if (existente == null) {
                throw new DataNotFoundException("Cliente no encontrado para actualizar: " + c.getIdCliente());
            }
            em.merge(c);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            throw new DataWriteException("Error al actualizar el cliente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> listarClientes() throws DataReadException, QueryException {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("FROM Cliente", Cliente.class).getResultList();
        } catch (PersistenceException e){
            throw new DataReadException("Error al listar clientes", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para listar clientes", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscarPorId(int id) throws DataNotFoundException, QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            Cliente c = em.find(Cliente.class, id);
            if(c == null){
                throw new DataNotFoundException("No existe el cliente con id: " + id);
            }
            return c;
        } catch (PersistenceException e){
            throw new DataReadException("Error al buscar clientes con id: " + id, e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para buscar clientes con id" + id, e);
        } finally {
            em.close();
        }
    }

    @Override
    public int contarClientes() throws QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            Long total = em.createQuery("SELECT COUNT(*) FROM Cliente", Long.class).getSingleResult();
            return total.intValue();
        } catch (PersistenceException e){
            throw new DataReadException("Error al contar los clientes", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para contar los clientes", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> buscarPorNombre(String nombre) throws DataNotFoundException, QueryException, DataReadException {
        EntityManager em = emf.createEntityManager();
        try{
            List<Cliente> lista = em.createQuery("FROM Cliente c Where c.nombre = :nombre", Cliente.class).setParameter("nombre", nombre).getResultList();
            if (lista.isEmpty()) {
                throw new DataNotFoundException("No existen clientes con ese nombre");
            } else {
                return lista;
            }
        } catch (PersistenceException e){
            throw new DataReadException("Error al buscar clientes por nombre", e);
        } catch (IllegalArgumentException e){
            throw new QueryException("Error en la query para buscar clientes por nombre", e);
        } finally {
            em.close();
        }
    }

}
