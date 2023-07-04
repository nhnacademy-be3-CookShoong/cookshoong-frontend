package store.cookshoong.www.cookshoongfrontend.auth.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotBlank
    private String phoneNumber;
}
