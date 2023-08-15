package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateCashCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateIssueCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreatePercentCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBankRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateCategoriesRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMerchantRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateCategoryRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllMerchantsResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.MerchantService;
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
    private final MerchantService merchantService;
    private final CouponManageService couponManageService;

    /**
     * 은행 리스트 가져오는 컨트롤러.
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
     * 은행 추가 컨트롤러.
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
     * 가맹점 조회하는 컨트롤러.
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
     * 가맹점 등록하는 컨트롤러.
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
     * 가맹점 삭제하는 컨트롤러.
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
     * 카테고리 조회 컨트롤러.
     *
     * @param pageable                   the pageable
     * @param model                      the model
     * @param createCategoriesRequestDto the create categories request dto
     * @param updateCategoryRequestDto   the update category request dto
     * @return the merchants
     */
    @GetMapping("/categories")
    public String getCategories(@PageableDefault(size = 5) Pageable pageable,
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
     * 카테고리 등록 컨트롤러.
     *
     * @param createCategoriesRequestDto the create categories request dto
     * @param bindingResult              the binding result
     * @param model                      the model
     * @return the string
     */
    @PostMapping("/categories")
    public String postCreateCategory(@Valid @ModelAttribute("createCategoriesRequestDto") CreateCategoriesRequestDto createCategoriesRequestDto,
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
     * 카테고리 삭제 컨트롤러.
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
     * 카테고리 수정 컨트롤러.
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

    /**
     * Gets event coupons.
     *
     * @param model      the model
     * @param merchantId the merchant id
     * @param pageable   the pageable
     * @return the event coupons
     */
    @GetMapping("/coupons")
    public String getEventCoupons(Model model, @RequestParam(required = false) Long merchantId, Pageable pageable) {
        List<SelectAllMerchantsResponseDto> merchants = merchantService.selectAllMerchants();
        model.addAttribute("merchants", merchants);

        Page<SelectPolicyResponseDto> policies = couponManageService.selectCouponPolicy(merchantId, pageable);
        model.addAttribute("policies", policies);

        return "coupon/coupon-admin";
    }

    /**
     * 금액 쿠폰 정책 생성 엔드포인트.
     *
     * @param request    쿠폰 정책 생성 dto
     * @param merchantId the store id
     * @return 쿠폰 view 화면
     */
    @PostMapping("/coupons/cash")
    public String postCashCouponPolicies(CreateCashCouponPolicyRequestDto request,
                                         @RequestParam(required = false) Long merchantId) {
        couponManageService.createCashCouponPolicy(request, merchantId);
        return redirectAdminCouponIndex(merchantId);
    }

    private static String redirectAdminCouponIndex(Long merchantId) {
        return "redirect:/admin/management/coupons" + getParam(merchantId);
    }

    private static String getParam(Long merchantId) {
        if (Objects.isNull(merchantId)) {
            return "";
        }

        return "?merchantId=" + merchantId;
    }

    /**
     * 퍼센트 쿠폰 정책 생성 엔드포인트.
     *
     * @param request    쿠폰 정책 생성 dto
     * @param merchantId the merchant id
     * @return 쿠폰 view 화면
     */
    @PostMapping("/coupons/percent")
    public String postPercentCouponPolicies(CreatePercentCouponPolicyRequestDto request,
                                            @RequestParam(required = false) Long merchantId) {
        couponManageService.createPercentCouponPolicy(request, merchantId);
        return redirectAdminCouponIndex(merchantId);
    }

    /**
     * 쿠폰 정책 삭제 엔드포인트.
     *
     * @param policyId   the policy id
     * @param merchantId the store id
     * @return 쿠폰 view 화면
     */
    @DeleteMapping("/coupons/policies/{policyId}")
    public String deleteCouponPolicy(@PathVariable Long policyId, @RequestParam(required = false) Long merchantId) {
        couponManageService.removeCouponPolicy(policyId);
        return redirectAdminCouponIndex(merchantId);
    }

    /**
     * 쿠폰 발행 엔드포인트.
     *
     * @param createIssueCouponRequestDto 쿠폰 발행 dto
     * @param merchantId                  the store id
     * @return 쿠폰 view 화면
     */
    @PostMapping("/coupons/issue")
    public String postCouponIssue(CreateIssueCouponRequestDto createIssueCouponRequestDto,
                                  @RequestParam(required = false) Long merchantId) {
        createIssueCouponRequestDto.setIssueMethod(CreateIssueCouponRequestDto.IssueMethod.EVENT);
        couponManageService.createIssueCoupon(createIssueCouponRequestDto);
        return redirectAdminCouponIndex(merchantId);
    }
}
