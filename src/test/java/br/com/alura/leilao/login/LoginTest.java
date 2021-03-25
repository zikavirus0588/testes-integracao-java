package br.com.alura.leilao.login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private LoginPage paginaDeLogin;

    @BeforeEach
    public void setUp() {
        this.paginaDeLogin = new LoginPage();
    }

    @AfterEach
    public void tearDown() {
        paginaDeLogin.fechar();
    }

    @Test
    public void deveriaEfetuarLoginComDadosValidos() {

        seRequerLogin("fulano", "pass");
        quandoEfetuarLogin();
        entaoLoginEfetuadoComSucesso("fulano");

    }

    @Test
    public void naoDeveriaEfetuarLoginComDadosInvalidos() {

        seRequerLogin("zika", "123321");
        quandoEfetuarLogin();
        entaoLoginInvalidoContemMensagem("Usuário e senha inválidos.");

    }

    @Test
    public void naoDeveriaAcessarPaginaRestritaSemEfetuarLogin() {

        quandoNavegarParaPaginaDeLeilaoUnico("2");
        entaoPaginaAtualNaoContemMensagem("Dados do Leilão");

    }

    private void seRequerLogin(String user, String password) {
        paginaDeLogin.preencheFormularioDeLogin(user,password);
    }

    private void quandoEfetuarLogin() {
        paginaDeLogin.efetuaLogin();
    }

    private void quandoNavegarParaPaginaDeLeilaoUnico(String id) {
        paginaDeLogin.navegaParaLeiloes(id);
    }

    private void entaoLoginEfetuadoComSucesso(String username) {
        assertFalse(paginaDeLogin.isPaginaDeLogin());
        assertEquals(username, paginaDeLogin.getUsuarioLogado());
    }

    private void entaoLoginInvalidoContemMensagem(String mensagem) {
        assertTrue(paginaDeLogin.isPaginaDeLoginError());
        assertTrue(paginaDeLogin.containsMensagem(mensagem));
        assertNull(paginaDeLogin.getUsuarioLogado());
    }

    private void entaoPaginaAtualNaoContemMensagem(String mensagem) {
        assertTrue(paginaDeLogin.isPaginaDeLogin());
        assertFalse(paginaDeLogin.containsMensagem(mensagem));
    }



}
