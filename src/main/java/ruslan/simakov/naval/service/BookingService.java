package ruslan.simakov.naval.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ruslan.simakov.naval.config.ActivityConfig;
import ruslan.simakov.naval.config.NavalProperties;
import ruslan.simakov.naval.model.BookingResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    
    private final BookingEngine bookingEngine;
    private final NavalProperties navalProperties;

    public void bookActivities() {

        List<ActivityConfig> activities = navalProperties.activities();

        for (ActivityConfig activity : activities) {
            try {
                ActivityConfig activityWithDefaults = applyDefaults(activity);
                BookingResult result = bookingEngine.book(activityWithDefaults);
                log.info("Booked: {} -> {}", activity.name(), result.status());

            } catch (Exception e) {
                log.error("Failed: {} -> {}", activity.name(), e.getMessage());
            }
        }
    }

    private ActivityConfig applyDefaults(ActivityConfig activity) {
        String user = activity.username() != null ? activity.username() : navalProperties.username();
        String pass = activity.password() != null ? activity.password() : navalProperties.password();
        return new ActivityConfig(
            activity.type(),
            activity.name(),
            user,
            pass,
            activity.preferredDate()
        );
    }
}
