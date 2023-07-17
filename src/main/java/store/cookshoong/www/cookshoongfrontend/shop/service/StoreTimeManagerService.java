package store.cookshoong.www.cookshoongfrontend.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreTimeAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBusinessHourRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateHolidayRequestDto;

/**
 * 매장의 영업시간 관리를 위한 서비스.
 *
 * @author papel
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
     * @param storeId                      매장 기본키
     * @param createHolidayRequestDto      휴업일 신규등록 요청 정보
     */
    public void createHoliday(Long storeId, CreateHolidayRequestDto createHolidayRequestDto) {
        storeTimeAdapter.executeCreatHoliday(storeId, createHolidayRequestDto);
    }
}


//수정 예정
