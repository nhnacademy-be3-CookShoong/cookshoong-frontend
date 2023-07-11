package store.cookshoong.www.cookshoongfrontend.store.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 휴업일 등록 Dto.
 *
 * @author papel
 * @since 2023.07.07
 */
@Getter
@AllArgsConstructor
public class CreateHolidayRequestDto {

    @NotBlank
    private String holidayStartDate;

    @NotBlank
    private String holidayEndDate;
}
