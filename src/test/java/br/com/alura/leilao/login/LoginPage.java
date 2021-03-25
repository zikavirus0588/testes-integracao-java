package br.com.alura.leilao.login;

import br.com.alura.leilao.PageObject;
import br.com.alura.leilao.leiloes.LeiloesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class LoginPage extends PageObject {

    private static final String URL_LOGIN = "http://localhost:8080/login";
    private static final String URL_LOGIN_ERROR = "http://localhost:8080/login?error";
    private static final String URL_LEILOES = "http://localhost:8080/leiloes";

    public LoginPage() {
        super(null);
        this.browser.navigate().to(URL_LOGIN);
    }

    public void preencheFormularioDeLogin(String username, String password) {
        browser.findElement(By.id("username")).sendKeys(username);
        browser.findElement(By.id("password")).sendKeys(password);
    }
    public LeiloesPage efetuaLogin() {
        browser.findElement(By.id("login-form")).submit();
        return new LeiloesPage(browser);
    }
    public boolean isPaginaDeLogin() {
        return browser.getCurrentUrl().equals(URL_LOGIN);
    }
    public String getUsuarioLogado() {
        try {
            return browser.findElement(By.id("usuario-logado")).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    public boolean isPaginaDeLoginError() {
        return browser.getCurrentUrl().equals(URL_LOGIN_ERROR);
    }
    public boolean containsMensagem(String mensagem) {
        return browser.getPageSource().contains(mensagem);
    }
    public void navegaParaLeiloes(String id) {
        browser.navigate().to(URL_LEILOES + "/" + id);
    }
}
