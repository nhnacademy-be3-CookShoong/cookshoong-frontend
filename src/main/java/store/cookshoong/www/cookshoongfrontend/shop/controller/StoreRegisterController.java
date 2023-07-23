package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllMerchantsResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.BankService;
import store.cookshoong.www.cookshoongfrontend.shop.service.MerchantService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreCategoryService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;

/**
 * 매장 등록 페이지 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.07.09
 */
@Controller
@RequiredArgsConstructor
public class StoreRegisterController {

    private final StoreService storeService;
    private final StoreCategoryService storeCategoryService;
    private final MerchantService merchantService;
    private final BankService bankService;
    private final AccountIdAware accountIdAware;

    /**
     * 매장 등록 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.09
     */
    @GetMapping("/store-register")
    public String getSelectStore(CreateStoreRequestDto createStoreRequestDto, Model model) {
        List<SelectAllMerchantsResponseDto> merchants = merchantService.selectAllMerchants();
        List<SelectAllCategoriesResponseDto> storeCategories = storeCategoryService.selectAllCategories();
        List<SelectAllBanksResponseDto> banks = bankService.selectAllBanks();

        model.addAttribute("banks", banks);
        model.addAttribute("merchants", merchants);
        model.addAttribute("categories", storeCategories);
        model.addAttribute("createStoreRequestDto", createStoreRequestDto);
        return "store/register/store-register";
    }


    /**
     * 매장 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.09
     */
    @PostMapping("/store-register")
    public String postCreateStore(
        @Valid @ModelAttribute("createStoreRequestDto") CreateStoreRequestDto createStoreRequestDto,
        BindingResult bindingResult, Model model,
        @RequestPart("businessLicense") MultipartFile businessLicense,
        @RequestPart("storeImage") MultipartFile storeImage) {

        Long accountId = accountIdAware.getAccountId();

        storeService.createStore(accountId, createStoreRequestDto, businessLicense, storeImage);

        if (bindingResult.hasErrors()) {
            model.addAttribute("createStoreRequestDto", createStoreRequestDto);
            return "store/register/store-register";
        }
        return "redirect:/stores";
    }

    @GetMapping("/stores")
    public String getStoresForBusiness(Pageable pageable,
                                       Model model,
                                       UpdateStoreStatusRequestDto requestDto) {
        Long accountId = accountIdAware.getAccountId();
        RestResponsePage<SelectAllStoresResponseDto> stores = storeService.selectStores(accountId, pageable);
        List<SelectAllStatusResponseDto> status = storeService.selectStatus();

        model.addAttribute("status", status);
        model.addAttribute("stores", stores);
        model.addAttribute("updateStoreStatusRequestDto", requestDto);
        model.addAttribute("buttonNumber", 5);
        return "store/info/store-list";
    }

    @PatchMapping("/stores/{storeId}/status")
    public String patchStoreStatus(@PathVariable("storeId") Long storeId,
                                   @Valid @ModelAttribute("updateStatus") UpdateStoreStatusRequestDto updateStatus,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("UpdateStoreStatusRequestDto", updateStatus);
            return "redirect:/stores";
        }
        Long accountId = accountIdAware.getAccountId();

        storeService.updateStatus(accountId, storeId, updateStatus);
        return "redirect:/stores";
    }
}
