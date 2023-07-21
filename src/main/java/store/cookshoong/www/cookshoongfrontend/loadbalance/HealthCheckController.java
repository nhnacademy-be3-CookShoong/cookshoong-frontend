package store.cookshoong.www.cookshoongfrontend.loadbalance;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import store.cookshoong.www.cookshoongfrontend.loadbalance.actuator.MyHealthIndicator;

/**
 * L4 200 인지 체크하는 RestController.
 *
 * @author jeongjewan
 * @since 2023.07.10
 */
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final MyHealthIndicator myHealthIndicator;

    /**
     * health check 용.
     *
     * @return health 반환
     */
    @GetMapping("/health-check")
    public ResponseEntity<Health> getHealthCheck() {
        Health health = myHealthIndicator.health();

        if (health.getStatus().equals(Health.down().build().getStatus())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(health);
        }

        return ResponseEntity.status(HttpStatus.OK).body(health);
    }

    /**
     * health check Recover.
     *
     * @return 헬스체크 Ok 반환
     */
    @PostMapping("/health-check/recover")
    public ResponseEntity<String> getHealthCheckRecover() {
        myHealthIndicator.recoverHealth();

        return ResponseEntity.ok("Health Check OK");
    }

    /**
     * health check fail.
     *
     * @return 헬스체크 Bad Request 반환
     */
    @PostMapping("/health-check/fail")
    public ResponseEntity<String> getHealthCheckFail() {
        myHealthIndicator.downHealth();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Health Check Bad Request");
    }

}
