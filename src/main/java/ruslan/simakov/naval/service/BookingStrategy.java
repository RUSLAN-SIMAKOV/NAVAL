package ruslan.simakov.naval.service;

import ruslan.simakov.naval.config.ActivityConfig;
import ruslan.simakov.naval.model.BookingResult;

public interface BookingStrategy {

    BookingResult execute(BrowserSession session, String username, String password, ActivityConfig config);
}