package br.com.alura.leilao.leiloes;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LeiloesPage {

    private final WebDriver browser;
    private static final String URL_LEILOES = "http://localhost:8080/leiloes";

    public LeiloesPage(WebDriver browser) {
        this.browser = browser;
    }

    public void fechar() {
        this.browser.quit();
    }

    public CadastroLeilaoPage carregarFormulario() {
        browser.findElement(By.id("novo_leilao_link")).click();
        return new CadastroLeilaoPage(browser);
    }

    public boolean isLeilaoCadastrado(String nome, String valorInicial, String dataAbertura) {
        WebElement ultimoLeilao = browser.findElement(By.cssSelector("#tabela-leiloes tbody tr:last-child"));
        WebElement colunaNome = ultimoLeilao.findElement(By.cssSelector("td:nth-child(1)"));
        WebElement colunaDataAbertura = ultimoLeilao.findElement(By.cssSelector("td:nth-child(2)"));
        WebElement colunaValorInicial = ultimoLeilao.findElement(By.cssSelector("td:nth-child(3)"));
        return colunaNome.getText().equals(nome)
                && colunaDataAbertura.getText().equals(dataAbertura)
                && colunaValorInicial.getText().equals(valorInicial);
    }
}
