package store.cookshoong.www.cookshoongfrontend.loadbalance.actuator;

import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Health check Indicator.
 *
 * @author jeongjewan
 * @since 2023.07.11
 */
@Component
public class MyHealthIndicator implements HealthIndicator {
    private final AtomicBoolean health = new AtomicBoolean(true);

    @Override
    public Health health() {
        if (!health.get()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

    public void downHealth() {
        health.set(false);
    }

    public void recoverHealth() {
        health.set(true);
    }
}
