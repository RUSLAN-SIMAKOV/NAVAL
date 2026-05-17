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
    }

    public boolean tryBookActivity(Page page, String activityName) {

        Locator block = page.locator(".row").filter(new Locator.FilterOptions().setHasText(activityName));
        Locator button = block.locator("a:has-text('Marcar')");

        if (button.isVisible()) {
            button.click();
            
            if (page.locator("text=Confirm").isVisible()) {
                page.click("text=Confirm");
            }

            page.waitForTimeout(5000);

            return true;
        }

        return false;
    }
}
