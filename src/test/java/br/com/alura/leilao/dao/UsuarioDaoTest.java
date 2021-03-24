package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;
    private Usuario user;

    @BeforeEach
    void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaEncontrarUsuarioCadastradoPeloNome() {
        seRequerUsuario();
        Usuario userEncontrado = quandoBuscarUsuario(user.getNome());
        entaoDeveriaEncontrarUsuarioNoBanco(userEncontrado);
    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastradoCadastradoPeloNome() {
        seRequerUsuario();
        entaoDeveriaLancarException(new NoResultException(),"ciclano");
    }

    private void seRequerUsuario() {
        this.user = new Usuario("fulano","fulano@email.com","123456");
        em.persist(user);
    }

    private Usuario quandoBuscarUsuario(String nome) {
        return dao.buscarPorUsername(nome);
    }

    private void entaoDeveriaEncontrarUsuarioNoBanco(Usuario usuario) {
        assertNotNull(usuario);
    }

    private void entaoDeveriaLancarException(Exception e, String nome) {
        assertThrows(e.getClass(), () -> dao.buscarPorUsername(nome));
    }
}