package org.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainStepDefinition {

    @Managed
    WebDriver driver;

    @When("I run mock step in {int} seconds")
    public void runMockStep(int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(driver -> {
                        driver.get("https://www.google.com");
                        return false;
                    });
        } catch (TimeoutException ignore) {
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
