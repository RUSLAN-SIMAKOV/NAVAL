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

        navalPage.openActivities(page);

        navalPage.selectActivity(page, "Tennis");

        navalPage.selectDate(page, config.preferredDate());

        boolean success = navalPage.tryBookFirstAvailableSlot(page);

        return success
            ? BookingResult.successful()
            : BookingResult.failed("No slots available");
    }
}
