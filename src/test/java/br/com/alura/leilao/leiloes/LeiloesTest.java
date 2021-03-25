package br.com.alura.leilao.leiloes;

import br.com.alura.leilao.login.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeiloesTest {

    private LeiloesPage paginaDeLeiloes;
    private LoginPage paginaDeLogin;
    private CadastroLeilaoPage paginaDeCadastroLeilao;

    @BeforeEach
    public void setUp() {
        seRequerLogin("fulano", "pass");
        quandoEfetuaLogin();
    }

    @AfterEach
    public void tearDown() {
        paginaDeLeiloes.fechar();
    }

    @Test
    public void deveriaCadastrarLeilao() {

        seRequerCadastroDeLeilao("mochila", "150.00", "25/03/2021");
        quandoSubmeterFormulario();
        entaoNovoLeilaoFoiCadastrado("mochila", "150.00", "25/03/2021");

    }

    @Test
    public void deveriaValidarCamposNoCadastroDeLeilao() {

        seRequerCadastroDeLeilao("", "", "");
        quandoSubmeterFormulario();
        entaoValidacaoDosCamposFoiRealizada();

    }

    private void seRequerLogin(String user, String password) {
        this.paginaDeLogin = new LoginPage();
        paginaDeLogin.preencheFormularioDeLogin(user, password);
    }

    private void quandoEfetuaLogin() {
        this.paginaDeLeiloes = paginaDeLogin.efetuaLogin();
    }

    private void seRequerCadastroDeLeilao(String nome, String valor, String data) {
        this.paginaDeCadastroLeilao = paginaDeLeiloes.carregarFormulario();
        paginaDeCadastroLeilao.preencherFormulario(nome, valor, data);
    }

    private void quandoSubmeterFormulario() {
        this.paginaDeLeiloes = paginaDeCadastroLeilao.submeterFormulario();
    }

    private void entaoNovoLeilaoFoiCadastrado(String nome, String valorInicial, String dataAbertura) {
        assertTrue(paginaDeLeiloes.isLeilaoCadastrado(nome, valorInicial, dataAbertura));
    }

    private void entaoValidacaoDosCamposFoiRealizada() {
        assertFalse(paginaDeCadastroLeilao.isPaginaAtual());
        assertTrue(paginaDeLeiloes.isPaginaAtual());
        assertTrue(paginaDeCadastroLeilao.containsMensagens());
    }


}
