package store.cookshoong.www.cookshoongfrontend.account.model.response;

import lombok.Getter;

/**
 * 회원의 현재 상태조회에 대한 응답 객체.
 *
 * @author koesnam (추만석)
 * @since 2023.07.29
 */
@Getter
public class AccountStatusResponseDto {
    private String status;
}
