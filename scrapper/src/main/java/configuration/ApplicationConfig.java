package configuration;



import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import configuration.access.AccessType;
import service.schedule.Schedule;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull Schedule scheduler,
                                @NotNull AccessType databaseAccessType,
                                @NotNull String exchangeName,
                                @NotNull String queryName,
                                @NotNull boolean useQueue) {
}