package store.cookshoong.www.cookshoongfrontend.store.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.store.adapter.StoreMenuAdapter;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateMenuRequestDto;

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
     * API 서버에 메뉴 그룹 신규등록 요청을 보냄.
     *
     * @param storeId                   매장 기본키
     * @param createMenuGroupRequestDto 매장 메뉴 그룹 신규등록 요청 정보
     */
    public void createMenuGroup(Long storeId, CreateMenuGroupRequestDto createMenuGroupRequestDto) {
        storeMenuAdapter.createMenuGroup(storeId, createMenuGroupRequestDto);
    }

    /**
     * API 서버에 메뉴 신규등록 요청을 보냄.
     *
     * @param storeId              매장 기본키
     * @param createMenuRequestDto 매장 메뉴 신규등록 요청 정보
     */
    public void createMenu(Long storeId, CreateMenuRequestDto createMenuRequestDto) {
        storeMenuAdapter.createMenu(storeId, createMenuRequestDto);
    }
}
