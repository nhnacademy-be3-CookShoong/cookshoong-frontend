package store.cookshoong.www.cookshoongfrontend.store.model;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 휴업일 등록 Dto.
 *
 * @author papel
 * @since 2023.07.07
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateHolidayRequestDto {

    @NotBlank
    private LocalDate holidayStartDate;

    @NotBlank
    private LocalDate holidayEndDate;
}
