package ruslan.simakov.naval.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

@Service
public class BrowserFactory {

    public BrowserSession create() {

        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(List.of("--start-maximized"))
        );

        BrowserContext context = browser.newContext(
            new Browser.NewContextOptions().setViewportSize(null)
        );

        Page page = context.newPage();
        page.setDefaultTimeout(30000);

        return new BrowserSession(playwright, browser, context, page);
    }
}
