package store.cookshoong.www.cookshoongfrontend.auth.model.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 요청 정보를 담은 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
@Getter
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
