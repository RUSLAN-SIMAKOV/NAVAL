package ruslan.simakov.naval.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BrowserSession implements AutoCloseable {

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;

    public BrowserSession(Playwright playwright,
        Browser browser,
        BrowserContext context,
        Page page) {
        this.playwright = playwright;
        this.browser = browser;
        this.context = context;
        this.page = page;
    }

    public Page page() {
        return page;
    }

    @Override
    public void close() {
        context.close();
        browser.close();
        playwright.close();
    }
}
