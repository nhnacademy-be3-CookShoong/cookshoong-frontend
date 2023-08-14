package store.cookshoong.www.cookshoongfrontend.address.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.RegularExpressions;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.ValidationFailureMessages;

/**
 * 회원이 주소를 등록할 때.
 * 회원과 주소관계에서 별칭을 저장하고 주소로 가서 메인, 상세주소, 위도, 경도를 생성
 *
 * @author jeongjewan
 * @since 2023.07.04
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountAddressRequestDto {

    @NotBlank
    @Length(min = 1, max = 10)
    private String alias;

    @NotBlank
    @Length(min = 1, max = 80)
    @Pattern(regexp = RegularExpressions.MAIN_DETAIL_ADDRESS, message = ValidationFailureMessages.MAIN_DETAIL_ADDRESS)
    private String mainPlace;

    @NotBlank
    @Length(max = 80)
    @Pattern(regexp = RegularExpressions.MAIN_DETAIL_ADDRESS, message = ValidationFailureMessages.MAIN_DETAIL_ADDRESS)
    private String detailPlace;

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;
}
