package dev.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class ChromeTest {

  static ChromeDriver driver;

  @BeforeAll
  public static void start() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-maximized");
    driver = new ChromeDriver(options);
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");
  }

  @Test
  public void CPF1() throws InterruptedException {

    String texto = "Test", msgSend = "Received!";
    // Recuperamos el elemento "Text input"
    WebElement textInput = driver.findElement(By.id("my-text-id"));

    // Escribimos el texto
    textInput.sendKeys(texto);

    // Recuperamos el botón de enviar
    WebElement submitButton = driver.findElement(By.className("btn"));
    // Hacemos click
    submitButton.click();

    // Recuperamos el mensaje
    WebElement message = driver.findElement(By.id("message"));
    // System.out.println(message.getText());
    assertEquals(msgSend, message.getText());

  }

  @Test
  public void inputCheckForm() throws InterruptedException {

    // Comprobar si el input check está marcado
    WebElement checkInput = driver.findElement(By.id(("my-check-1")));
    // Hacer click en el elemento -> Deselecciona
    checkInput.click();
    assertEquals(false, checkInput.isSelected());
  }

  @Test
  public void elementDisabledForms() throws Exception {

    /** INPUT DISABLED */
    WebElement checkInpuDisabled = driver.findElement(By.name(("my-disabled")));
    String textTest = "Test";
    Exception thrown = Assertions.assertThrows(Exception.class, () -> {
      checkInpuDisabled.sendKeys(textTest);
    }, "element not interactable");

    Assertions.assertEquals("element not interactable", thrown.getMessage());
    Thread.sleep(2000);

  }

  @Test
  public void uploadFileForm() throws Exception {
    String nameImage = "logo.png";
    driver.get("https://the-internet.herokuapp.com/upload");
    // Creamos una imagen
    File uploadFile = new File("src/test/files/" + nameImage);

    // Recuperamos el input para subir ficheros
    WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
    // Envíamos la ruta absoluta
    fileInput.sendKeys(uploadFile.getAbsolutePath());

    driver.findElement(By.id("file-submit")).click();
    WebElement fileName = driver.findElement(By.id("uploaded-files"));
    // Comprobamos que se subió correcamente
    assertEquals(nameImage, fileName.getText()); // logo.png

  }

  @Test
  public void selectForms() throws Exception {

    WebElement select = driver.findElement(By.name("my-select"));
    Select comboSelect = new Select(select);
    // Seleccionamos una opción
    comboSelect.selectByIndex(2);

  }

  @Test
  public void elementDataForms() throws Exception {

    WebElement calendar = driver.findElement(By.name("my-date"));
    // Enviams una fecha
    calendar.sendKeys("01/30/2024");
    // Tabulamos para eliminar el pop up.
    calendar.sendKeys(Keys.TAB);
  }

  @AfterAll
  public static void end() {
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    driver.quit();
  }
}