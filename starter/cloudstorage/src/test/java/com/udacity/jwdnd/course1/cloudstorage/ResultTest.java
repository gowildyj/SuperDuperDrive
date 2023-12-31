package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultTest {

    private final JavascriptExecutor js;

    public ResultTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "aResultSuccess")
    private WebElement aResultSuccess;

    public void clickOk() {
        js.executeScript("arguments[0].click();", aResultSuccess);
    }
}
