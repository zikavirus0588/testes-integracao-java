package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
    }

    @Test
    void buscaPorNomeDeUsuario() {
        Usuario novoUsuario = new Usuario("fulano","fulano@email.com","123456");
        em.getTransaction().begin();
        em.persist(novoUsuario);
        em.getTransaction().commit();
        Usuario user = dao.buscarPorUsername(novoUsuario.getNome());
        assertNotNull(user);
    }
}