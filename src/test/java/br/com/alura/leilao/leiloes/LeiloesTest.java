package br.com.alura.leilao.leiloes;

import br.com.alura.leilao.login.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeiloesTest {

    private LeiloesPage paginaDeLeiloes;
    private LoginPage paginaDeLogin;
    private CadastroLeilaoPage paginaDeCadastroLeilao;

    @AfterEach
    public void tearDown() {
        paginaDeLeiloes.fechar();
    }

    @Test
    public void deveriaCadastrarLeilao() {
        seRequerLogin("fulano","pass");
        quandoEfetuaLogin();
        seRequerCadastroDeLeilao("mochila", "150.00","25/03/2021");
        quandoSubmeterFormulario();
        entaoNovoLeilaoFoiCadastrado("mochila", "150.00","25/03/2021");

    }

    private void seRequerLogin(String user, String password) {
        this.paginaDeLogin = new LoginPage();
        paginaDeLogin.preencheFormularioDeLogin(user,password);
    }

    private void quandoEfetuaLogin() {
        this.paginaDeLeiloes = paginaDeLogin.efetuaLogin();
    }

    private void seRequerCadastroDeLeilao(String nome, String valor, String data) {
        this.paginaDeCadastroLeilao = paginaDeLeiloes.carregarFormulario();
        paginaDeCadastroLeilao.preencherFormulario(nome,valor,data);
    }

    private void quandoSubmeterFormulario() {
        this.paginaDeLeiloes = paginaDeCadastroLeilao.submeterFormulario();
    }

    private void entaoNovoLeilaoFoiCadastrado(String nome, String valorInicial, String dataAbertura) {
        assertTrue(paginaDeLeiloes.isLeilaoCadastrado(nome,valorInicial,dataAbertura));
    }

}
