package ruslan.simakov.naval.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ruslan.simakov.naval.service.BookingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NavalScheduler {

    private final BookingService bookingService;

    @Scheduled(cron = "0 0 7 * * *")
    public void runMorningBooking() {
        bookingService.bookActivities();
    }

}
