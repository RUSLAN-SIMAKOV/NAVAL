package ruslan.simakov.naval.service;

import org.springframework.stereotype.Component;

import com.microsoft.playwright.Page;

import lombok.RequiredArgsConstructor;
import ruslan.simakov.naval.config.ActivityConfig;
import ruslan.simakov.naval.config.NavalProperties;
import ruslan.simakov.naval.model.BookingResult;

@Component("TENNIS")
@RequiredArgsConstructor
public class TennisBookingStrategy implements BookingStrategy {

    private final NavalPage navalPage;
    private final NavalProperties navalProperties;

    @Override
    public BookingResult execute(BrowserSession session, String username, String password, ActivityConfig config) {

        Page page = session.page();
        page.navigate(navalProperties.url());

        navalPage.loginIfNeeded(page, username, password);

        for (int i = 0; i < 1000; i++) {
            navalPage.selectDate(page, config.preferredDate());

            if (navalPage.tryBookActivity(page, config.name())) {
                return BookingResult.successful();
            }

            if (navalPage.isTimerPresent(page, config.name())) {
                page.waitForTimeout(1000);
                page.reload();
                navalPage.loginIfNeeded(page, username, password);
                continue;
            }

            break;
        }

        return BookingResult.failed("No slots available");
    }
}
