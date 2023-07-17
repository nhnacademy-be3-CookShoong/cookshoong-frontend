package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreMenuAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;

/**
 * 매장의 메뉴 관리를 위한 서비스.
 *
 * @author papel
 * @since 2023.07.13
 */
@Service
@RequiredArgsConstructor
public class StoreMenuManagerService {
    private final StoreMenuAdapter storeMenuAdapter;

    /**
     * 메뉴 그룹 신규등록 요청을 보냄.
     *
     * @param storeId                   매장 기본키
     * @param createMenuGroupRequestDto 매장 메뉴 그룹 신규등록 요청 정보
     */
    public void createMenuGroup(Long storeId, CreateMenuGroupRequestDto createMenuGroupRequestDto) {
        storeMenuAdapter.executeCreateMenuGroup(storeId, createMenuGroupRequestDto);
    }

    /**
     * 메뉴 신규등록 요청을 보냄.
     *
     * @param storeId              매장 기본키
     * @param createMenuRequestDto 매장 메뉴 신규등록 요청 정보
     */
    public void createMenu(Long storeId, CreateMenuRequestDto createMenuRequestDto) {
        storeMenuAdapter.executeCreateMenu(storeId, createMenuRequestDto);
    }

    public List<SelectMenuResponseDto> selectMenus(Long storeId) {
        return storeMenuAdapter.fetchMenus(storeId);
    }
}