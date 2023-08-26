package store.cookshoong.www.cookshoongfrontend.account.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.EMPTY_CART;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.account.model.request.UpdateAccountInfoRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectOwnCouponResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectAccountOrderInStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.service.OrderService;
import store.cookshoong.www.cookshoongfrontend.point.model.response.PointLogResponseDto;
import store.cookshoong.www.cookshoongfrontend.point.service.PointService;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 마이페이지 컨트롤러.
 *
 * @author papel (윤동현), koesnam (추만석)
 * @since 2023.08.06
 */
@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final AccountService accountService;
    private final AccountAddressService accountAddressService;
    private final AccountIdAware accountIdAware;
    private final OrderService orderService;
    private final CouponManageService couponManageService;
    private final CartService cartService;
    private final PointService pointService;
    private final StoreService storeService;

    /**
     * 회원정보 페이지 진입.
     *
     * @param updateAccountInfoRequestDto the update account info request dto
     * @param model                       the model
     * @return the my-page
     */
    @GetMapping("/my-page")
    public String getMyPage(UpdateAccountInfoRequestDto updateAccountInfoRequestDto, Model model) {

        addressListHeader(model, accountIdAware.getAccountId());

        model.addAttribute("updateAccountInfoRequestDto", updateAccountInfoRequestDto);
        model.addAttribute("accountInfo", accountService.selectAccountMypageInfo(accountIdAware.getAccountId()));

        commonInfo(model, accountIdAware.getAccountId());
        return "account/my-page";
    }

    /**
     * 회원 정보 수정 요청.
     *
     * @param updateAccountInfoRequestDto the update account info request dto
     * @param bindingResult               the binding result
     * @return the string
     */
    @PostMapping("/my-page")
    public String postMyPage(@Valid UpdateAccountInfoRequestDto updateAccountInfoRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/my-page";
        }
        accountService.updateAccountMyPageInfo(accountIdAware.getAccountId(), updateAccountInfoRequestDto);
        return "redirect:/my-page";
    }

    /**
     * 회원이 주소를 등록하는 페이지와 가지고 있는 모든 주소를 보여주는 메서드.
     *
     * @param createAccountAddressRequestDto 회원이 주소를 등록하는 Dto
     * @param model                          HTML 로 보낼 데이터
     * @return 회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @GetMapping("/my-address")
    public String getMyAddress(
        @ModelAttribute("createAccountAddressRequestDto") CreateAccountAddressRequestDto createAccountAddressRequestDto,
        Model model) {

        AddressResponseDto address =
            accountAddressService.selectAccountAddressRenewalAt(accountIdAware.getAccountId());

        addressListHeader(model, accountIdAware.getAccountId());

        model.addAttribute("latitude", address.getLatitude());
        model.addAttribute("longitude", address.getLongitude());

        commonInfo(model, accountIdAware.getAccountId());

        return "account/my-address";
    }

    /**
     * 회원이 주소를 등록하는 메서드.
     *
     * @param createAccountAddressRequestDto 회원이 주소를 등록하는 Dto
     * @param bindingResult                  입력에 대한 검증 결과
     * @param model                          HTML 로 보낼 데이터
     * @return 현제 주소 등록 페이지로 반환
     */
    @PostMapping("/my-address")
    public String postCreateAccountAddress(
        @ModelAttribute("createAccountAddressRequestDto")
        @Valid CreateAccountAddressRequestDto createAccountAddressRequestDto,
        BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("createAccountAddressRequestDto", createAccountAddressRequestDto);
            return "redirect:/my-address";
        }

        accountAddressService.createAccountAddress(accountIdAware.getAccountId(), createAccountAddressRequestDto);

        return "redirect:/my-address";
    }

    /**
     * 회원이 선택한 주소에 대해 갱신 날짜를 업데이트.
     *
     * @param addressId 주소 아이디
     * @return 현재 주소 등록 페이지로 반환
     */
    @PatchMapping("my-address/{addressId}")
    public String patchSelectAccountAddress(@PathVariable Long addressId) {

        accountAddressService.updateSelectAccountAddressRenewalAt(accountIdAware.getAccountId(), addressId);

        return "redirect:/my-address";
    }

    /**
     * 메인화면에서 주소를 선택할 때 메인 페이지로 돌아오는 메스드.
     *
     * @param addressId 주소 아이디
     * @return 현재 주소 등록 페이지로 반환
     */
    @PatchMapping("my-address/{addressId}/index")
    public String patchIndexSelectAccountAddress(@PathVariable Long addressId) {

        accountAddressService.updateSelectAccountAddressRenewalAt(accountIdAware.getAccountId(), addressId);

        return "redirect:/";
    }

    /**
     * 회원이 등록한 주소를 삭제하는 메서드.
     *
     * @param addressId 주소 아이디
     * @return 회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @DeleteMapping("/my-address/{addressId}")
    public String deleteDeleteAccountAddress(@PathVariable Long addressId) {

        accountAddressService.removeAccountAddress(accountIdAware.getAccountId(), addressId);

        return "redirect:/my-address";
    }

    /**
     * 내 주문 확인.
     *
     * @param pageable the pageable
     * @param model    the model
     * @return my orders
     */
    @GetMapping("/my-orders")
    public String getMyOrders(Pageable pageable, Model model, CreateReviewRequestDto createReviewRequestDto) {
        Long accountId = accountIdAware.getAccountId();
        Page<SelectAccountOrderInStatusResponseDto> orders =
            orderService.selectOwnOrder(accountId, pageable);

        addressListHeader(model, accountId);

        model.addAttribute("orders", orders);
        model.addAttribute("createReviewRequestDto", createReviewRequestDto);

        commonInfo(model, accountIdAware.getAccountId());
        return "account/my-orders";
    }

    private void addressListHeader(Model model, Long accountId) {
        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);

        model.addAttribute("accountAddresses", accountAddresses);

        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), EMPTY_CART)) {

            model.addAttribute("count", CART_COUNT_ZERO);
        } else {

            model.addAttribute("count", cartMenuCountDto.getCount());
        }
    }

    /**
     * 내 쿠폰 확인.
     *
     * @param pageable the pageable
     * @param model    the model
     * @return my coupons
     */
    @GetMapping("/my-coupons")
    public String getMyCoupons(Pageable pageable, Model model) {
        Long accountId = accountIdAware.getAccountId();
        Page<SelectOwnCouponResponseDto> coupons = couponManageService.selectOwnCoupons(accountId, pageable);

        addressListHeader(model, accountId);

        model.addAttribute("coupons", coupons);

        commonInfo(model, accountIdAware.getAccountId());
        return "account/my-coupons";
    }

    /**
     * 내 포인트 확인.
     *
     * @param pageable the pageable
     * @param model    the model
     * @return my points
     */
    @GetMapping("/my-points")
    public String getMyPoints(Pageable pageable, Model model) {
        Long accountId = accountIdAware.getAccountId();
        int totalPoint = pointService.selectPoint(accountId);
        model.addAttribute("totalPoint", totalPoint);

        Page<PointLogResponseDto> pointLogs = pointService.selectOwnPoints(accountId, pageable);

        addressListHeader(model, accountId);

        model.addAttribute("pointLogs", pointLogs);
        model.addAttribute("buttonNumber", 5);
        model.addAttribute("balance", pointLogs.stream()
            .mapToInt(PointLogResponseDto::getPointMovement)
            .sum()
        );

        commonInfo(model, accountIdAware.getAccountId());
        return "account/my-points";
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
