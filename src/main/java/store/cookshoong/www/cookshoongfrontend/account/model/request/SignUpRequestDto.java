package store.cookshoong.www.cookshoongfrontend.account.model.request;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import store.cookshoong.www.cookshoongfrontend.common.util.RegularExpressions;
import store.cookshoong.www.cookshoongfrontend.common.util.ValidationFailureMessages;

/**
 * 회원가입 폼에서 회원가입 요청을 위해 보내는 Dto.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    @Length(min = 1, max = 30)
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    @Length(min = 2, max = 30)
    @Pattern(regexp = RegularExpressions.LETTER_ONLY, message = ValidationFailureMessages.LETTER_ONLY)
    private String name;
    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = RegularExpressions.LETTER_WITH_NUMBER, message = ValidationFailureMessages.LETTER_WITH_NUMBER)
    private String nickname;
    @NotBlank
    @Email
    private String email;
    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotBlank
    @Pattern(regexp = RegularExpressions.NUMBER_ONLY, message = ValidationFailureMessages.NUMBER_ONLY)
    private String phoneNumber;
}
