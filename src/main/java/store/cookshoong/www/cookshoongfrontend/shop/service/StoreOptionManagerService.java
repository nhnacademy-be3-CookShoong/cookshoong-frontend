package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreOptionAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;

/**
 * 매장 옵션 관리 서비스.
 *
 * @author papel (윤동현)
 * @since 2023.07.13
 */
@Service
@RequiredArgsConstructor
public class StoreOptionManagerService {
    private final StoreOptionAdapter storeOptionAdapter;

    /**
     * 옵션 그룹 등록 요청 서비스.
     *
     * @param storeId                     매장 기본키
     * @param createOptionGroupRequestDto 옵션 그룹 등록 Dto
     */
    public void createOptionGroup(Long storeId, CreateOptionGroupRequestDto createOptionGroupRequestDto) {
        storeOptionAdapter.executeCreateOptionGroup(storeId, createOptionGroupRequestDto);
    }

    /**
     * 옵션 등록 요청 서비스.
     *
     * @param storeId                매장 기본키
     * @param createOptionRequestDto 옵션 등록 Dto
     */
    public void createOption(Long storeId, CreateOptionRequestDto createOptionRequestDto) {
        storeOptionAdapter.executeCreateOption(storeId, createOptionRequestDto);
    }

    /**
     * 옵션 그룹 리스트 조회 서비스.
     *
     * @param storeId 매장 아이디
     * @return 매장의 옵션 그룹 리스트
     */
    public List<SelectOptionGroupResponseDto> selectOptionGroups(Long storeId) {
        return storeOptionAdapter.fetchOptionGroups(storeId);
    }
    public List<SelectOptionGroupResponseDto> selectOptionGroupsByMenu(Long storeId, Long menuId) {
        return storeOptionAdapter.fetchOptionGroupsByMenu(storeId, menuId);
    }

    /**
     * 옵션 리스트 조회 서비스.
     *
     * @param storeId 매장 아이디
     * @return 매장의 옵션 리스트
     */
    public List<SelectOptionResponseDto> selectOptions(Long storeId) {
        return storeOptionAdapter.fetchOptions(storeId);
    }

    /**
     * 옵션 그룹 삭제 서비스.
     *
     * @param storeId 매장 아이디
     * @param optionGroupId 옵션 그룹 아이디
     */
    public void deleteOptionGroup(Long storeId, Long optionGroupId) {
        storeOptionAdapter.executeDeleteOptionGroup(storeId, optionGroupId);
    }

    /**
     * 옵션 삭제 서비스.
     *
     * @param storeId 매장 아이디
     * @param optionId  옵션 아이디
     */
    public void deleteOption(Long storeId, Long optionId) {
        storeOptionAdapter.executeDeleteOption(storeId, optionId);
    }
}
