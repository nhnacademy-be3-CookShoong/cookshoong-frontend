package store.cookshoong.www.cookshoongfrontend.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
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

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate holidayStartDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate holidayEndDate;
}
