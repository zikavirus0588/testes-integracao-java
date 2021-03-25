package br.com.alura.leilao.leiloes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CadastroLeilaoPage {

    private final WebDriver browser;
    private final static String CURRENT_URL = "http://localhost:8080/leiloes/new";
    private final static String MIN_CHAR = "minimo 3 caracteres";
    private final static String NOT_BLANK = "não deve estar em branco";
    private final static String MIN_VALUE = "deve ser um valor maior de 0.1";
    private final static String VALID_DATE = "deve ser uma data no formato dd/MM/yyyy";


    public CadastroLeilaoPage(WebDriver browser) {
        this.browser = browser;
    }

    public void fechar() {
        this.browser.quit();
    }

    public void preencherFormulario(String nome, String valorInicial, String dataAbertura) {
        browser.findElement(By.id("nome")).sendKeys(nome);
        browser.findElement(By.id("valorInicial")).sendKeys(valorInicial);
        browser.findElement(By.id("dataAbertura")).sendKeys(dataAbertura);
    }

    public LeiloesPage submeterFormulario() {
        browser.findElement(By.id("button-submit")).submit();
        return new LeiloesPage(browser);
    }

    public boolean isPaginaAtual() {
        return browser.getCurrentUrl().equals(CURRENT_URL);
    }

    public boolean containsMensagens() {
        String pageSource =  browser.getPageSource();
        return pageSource.contains(MIN_CHAR)
                && pageSource.contains(NOT_BLANK)
                && pageSource.contains(MIN_VALUE)
                && pageSource.contains(VALID_DATE);
    }
}
