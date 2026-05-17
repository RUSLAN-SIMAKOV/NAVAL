package ruslan.simakov.naval.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ruslan.simakov.naval.config.ActivityConfig;
import ruslan.simakov.naval.model.BookingResult;

@Service
@RequiredArgsConstructor
public class BookingEngine {

    private final BrowserFactory browserFactory;
    private final Map<String, BookingStrategy> strategies;

    public BookingResult book(ActivityConfig config) {

        BookingStrategy strategy = strategies.get(config.type());

        if (strategy == null) {
            throw new IllegalArgumentException("No strategy for: " + config.type());
        }

        try (BrowserSession session = browserFactory.create()) {
            return strategy.execute(session, config);
        }
    }
}
