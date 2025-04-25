package Tests;

import Uso_Comun.DAOs.DAO_Usuario;
import Uso_Comun.Modelos.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class Test_Usuario {

    private static EntityManagerFactory emf;
    private DAO_Usuario dao;

    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("ETradeTech_PU");
    }

    @AfterAll
    public static void tearDownClass() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @BeforeEach
    public void setUp() {
        dao = new DAO_Usuario(); // Usa el constructor sin parámetros
        limpiarBaseDeDatos(); // Limpiamos antes de cada test
    }

    private void limpiarBaseDeDatos() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM Usuario").executeUpdate(); // Solo necesitamos limpiar Usuario
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }
    }

    // ------------------- Test CREATE -------------------
    @Test
    public void testCreateUsuario() throws Exception {
        // Crear usuario
        Usuario usuario = new Usuario(100,"Ana López","ana@test.com","DJFLIAENFJHFLOALDFGLDFOGLSJFGLJSDFHB");

        // Guardar en BD
        dao.create(usuario);

        // Verificar que existe
        Usuario encontrado = dao.findModel_Usuario(100);
        assertNotNull(encontrado, "El usuario debería existir después de crearlo");
        assertEquals("Ana López", encontrado.getNombre(), "El nombre debe coincidir");
    }

    // ------------------- Test DESTROY -------------------
    @Test
    public void testDestroyUsuario() throws Exception {
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setUsuarioid(2);
        usuario.setNombre("Pedro Gómez");
        dao.create(usuario);

        // Eliminar
        dao.destroy(2);

        // Verificar que ya no existe
        Usuario encontrado = dao.findModel_Usuario(2);
        assertNull(encontrado, "El usuario debería ser eliminado");
    }

    // ------------------- Test FIND -------------------
    @Test
    public void testFindUsuarioExistente() throws Exception {
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setUsuarioid(3);
        usuario.setNombre("María Ruiz");
        dao.create(usuario);

        // Buscar
        Usuario encontrado = dao.findModel_Usuario(3);
        assertNotNull(encontrado, "Debería encontrar el usuario creado");
        assertEquals(3, encontrado.getUsuarioid(), "El ID debe ser 3");
    }

    @Test
    public void testFindUsuarioNoExistente() {
        // Buscar un ID que no existe
        Usuario encontrado = dao.findModel_Usuario(999);
        assertNull(encontrado, "No debería encontrar un usuario inexistente");
    }
}