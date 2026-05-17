package ruslan.simakov.naval.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "naval")
public record NavalProperties(
    String url,
    String username,
    String password) {
}
