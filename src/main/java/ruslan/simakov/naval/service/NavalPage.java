package ruslan.simakov.naval.service;

import org.springframework.stereotype.Component;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

@Component
public class NavalPage {

    public void loginIfNeeded(Page page, String user, String pass) {

        if (page.locator(".activity_catalog_buy").isVisible()) {
            page.click(".activity_catalog_buy");
        }

        if (page.url().contains("Login")) {

            page.fill("#Email", user);
            page.click("text=Prosseguir");

            page.fill("#Password", pass);
            
            if (page.locator("button[type='submit']:not([name='provider'])").isVisible()) {
                page.click("button[type='submit']:not([name='provider'])");
            } else {
                page.click(".account_button");
            }

            page.waitForURL("**/Portal/**");
        }
    }

    public void openActivities(Page page) {
        page.click("text=Activities");
    }

    public void selectActivity(Page page, String name) {
        page.click("text=" + name);
    }

    public void selectDate(Page page, String date) {
        page.click("[data-date='" + date + "']");
    }

    public boolean tryBookFirstAvailableSlot(Page page) {

        Locator slots = page.locator(".slot.available");

        if (slots.count() == 0) return false;

        slots.first().click();
        page.click("text=Confirm");

        page.waitForTimeout(5000);

        return page.locator("text=Success").count() > 0;
    }
}
