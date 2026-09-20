package com.mycompany.tests.pages;

import com.automation.components.ButtonComponent;
import com.automation.pages.BasePage;
import org.openqa.selenium.By;

/**
 * Consumer Project Page Object extending BasePage from the Core SDK.
 */
public class ConsumerLoginPage extends BasePage {

    public ButtonComponent loginBtn;

    public ConsumerLoginPage() {
        super("ConsumerLoginPage");
    }

    @Override
    protected void initElements() {
        register("username", "Username text input field", By.name("username"));
        register("password", "Password text input field", By.name("password"));
        register("loginBtn", "Login submit button", By.cssSelector("button[type='submit']"));
        register("forgotPasswordLink", "Forgot your password link", By.xpath("//p[contains(@class,'orangehrm-login-forgot-header')] | //p[contains(.,'Forgot your password?')]"));

        loginBtn = initComponent(ButtonComponent.class, getElement("loginBtn"));
    }

    public void login(String username, String password) {
        sendKeys(getElement("username"), username);
        sendKeys(getElement("password"), password);
        click(getElement("loginBtn"));
    }
}
