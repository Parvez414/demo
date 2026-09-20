package com.mycompany.tests.pages;

import com.automation.driver.DriverManager;
import com.automation.pages.BasePage;
import org.openqa.selenium.By;

public class ConsumerDashboardPage extends BasePage {

    public ConsumerDashboardPage() {
        super("ConsumerDashboardPage");
    }

    @Override
    protected void initElements() {
        register("dashboardHeader", "Dashboard main title header", By.xpath("//h6[contains(normalize-space(),'Dashboard')] | //span[contains(@class,'oxd-topbar-header-breadcrumb')] | //header"));
    }

    public boolean isDashboardLoaded() {
        try {
            waitForVisibility(getElement("dashboardHeader"), 15);
            return true;
        } catch (Exception e) {
            return isDisplayed(getElement("dashboardHeader")) ||
                   (DriverManager.getDriver() != null && DriverManager.getDriver().getCurrentUrl().contains("/dashboard"));
        }
    }
}
