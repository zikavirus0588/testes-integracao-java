package br.com.alura.leilao.login;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPage {

    private final WebDriver browser;
    private static final String URL_LOGIN = "http://localhost:8080/login";
    private static final String URL_LOGIN_ERROR = "http://localhost:8080/login?error";
    private static final String URL_LEILOES = "http://localhost:8080/leiloes";

    public LoginPage() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        this.browser = new ChromeDriver();
        browser.navigate().to(URL_LOGIN);
    }

    public void fechar() {
        this.browser.quit();
    }

    public void preencheFormularioDeLogin(String username, String password) {
        browser.findElement(By.id("username")).sendKeys(username);
        browser.findElement(By.id("password")).sendKeys(password);
    }
    public void efetuaLogin() {
        browser.findElement(By.id("login-form")).submit();
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
