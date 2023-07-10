package store.cookshoong.www.cookshoongfrontend.store.model;

import java.time.LocalTime;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 영업시간 등록 Dto.
 *
 * @author papel
 * @since 2023.07.10
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBusinessHourRequestDto {

    @NotBlank
    private String dayCodeName;

    @NotBlank
    private String openHour;

    @NotBlank
    private String closeHour;
}
