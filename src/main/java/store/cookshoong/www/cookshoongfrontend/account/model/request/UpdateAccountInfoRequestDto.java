package store.cookshoong.www.cookshoongfrontend.account.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 회원 정보를 수정하는 요청 Dto.
 *
 * @author koesnam (추만석)
 * @since 2023.07.08
 */
@Getter
@AllArgsConstructor
public class UpdateAccountInfoRequestDto {
    @NotBlank
    @Length(min = 1, max = 30)
    private String nickname;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Length(min = 11, max = 11)
    private String phoneNumber;
}
