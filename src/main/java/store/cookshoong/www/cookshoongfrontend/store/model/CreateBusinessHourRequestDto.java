package store.cookshoong.www.cookshoongfrontend.store.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 영업시간 등록 Dto.
 *
 * @author papel
 * @since 2023.07.10
 */
@Getter
@AllArgsConstructor
public class CreateBusinessHourRequestDto {

    @NotBlank
    private String dayCodeName;

    @NotBlank
    private String openHour;

    @NotBlank
    private String closeHour;
}
