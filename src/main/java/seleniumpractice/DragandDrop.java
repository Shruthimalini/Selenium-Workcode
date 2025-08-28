package seleniumpractice;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DragandDrop {
	public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/test/drag_drop.html");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Actions actions = new Actions(driver);

        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("bank")));

        
        WebElement bank = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'BANK')]")));
        WebElement sales = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'SALES')]")));
        WebElement debitAmount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(text(),'5000')])[2]")));
        WebElement creditAmount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(text(),'5000')])[2]")));

        WebElement debitAccount = driver.findElement(By.id("bank"));
        WebElement debitAmt = driver.findElement(By.id("amt7"));
        WebElement creditAccount = driver.findElement(By.id("loan"));
        WebElement creditAmt = driver.findElement(By.id("amt8"));

        actions.dragAndDrop(bank, debitAccount).perform();
        actions.dragAndDrop(debitAmount, debitAmt).perform();
        actions.dragAndDrop(sales, creditAccount).perform();
        actions.dragAndDrop(creditAmount, creditAmt).perform();
    }
}
