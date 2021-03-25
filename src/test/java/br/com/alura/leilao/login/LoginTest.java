package br.com.alura.leilao.login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private WebDriver browser;
    private static final String URL_LOGIN = "http://localhost:8080/login";
    private static final String URL_LEILOES = "http://localhost:8080/leiloes";

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
    }

    @BeforeEach
    public void setUp() {
        browser = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        browser.quit();
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
        entaoLoginInvalido("Usuário e senha inválidos.");

    }

    @Test
    public void naoDeveriaAcessarPaginaRestritaSemEfetuarLogin() {

        quandoNavegarParaPaginaDeLeilaoUnico("2");
        entaoNaoDeveriaAcessarPaginaRestrita();

    }

    private void seRequerLogin(String user, String password) {
        browser.navigate().to(URL_LOGIN);
        browser.findElement(By.id("username")).sendKeys(user);
        browser.findElement(By.id("password")).sendKeys(password);
    }

    private void quandoEfetuarLogin() {
        browser.findElement(By.id("login-form")).submit();
    }

    private void quandoNavegarParaPaginaDeLeilaoUnico(String id) {
        browser.navigate().to(URL_LEILOES + "/" + id);
    }

    private void entaoLoginEfetuadoComSucesso(String username) {
        assertNotEquals(browser.getCurrentUrl(), URL_LOGIN);
        assertEquals(username, browser.findElement(By.id("usuario-logado")).getText());
    }

    private void entaoLoginInvalido(String mensagem) {
        assertEquals(browser.getCurrentUrl(), URL_LOGIN + "?error");
        assertTrue(browser.getPageSource().contains(mensagem));
        assertThrows(NoSuchElementException.class, () -> browser.findElement(By.id("usuario-logado")).getText());
    }

    private void entaoNaoDeveriaAcessarPaginaRestrita() {
        assertEquals(browser.getCurrentUrl(), URL_LOGIN);
        assertFalse(browser.getPageSource().contains("Dados do Leilão"));

    }

}
