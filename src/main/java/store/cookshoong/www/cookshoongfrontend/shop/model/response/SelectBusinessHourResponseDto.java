package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메뉴 그룹 조회 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectBusinessHourResponseDto {
    private Long id;
    private String dayCodeName;
    private LocalTime openHour;
    private LocalTime closeHour;
}
