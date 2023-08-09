package store.cookshoong.www.cookshoongfrontend.account.model.vo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import store.cookshoong.www.cookshoongfrontend.account.model.response.AccountInfoResponseDto;

/**
 * MyPage 에서 사용되는 정보를 담은 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.09
 */

@Getter
@AllArgsConstructor
public class AccountMyPageInfo {
    private String rank;
    private String loginId;
    private String name;
    private String nickname;
    private String email;
    private LocalDate birthday;
    private String phoneNumber;

    /**
     * 회원 모든 정보에서 마이페이지에 필요한 정보만을 빼내어 생성한다.
     *
     * @param accountInfoResponseDto the account info response dto
     */
    public AccountMyPageInfo(AccountInfoResponseDto accountInfoResponseDto) {
        this(accountInfoResponseDto.getRank(), accountInfoResponseDto.getLoginId(), accountInfoResponseDto.getName(),
            accountInfoResponseDto.getNickname(), accountInfoResponseDto.getEmail(), accountInfoResponseDto.getBirthday(),
            accountInfoResponseDto.getPhoneNumber());
    }
}
