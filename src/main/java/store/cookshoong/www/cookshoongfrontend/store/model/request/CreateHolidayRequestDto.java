package store.cookshoong.www.cookshoongfrontend.store.model.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 사업자 : 휴업일 등록 Dto.
 *
 * @author papel
 * @since 2023.07.07
 */
@Getter
@AllArgsConstructor
public class CreateHolidayRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate holidayStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate holidayEndDate;
}
