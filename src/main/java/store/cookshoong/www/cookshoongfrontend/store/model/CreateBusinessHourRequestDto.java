package store.cookshoong.www.cookshoongfrontend.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
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

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalTime openHour;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalTime closeHour;
}
