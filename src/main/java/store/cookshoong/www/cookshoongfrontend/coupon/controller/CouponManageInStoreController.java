package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.EMPTY_CART;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateCashCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateIssueCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreatePercentCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 매장에서의 쿠폰 관리 엔드포인트.
 *
 * @author eora21 (김주호)
 * @since 2023.07.14
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/store-coupon-manager")
public class CouponManageInStoreController {
    private static final String REDIRECT_STORE_COUPON_INDEX_HEAD = "redirect:/stores/";
    private static final String REDIRECT_STORE_COUPON_INDEX_TAIL = "/store-coupon-manager";
    private final CouponManageService couponManageService;
    private final AccountIdAware account;
    private final AccountAddressService accountAddressService;
    private final CartService cartService;
    private final StoreService storeService;

    @ModelAttribute
    private Long storeId(@PathVariable Long storeId) {
        return storeId;
    }

    /**
     * 매장 쿠폰 정책 확인 엔드포인트.
     *
     * @param pageable the pageable
     * @param model    the model
     * @param storeId  the store id
     * @return the coupon policies
     */
    @GetMapping
    public String getStoreCouponPolicies(Pageable pageable, Model model, @ModelAttribute Long storeId) {
        Page<SelectPolicyResponseDto> policies = couponManageService.selectStorePolicy(pageable, storeId);
        model.addAttribute("policies", policies);
        model.addAttribute("buttonNumber", 5);
        model.addAttribute("storeId", storeId);

        commonInfo(model, account.getAccountId());

        return "coupon/coupon-store";
    }

    /**
     * 매장 금액 쿠폰 정책 생성 엔드포인트.
     *
     * @param request 쿠폰 정책 생성 dto
     * @param storeId the store id
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/cash")
    public String postStoreCashCouponPolicies(CreateCashCouponPolicyRequestDto request, @ModelAttribute Long storeId) {
        couponManageService.createStoreCashCouponPolicy(request, storeId);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 매장 퍼센트 쿠폰 정책 생성 엔드포인트.
     *
     * @param request 쿠폰 정책 생성 dto
     * @param storeId the store id
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/percent")
    public String postStorePercentCouponPolicies(CreatePercentCouponPolicyRequestDto request,
                                                 @ModelAttribute Long storeId) {
        couponManageService.createStorePercentCouponPolicy(request, storeId);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 쿠폰 정책 삭제 엔드포인트.
     *
     * @param policyId the policy id
     * @param storeId  the store id
     * @return 매장 쿠폰 view 화면
     */
    @DeleteMapping("/policies/{policyId}")
    public String deleteCouponPolicy(@PathVariable Long policyId, @ModelAttribute Long storeId) {
        couponManageService.removeCouponPolicy(policyId);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 쿠폰 발행 엔드포인트.
     *
     * @param createIssueCouponRequestDto 쿠폰 발행 dto
     * @param storeId                     the store id
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/issue")
    public String postCouponIssue(CreateIssueCouponRequestDto createIssueCouponRequestDto,
                                  @ModelAttribute Long storeId) {
        createIssueCouponRequestDto.setIssueMethod(CreateIssueCouponRequestDto.IssueMethod.NORMAL);
        couponManageService.createIssueCoupon(createIssueCouponRequestDto);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 매장 쿠폰 관리 페이지 주소를 반환하는 메서드.
     *
     * @param storeId the store id
     * @return the string
     */
    public static String redirectStoreCouponIndex(Long storeId) {
        return REDIRECT_STORE_COUPON_INDEX_HEAD + storeId + REDIRECT_STORE_COUPON_INDEX_TAIL;
    }

    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), EMPTY_CART)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }

        model.addAttribute("accountAddresses", accountAddresses);

        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

    }
}
