package store.cookshoong.www.cookshoongfrontend.account.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountMyPageInfo;

/**
 * 회원에 대한 모든 정보가 담긴 응답 객체.
 *
 * @author koesnam (추만석)
 * @since 2023.08.09
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoResponseDto {
    private Long id;
    private String status;
    private String authority;
    private String rank;
    private String loginId;
    private String name;
    private String nickname;
    private String email;
    private LocalDate birthday;
    private String phoneNumber;
    private LocalDateTime lastLoginAt;
}
