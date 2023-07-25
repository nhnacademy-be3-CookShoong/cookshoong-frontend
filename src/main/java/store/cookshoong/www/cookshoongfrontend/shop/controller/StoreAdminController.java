package store.cookshoong.www.cookshoongfrontend.shop.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBankRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateCategoriesRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMerchantRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateCategoryRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllMerchantsResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreAdminService;

/**
 * 관리자 : 매장과 관련하여 관리자가 관리해야할 사항들. etc) 은행, 카테고리, 가맹점.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.24
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/management")
public class StoreAdminController {
    private final StoreAdminService storeAdminService;

    /**
     * d.
     *
     * @param pageable             the pageable
     * @param model                the model
     * @param createBankRequestDto the create bank request dto
     * @return the banks
     */
    @GetMapping("/banks")
    public String getBanks(@PageableDefault(size = 5) Pageable pageable,
                           Model model,
                           CreateBankRequestDto createBankRequestDto) {
        RestResponsePage<SelectAllBanksResponseDto> banks = storeAdminService.selectAllBanks(pageable);
        model.addAttribute("createBankRequestDto", createBankRequestDto);
        model.addAttribute("banks", banks);
        model.addAttribute("buttonNumber", 5);
        return "store/admin/admin-bank-manager";
    }

    /**
     * Post create bank string.
     *
     * @param createBankRequestDto the create bank request dto
     * @param bindingResult        the binding result
     * @param model                the model
     * @return the string
     */
    @PostMapping("/banks")
    public String postCreateBank(@Valid @ModelAttribute("createBankRequestDto") CreateBankRequestDto createBankRequestDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("createBankRequestDto", createBankRequestDto);
            return "store/admin/admin-bank-manager";
        }
        storeAdminService.createBank(createBankRequestDto);
        return "redirect:/admin/management/banks";
    }

    /**
     * Gets merchants.
     *
     * @param pageable                 the pageable
     * @param model                    the model
     * @param createMerchantRequestDto the create merchant request dto
     * @return the merchants
     */
    @GetMapping("/merchants")
    public String getMerchants(@PageableDefault(size = 5) Pageable pageable,
                               Model model,
                               CreateMerchantRequestDto createMerchantRequestDto) {
        RestResponsePage<SelectAllMerchantsResponseDto> merchants = storeAdminService.selectAllMerchants(pageable);
        model.addAttribute("createMerchantRequestDto", createMerchantRequestDto);
        model.addAttribute("merchants", merchants);
        model.addAttribute("buttonNumber", 5);
        return "store/admin/admin-merchant-manager";
    }

    /**
     * Post create merchant string.
     *
     * @param createMerchantRequestDto the create merchant request dto
     * @param bindingResult            the binding result
     * @param model                    the model
     * @return the string
     */
    @PostMapping("/merchants")
    public String postCreateMerchant(@Valid @ModelAttribute("createMerchantRequestDto") CreateMerchantRequestDto createMerchantRequestDto,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("createMerchantRequest", createMerchantRequestDto);
            return "store/admin/admin-merchant-manager";
        }
        storeAdminService.createMerchant(createMerchantRequestDto);
        return "redirect:/admin/management/merchants";
    }

    /**
     * Delete merchant string.
     *
     * @param merchantId the merchant id
     * @return the string
     */
    @DeleteMapping("/merchants/{merchantId}")
    public String deleteMerchant(@PathVariable("merchantId") Long merchantId) {
        storeAdminService.removeMerchant(merchantId);
        return "redirect:/admin/management/merchants";
    }

    /**
     * Gets merchants.
     *
     * @param pageable                   the pageable
     * @param model                      the model
     * @param createCategoriesRequestDto the create categories request dto
     * @param updateCategoryRequestDto   the update category request dto
     * @return the merchants
     */
    @GetMapping("/categories")
    public String getMerchants(@PageableDefault(size = 5) Pageable pageable,
                               Model model,
                               CreateCategoriesRequestDto createCategoriesRequestDto,
                               UpdateCategoryRequestDto updateCategoryRequestDto) {
        RestResponsePage<SelectAllCategoriesResponseDto> categories = storeAdminService.selectAllCategories(pageable);
        model.addAttribute("createCategoriesRequestDto", createCategoriesRequestDto);
        model.addAttribute("updateCategoryRequestDto", updateCategoryRequestDto);
        model.addAttribute("categories", categories);
        model.addAttribute("buttonNumber", 5);
        return "store/admin/admin-category-manager";
    }

    /**
     * Post create merchant string.
     *
     * @param createCategoriesRequestDto the create categories request dto
     * @param bindingResult              the binding result
     * @param model                      the model
     * @return the string
     */
    @PostMapping("/categories")
    public String postCreateMerchant(@Valid @ModelAttribute("createCategoriesRequestDto") CreateCategoriesRequestDto createCategoriesRequestDto,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("createCategoriesRequestDto", createCategoriesRequestDto);
            return "store/admin/admin-category-manager";
        }
        storeAdminService.createCategory(createCategoriesRequestDto);
        return "redirect:/admin/management/categories";
    }

    /**
     * Delete category string.
     *
     * @param categoryCode the category code
     * @return the string
     */
    @DeleteMapping("/categories/{categoryCode}")
    public String deleteCategory(@PathVariable("categoryCode") String categoryCode) {
        storeAdminService.removeCategory(categoryCode);
        return "redirect:/admin/management/categories";
    }

    /**
     * Patch category string.
     *
     * @param categoryCode             the category code
     * @param updateCategoryRequestDto the update category request dto
     * @param bindingResult            the binding result
     * @param model                    the model
     * @return the string
     */
    @PatchMapping("/categories/{categoryCode}")
    public String patchCategory(@PathVariable("categoryCode") String categoryCode,
                                @Valid @ModelAttribute("updateCategoryRequestDto") UpdateCategoryRequestDto updateCategoryRequestDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updateCategoryRequestDto", updateCategoryRequestDto);
            return "store/admin/admin-category-manager";
        }
        storeAdminService.updateCategory(updateCategoryRequestDto, categoryCode);
        return "redirect:/admin/management/categories";
    }
}
