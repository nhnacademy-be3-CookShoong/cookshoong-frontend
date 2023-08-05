package store.cookshoong.www.cookshoongfrontend.account.model.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 상태 변경시 응답 객체.
 *
 * @author koesnam (추만석)
 * @since 2023.07.29
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAccountStatusResponseDto {
    private String status;
    private LocalDateTime updatedAt;
}
