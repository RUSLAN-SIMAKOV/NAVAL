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
            
            page.click("button[type='submit']:not([name='provider']), .account_button");

            page.waitForURL("**/Portal/**");
            page.waitForLoadState();
        }
    }

    public void openActivities(Page page) {
        page.click("text=Activities");
    }

    public void selectActivity(Page page, String name) {
        page.click("text=" + name);
    }

    public void selectDate(Page page, String date) {
        String dataDay = date.replace("-", "");
        page.click("div[data-day='" + dataDay + "']");
        page.waitForLoadState();
        page.waitForTimeout(2000);
    }

    public boolean tryBookActivity(Page page, String activityName) {

        // Use a more robust wait that specifically targets visible activity blocks
        Locator visibleBlocks = page.locator(".chekin-aula:visible")
                .filter(new Locator.FilterOptions().setHasText(activityName));

        visibleBlocks.first().waitFor();

        Locator block = visibleBlocks.first();

        if (block.count() > 0) {
            Locator button = block.locator("a.btn-user-checkin-cancheckin:has-text('Marcar')");

            if (button.count() > 0) {
                button.click();

                // Wait for any potential confirmation dialog
                page.waitForTimeout(2000);

                Locator confirm = page.locator("text=Confirmar, text=Confirm, text=Sim, text=Yes, .btn-primary:has-text('Marcar')");
                if (confirm.first().isVisible()) {
                    confirm.first().click();
                }

                return true;
            }
        }

        return false;
    }

    public boolean isTimerPresent(Page page, String activityName) {
        Locator block = page.locator(".chekin-aula:visible")
                .filter(new Locator.FilterOptions().setHasText(activityName))
                .first();

        if (block.count() > 0) {
            String text = block.locator(".checkin-buttonsarea").innerText();
            // Timer pattern like 00:00:00 or 00:00. Exclude "Marcar" which has "Lot: X/Y"
            return text.matches("(?s).*\\d{1,2}:\\d{2}(:\\d{2})?.*") && !text.contains("Marcar");
        }
        return false;
    }
}
