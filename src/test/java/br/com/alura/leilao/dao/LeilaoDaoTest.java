package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LeilaoDaoTest {

    private LeilaoDao dao;
    private EntityManager em;
    private Leilao leilao;
    private Usuario user;

    @BeforeEach
    void setUp() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaCadastrarUmLeilao() {
        seRequerUsuario();
        seRequerLeilao("Mochila","200.0",user);
        leilao = quandoSalvarLeilao(leilao);
        Leilao leilaoEncontrado = quandoBuscarLeilao(leilao);
        entaoDeveriaEncontrarLeilaoNoBanco(leilaoEncontrado);
    }

    @Test
    void deveriaAtualizarUmLeilao() {
        seRequerUsuario();
        seRequerLeilao("Mochila","200.0",user);
        leilao = quandoSalvarLeilao(leilao);
        leilao = quandoAtualizarLeilao(leilao,"Celular","2500.00");
        Leilao leilaoEncontrado = quandoBuscarLeilao(leilao);
        entaoDeveriaEncontrarLeilaoAtualizado(leilaoEncontrado, leilao);
    }

    private void seRequerUsuario() {
        this.user = new Usuario("fulano","fulano@email.com","123456");
        em.persist(user);
    }

    private void seRequerLeilao(String nome, String valorInicial, Usuario usuario) {
        this.leilao = new Leilao(nome, new BigDecimal(valorInicial), LocalDate.now(),usuario);
        em.persist(leilao);
    }

    private Leilao quandoBuscarLeilao(Leilao leilao) {
        return dao.buscarPorId(leilao.getId());
    }

    private Leilao quandoSalvarLeilao(Leilao leilao) {
        return dao.salvar(leilao);
    }

    private Leilao quandoAtualizarLeilao(Leilao leilao, String nome, String valor) {
        leilao.setNome(nome);
        leilao.setValorInicial(new BigDecimal(valor));
        return dao.salvar(leilao);
    }

    private void entaoDeveriaEncontrarLeilaoNoBanco(Leilao Leilao) {
        assertNotNull(Leilao);
    }
    private void entaoDeveriaEncontrarLeilaoAtualizado(Leilao Leilao, Leilao expected) {
        assertNotNull(Leilao);
        assertEquals(expected.getNome(), leilao.getNome());
    }

    private void entaoDeveriaLancarException(Exception e, Leilao leilao) {
        assertThrows(e.getClass(), () -> dao.buscarPorId(leilao.getId()));
    }
}