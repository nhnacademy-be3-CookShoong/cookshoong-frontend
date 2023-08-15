package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreCategoryRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreInfoRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreManagerRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreInfoResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreCategoryService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreInfoManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 매장 정보 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel (윤동현), seongyeon (유승연)
 * @since 2023.07.09
 */
@Controller
@RequiredArgsConstructor
public class StoreInfoManagerController {

    private final StoreService storeService;
    private final StoreCategoryService storeCategoryService;
    private final StoreInfoManagerService storeInfoManagerService;
    private final AccountIdAware accountIdAware;

    /**
     * 매장 정보 조회.
     *
     * @param storeId                       the store id
     * @param model                         the model
     * @param updateStoreCategoryRequestDto the update store category request dto
     * @return 매장 정보
     * @throws IOException the io exception
     */
    @GetMapping("/stores/{storeId}/store-info-manager")
    public String getStoreInfo(
        @PathVariable("storeId") Long storeId,
        Model model,
        UpdateStoreCategoryRequestDto updateStoreCategoryRequestDto) {

        Long accountId = accountIdAware.getAccountId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        SelectStoreInfoResponseDto storeInfo = storeService.selectStoreInfo(accountId, storeId);
        String storeName = storeInfo.getStoreName();
        List<SelectAllCategoriesResponseDto> selectCategories = storeCategoryService.selectAllCategories();
        List<SelectAllBanksResponseDto> banks = storeService.selectAllBanks();

        model.addAttribute("businessStoreList", businessStoreList);
        model.addAttribute("storeName", storeName);
        model.addAttribute("storeInfo", storeInfo);

        UpdateStoreManagerRequestDto updateStoreManagerRequestDto =
            new UpdateStoreManagerRequestDto(storeInfo.getRepresentativeName(),
                storeInfo.getBankCode(), storeInfo.getBankAccountNumber());
        model.addAttribute("updateStoreManagerInfo", updateStoreManagerRequestDto);
        UpdateStoreInfoRequestDto updateStoreInfoRequestDto =
            new UpdateStoreInfoRequestDto(storeInfo.getOpeningDate(), storeInfo.getStoreName(), storeInfo.getMainPlace(), storeInfo.getDetailPlace(),
                storeInfo.getLatitude(), storeInfo.getLongitude(), storeInfo.getPhoneNumber(), storeInfo.getDescription(), storeInfo.getDefaultEarningRate(),
                storeInfo.getMinimumOrderPrice(), storeInfo.getDeliveryCost());
        model.addAttribute("updateStoreInfoRequestDto", updateStoreInfoRequestDto);

        model.addAttribute("updateStoreCategory", updateStoreCategoryRequestDto);

        model.addAttribute("selectCategories", selectCategories);
        model.addAttribute("selectBanks", banks);

        return "store/info/store-info-manager";
    }

    /**
     * 사업자 : 사업자 정보에 대한 정보 수정.
     *
     * @param storeId                      the store id
     * @param updateStoreManagerRequestDto the update store manager request dto
     * @param bindingResult                the binding result
     * @return the string
     */
    @PatchMapping("/stores/{storeId}/store-info-manager/managerInfo")
    public String patchStoreBusinessInfo(@PathVariable("storeId") Long storeId,
                                         @Valid @ModelAttribute("updateStoreManagerInfo") UpdateStoreManagerRequestDto updateStoreManagerRequestDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/stores/" + storeId + "/store-info-manager";
        }
        Long accountId = accountIdAware.getAccountId();
        storeInfoManagerService.updateStoreManagerInfo(accountId, storeId, updateStoreManagerRequestDto);
        return "redirect:/stores/" + storeId + "/store-info-manager";
    }

    /**
     * 사업자 : 영업점 정보에 대한 정보 수정.
     *
     * @param storeId                   the store id
     * @param updateStoreInfoRequestDto the update store info request dto
     * @param bindingResult             the binding result
     * @return the string
     */
    @PatchMapping("/stores/{storeId}/store-info-manager/storeInfo")
    public String patchStoreInfo(@PathVariable("storeId") Long storeId,
                                 @Valid @ModelAttribute("updateStoreInfoRequestDto") UpdateStoreInfoRequestDto updateStoreInfoRequestDto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/stores/" + storeId + "/store-info-manager";
        }
        Long accountId = accountIdAware.getAccountId();
        storeInfoManagerService.updateStoreInfo(accountId, storeId, updateStoreInfoRequestDto);
        return "redirect:/stores/" + storeId + "/store-info-manager";
    }

    /**
     * 사업자 : 카테고리 정보에 대한 정보 수정.
     *
     * @param storeId             the store id
     * @param updateStoreCategory the update store category
     * @param bindingResult       the binding result
     * @return the string
     */
    @PatchMapping("/stores/{storeId}/store-info-manager/categoryInfo")
    public String patchStoreCategoryInfo(@PathVariable("storeId") Long storeId,
                                         @Valid @ModelAttribute("updateStoreCategory") UpdateStoreCategoryRequestDto updateStoreCategory,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/stores/" + storeId + "/store-info-manager";
        }
        Long accountId = accountIdAware.getAccountId();
        storeInfoManagerService.updateStoreCategoryInfo(accountId, storeId, updateStoreCategory);

        return "redirect:/stores/" + storeId + "/store-info-manager";

    }

    /**
     * 사업자 : 매장 이미지에 대한 정보 수정.
     *
     * @param storeId       the store id
     * @param multipartFile the multipart file
     * @return the string
     */
    @PatchMapping("/stores/{storeId}/store-info-manager/storeImage")
    public String patchStoreImage(@PathVariable("storeId") Long storeId,
                                  @RequestPart("uploadImage") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return "redirect:/stores/" + storeId + "/store-info-manager";
        }
        Long accountId = accountIdAware.getAccountId();
        storeInfoManagerService.updateStoreImage(accountId, storeId, multipartFile);
        return "redirect:/stores/" + storeId + "/store-info-manager";
    }

    /**
     * 사업자 : 매장 상태 변경.
     *
     * @param storeId the store id
     * @param option  the option
     * @return the boolean
     */
    @PatchMapping("/stores/{storeId}/status")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public boolean patchStoreStatus(@PathVariable("storeId") Long storeId,
                                    @RequestParam("option") String option) {
        Long accountId = accountIdAware.getAccountId();
        storeInfoManagerService.updateStoreStatus(accountId, storeId, option);
        return true;
    }

    /**
     * 사업자 : 등록한 매장의 이미지를 삭제.
     *
     * @param storeId the store id
     * @return the string
     */
    @DeleteMapping("/stores/{storeId}/store-info-manager/storeImage/delete")
    public String deleteStoreImage(@PathVariable("storeId") Long storeId) {
        Long accountId = accountIdAware.getAccountId();
        storeInfoManagerService.removeStoreIamge(accountId, storeId);
        return "redirect:/stores/" + storeId + "/store-info-manager";
    }
}
