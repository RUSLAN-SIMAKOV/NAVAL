package ruslan.simakov.naval.service;

import org.springframework.stereotype.Component;

import com.microsoft.playwright.Page;

import lombok.RequiredArgsConstructor;
import ruslan.simakov.naval.config.ActivityConfig;
import ruslan.simakov.naval.model.BookingResult;

@Component("TENNIS")
@RequiredArgsConstructor
public class TennisBookingStrategy implements BookingStrategy {

    private final NavalPage navalPage;

    @Override
    public BookingResult execute(BrowserSession session, ActivityConfig config) {

        Page page = session.page();

        navalPage.loginIfNeeded(page, config.username(), config.password());

        navalPage.openActivities(page);

        navalPage.selectActivity(page, "Tennis");

        navalPage.selectDate(page, config.preferredDate());

        boolean success = navalPage.tryBookFirstAvailableSlot(page);

        return success
            ? BookingResult.successful()
            : BookingResult.failed("No slots available");
    }
}
