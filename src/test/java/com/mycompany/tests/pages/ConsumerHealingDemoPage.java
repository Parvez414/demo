package com.mycompany.tests.pages;

import com.automation.components.ButtonComponent;
import com.automation.pages.BasePage;
import org.openqa.selenium.By;

/**
 * Page Object with deliberately broken locators to demonstrate autonomous AI Self-Healing.
 */
public class ConsumerHealingDemoPage extends BasePage {

    public ButtonComponent submitBtn;

    public ConsumerHealingDemoPage() {
        super("ConsumerHealingDemoPage");
    }

    @Override
    protected void initElements() {
        // Intentionally broken locator that the AI Healer recovers
        register("brokenUsername", "Username text input field", By.id("invalid_broken_consumer_user_input_99999"));
        register("password", "Password text input field", By.name("password"));
        register("submitBtn", "Login submit button", By.cssSelector("button[type='submit']"));

        submitBtn = initComponent(ButtonComponent.class, getElement("submitBtn"));
    }

    public void loginWithHealedElement(String user, String pass) {
        sendKeys(getElement("brokenUsername"), user);
        sendKeys(getElement("password"), pass);
        click(getElement("submitBtn"));
    }
}
