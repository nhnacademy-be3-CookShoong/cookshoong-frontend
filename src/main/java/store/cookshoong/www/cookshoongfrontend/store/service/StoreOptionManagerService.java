package store.cookshoong.www.cookshoongfrontend.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.store.adapter.StoreOptionAdapter;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.model.request.CreateOptionRequestDto;

/**
 * 매장의 옵션 관리를 위한 서비스.
 *
 * @author papel
 * @since 2023.07.13
 */
@Service
@RequiredArgsConstructor
public class StoreOptionManagerService {
    private final StoreOptionAdapter storeOptionAdapter;

    /**
     * 옵션 그룹 신규등록 요청을 보냄.
     *
     * @param storeId                     매장 기본키
     * @param createOptionGroupRequestDto 매장 옵션 그룹 신규등록 요청 정보
     */
    public void createOptionGroup(Long storeId, CreateOptionGroupRequestDto createOptionGroupRequestDto) {
        storeOptionAdapter.executeCreateOptionGroup(storeId, createOptionGroupRequestDto);
    }

    /**
     * 옵션 신규등록 요청을 보냄.
     *
     * @param storeId                매장 기본키
     * @param createOptionRequestDto 매장 메뉴 신규등록 요청 정보
     */
    public void createOption(Long optionGroupId, Long storeId, CreateOptionRequestDto createOptionRequestDto) {
        storeOptionAdapter.executeCreateOption(optionGroupId, storeId, createOptionRequestDto);
    }
}
