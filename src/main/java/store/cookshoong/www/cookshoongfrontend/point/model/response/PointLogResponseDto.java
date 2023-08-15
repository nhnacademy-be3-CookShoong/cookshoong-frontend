package store.cookshoong.www.cookshoongfrontend.point.model.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 로그 응답 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.14
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointLogResponseDto {
    private String reason;
    private int pointMovement;
    private LocalDateTime pointAt;
}
