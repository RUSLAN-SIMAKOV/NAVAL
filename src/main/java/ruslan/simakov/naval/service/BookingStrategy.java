package ruslan.simakov.naval.service;

import ruslan.simakov.naval.config.ActivityConfig;
import ruslan.simakov.naval.model.BookingResult;

public interface BookingStrategy {

    BookingResult execute(BrowserSession session, ActivityConfig config);
}