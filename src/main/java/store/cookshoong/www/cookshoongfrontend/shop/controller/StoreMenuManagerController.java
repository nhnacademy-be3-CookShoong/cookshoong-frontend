package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 매장 메뉴 관리 페이지 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Controller
@RequiredArgsConstructor
public class StoreMenuManagerController {

    private final StoreService storeService;
    private final StoreMenuManagerService storeMenuManagerService;
    private final StoreOptionManagerService storeOptionManagerService;
    private final AccountIdAware accountIdAware;

    /**
     * 매장 메뉴 관리 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/stores/{storeId}/store-menu-manager")
    public String getSelectStoreMenuManager(
        @ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto,
        @ModelAttribute("createMenuGroupRequestDto") CreateMenuGroupRequestDto createMenuGroupRequestDto,
        @PathVariable("storeId") Long storeId,
        Model model) {

        Long accountId = accountIdAware.getAccountId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);
        String storeName = storeService.selectStoreInfo(accountId, storeId).getStoreName();
        model.addAttribute("storeName", storeName);

        List<SelectMenuResponseDto> menus = storeMenuManagerService.selectMenus(storeId);

        List<SelectMenuGroupResponseDto> menuGroups = storeMenuManagerService.selectMenuGroups(storeId);
        List<SelectOptionGroupResponseDto> optionGroups = storeOptionManagerService.selectOptionGroups(storeId);


        model.addAttribute("storeId", storeId);
        model.addAttribute("menus", menus);
        model.addAttribute("menuGroups", menuGroups);
        model.addAttribute("optionGroups", optionGroups);
        model.addAttribute("createMenuRequestDto", createMenuRequestDto);
        model.addAttribute("createMenuGroupRequestDto", createMenuGroupRequestDto);
        return "store/menu/store-menu-manager";
    }

    /**
     * 매장 메뉴 그룹 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-menu-group-manager")
    public String postCreateMenuGroup(
        @Valid @ModelAttribute("createMenuGroupRequestDto") CreateMenuGroupRequestDto createMenuGroupRequestDto,
        @PathVariable("storeId") Long storeId,
        BindingResult bindingResult) {
        storeMenuManagerService.createMenuGroup(storeId, createMenuGroupRequestDto);
        return "redirect:" + "/stores/" + storeId + "/store-menu-manager";
    }

    /**
     * 매장 메뉴 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-menu-manager")
    public String postCreateMenu(
        @Valid @ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto,
        @PathVariable("storeId") Long storeId,
        BindingResult bindingResult,
        @RequestPart("menuImage") MultipartFile menuImage) {
        storeMenuManagerService.createMenu(storeId, createMenuRequestDto, menuImage);
        return "redirect:" + "/stores/" + storeId + "/store-menu-manager";
    }

    /**
     * 매장 메뉴 그룹 삭제 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.27
     */
    @DeleteMapping("/stores/{storeId}/store-menu-group-manager/{menuGroupId}")
    public String postDeleteMenuGroup(
        @PathVariable("storeId") Long storeId, @PathVariable("menuGroupId") Long menuGroupId) {
        storeMenuManagerService.deleteMenuGroup(storeId, menuGroupId);
        return "redirect:" + "/stores/" + storeId + "/store-menu-manager";
    }

    /**
     * 매장 메뉴 삭제 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.27
     */
    @DeleteMapping("/stores/{storeId}/store-menu-manager/{menuId}")
    public String postDeleteMenu(
        @PathVariable("storeId") Long storeId, @PathVariable("menuId") Long menuId) {
        storeMenuManagerService.deleteMenu(storeId, menuId);
        return "redirect:" + "/stores/" + storeId + "/store-menu-manager";
    }
}
