package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreTimeAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBusinessHourRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateHolidayRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectBusinessHourResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectHolidayResponseDto;

/**
 * 매장의 영업시간 관리를 위한 서비스.
 *
 * @author papel (윤동현)
 * @since 2023.07.09
 */
@Service
@RequiredArgsConstructor
public class StoreTimeManagerService {
    private final StoreTimeAdapter storeTimeAdapter;

    /**
     * 영업시간 신규등록 요청을 보냄.
     *
     * @param storeId                      매장 기본키
     * @param createBusinessHourRequestDto 영업시간 신규등록 요청 정보
     */
    public void createBusinessHour(Long storeId, CreateBusinessHourRequestDto createBusinessHourRequestDto) {
        storeTimeAdapter.executeCreateBusinessHour(storeId, createBusinessHourRequestDto);
    }


    /**
     * 휴업일 신규등록 요청을 보냄.
     *
     * @param storeId                 매장 기본키
     * @param createHolidayRequestDto 휴업일 신규등록 요청 정보
     */
    public void createHoliday(Long storeId, CreateHolidayRequestDto createHolidayRequestDto) {
        storeTimeAdapter.executeCreatHoliday(storeId, createHolidayRequestDto);
    }

    /**
     * 영업시간 리스트 조회 서비스.
     *
     * @param storeId 매장 아이디
     * @return 매장의 메뉴 그룹 리스트
     */
    public List<SelectBusinessHourResponseDto> selectBusinessHours(Long storeId) {
        return storeTimeAdapter.fetchBusinessHours(storeId);
    }

    /**
     * 휴업일 리스트 조회 서비스.
     *
     * @param storeId 매장 아이디
     * @return 매장의 메뉴 리스트
     */
    public List<SelectHolidayResponseDto> selectHolidays(Long storeId) {
        return storeTimeAdapter.fetchHolidays(storeId);
    }

    /**
     * 영업시간 삭제 서비스.
     *
     * @param storeId        매장 아이디
     * @param businessHourId 영업시간 아이디
     */
    public void deleteBusinessHour(Long storeId, Long businessHourId) {
        storeTimeAdapter.executeDeleteBusinessHour(storeId, businessHourId);
    }

    /**
     * 휴업일 삭제 서비스.
     *
     * @param storeId   매장 아이디
     * @param holidayId 휴업일 아이디
     */
    public void deleteHoliday(Long storeId, Long holidayId) {
        storeTimeAdapter.executeDeleteHoliday(storeId, holidayId);
    }
}
