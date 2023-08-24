package store.cookshoong.www.cookshoongfrontend.shop.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

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
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 매장 옵션 관리 페이지 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Controller
@RequiredArgsConstructor
public class StoreOptionManagerController {
    private final StoreService storeService;
    private final StoreOptionManagerService storeOptionManagerService;
    private final AccountIdAware accountIdAware;
    private final AccountAddressService accountAddressService;
    private final CartService cartService;

    /**
     * 매장 옵션 관리 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/stores/{storeId}/store-option-manager")
    public String getSelectStoreMenuRegister(
        @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        @PathVariable("storeId") Long storeId,
        Model model) {

        Long accountId = accountIdAware.getAccountId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);
        String storeName = storeService.selectStoreInfo(accountId, storeId).getStoreName();
        model.addAttribute("storeName", storeName);

        List<SelectOptionResponseDto> options = storeOptionManagerService.selectOptions(storeId);
        List<SelectOptionGroupResponseDto> optionGroups = storeOptionManagerService.selectOptionGroups(storeId);

        model.addAttribute("storeId", storeId);
        model.addAttribute("options", options);
        model.addAttribute("optionGroups", optionGroups);
        model.addAttribute("createOptionRequestDto", createOptionRequestDto);
        model.addAttribute("createOptionGroupRequestDto", createOptionGroupRequestDto);

        commonInfo(model, accountId);

        return "store/menu/store-option-manager";
    }

    /**
     * 매장 옵션 그룹 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-option-group-manager")
    public String postCreateOptionGroup(
        @Valid @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        @PathVariable("storeId") Long storeId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:" + "/stores/" + storeId + "/store-option-manager";
        }
        storeOptionManagerService.createOptionGroup(storeId, createOptionGroupRequestDto);
        return "redirect:" + "/stores/" + storeId + "/store-option-manager";
    }

    /**
     * 매장 옵션 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-option-manager")
    public String postCreateOption(
        @Valid @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        @PathVariable("storeId") Long storeId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:" + "/stores/" + storeId + "/store-option-manager";
        }
        storeOptionManagerService.createOption(storeId, createOptionRequestDto);
        return "redirect:" + "/stores/" + storeId + "/store-option-manager";
    }

    /**
     * 매장 옵션 그룹 삭제 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.27
     */
    @DeleteMapping("/stores/{storeId}/store-option-group-manager/{optionGroupId}")
    public String postDeleteOptionGroup(@PathVariable("storeId") Long storeId,
                                        @PathVariable("optionGroupId") Long optionGroupId) {
        storeOptionManagerService.deleteOptionGroup(storeId, optionGroupId);
        return "redirect:" + "/stores/" + storeId + "/store-option-manager";
    }

    /**
     * 매장 옵션 삭제 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.27
     */
    @DeleteMapping("/stores/{storeId}/store-option-manager/{optionId}")
    public String postDeleteOption(@PathVariable("storeId") Long storeId, @PathVariable("optionId") Long optionId) {
        storeOptionManagerService.deleteOption(storeId, optionId);
        return "redirect:" + "/stores/" + storeId + "/store-option-manager";
    }

    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), NO_MENU)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }

        model.addAttribute("accountAddresses", accountAddresses);

        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

    }
}
