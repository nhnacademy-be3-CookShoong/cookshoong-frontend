package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreMenuAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;

/**
 * 매장 메뉴 관리 서비스.
 *
 * @author papel (윤동현)
 * @since 2023.07.13
 */
@Service
@RequiredArgsConstructor
public class StoreMenuManagerService {
    private final StoreMenuAdapter storeMenuAdapter;

    /**
     * 메뉴 그룹 등록 요청 서비스.
     *
     * @param storeId                   매장 아이디
     * @param createMenuGroupRequestDto 메뉴 그룹 등록 Dto
     */
    public void createMenuGroup(Long storeId,
                                CreateMenuGroupRequestDto createMenuGroupRequestDto) {
        storeMenuAdapter.executeCreateMenuGroup(storeId, createMenuGroupRequestDto);
    }

    /**
     * 메뉴 등록 요청 서비스.
     *
     * @param storeId              매장 기본키
     * @param createMenuRequestDto 메뉴 등록 Dto
     */
    public void createMenu(Long storeId, CreateMenuRequestDto createMenuRequestDto, MultipartFile menuImage) {
        if (Objects.nonNull(createMenuRequestDto.getTargetMenuId())) {
            storeMenuAdapter.executeUpdateMenu(storeId, createMenuRequestDto, menuImage);
        } else {
            storeMenuAdapter.executeCreateMenu(storeId, createMenuRequestDto, menuImage);
        }
    }

    /**
     * 메뉴 그룹 리스트 조회 서비스.
     *
     * @param storeId 매장 아이디
     * @return 매장의 메뉴 그룹 리스트
     */
    public List<SelectMenuGroupResponseDto> selectMenuGroups(Long storeId) {
        return storeMenuAdapter.fetchMenuGroups(storeId);
    }

    /**
     * 메뉴 그룹 조회 서비스.
     *
     * @param storeId     매장 아이디
     * @param menuGroupId 메뉴 그룹 아이디
     * @return 매장의 메뉴 그룹
     */
    public SelectMenuGroupResponseDto selectMenuGroup(Long storeId, Long menuGroupId) {
        return storeMenuAdapter.fetchMenuGroup(storeId, menuGroupId);
    }

    /**
     * 메뉴 리스트 조회 서비스.
     *
     * @param storeId 매장 아이디
     * @return 매장의 메뉴 리스트
     */
    public List<SelectMenuResponseDto> selectMenus(Long storeId) {
        return storeMenuAdapter.fetchMenus(storeId);
    }

    /**
     * 메뉴 조회 서비스.
     *
     * @param storeId 매장 아이디
     * @param menuId  메뉴 아이디
     * @return 매장의 메뉴
     */
    public SelectMenuResponseDto selectMenu(Long storeId, Long menuId) {
        return storeMenuAdapter.fetchMenu(storeId, menuId);
    }

    /**
     * 메뉴 그룹 삭제 서비스.
     *
     * @param storeId     매장 아이디
     * @param menuGroupId 메뉴 그룹 아이디
     */
    public void deleteMenuGroup(Long storeId, Long menuGroupId) {
        storeMenuAdapter.executeDeleteMenuGroup(storeId, menuGroupId);
    }

    /**
     * 메뉴 삭제 서비스.
     *
     * @param storeId 매장 아이디
     * @param menuId  메뉴 아이디
     */
    public void deleteMenu(Long storeId, Long menuId) {
        storeMenuAdapter.executeDeleteMenu(storeId, menuId);
    }
}
