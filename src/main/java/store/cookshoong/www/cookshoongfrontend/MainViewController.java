package store.cookshoong.www.cookshoongfrontend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;

/**
 * 각 메인 뷰 페이지를 연결하는 컨트롤러.
 *
 * @author koesnam
 * @since 2023.07.04
 */
@Controller
@RequiredArgsConstructor
public class MainViewController {
    private final ApiProperties apiProperties;

    /**
     * 매장 랜딩 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping({"/index", ""})
    public String getIndex() {
        return "index";
    }

    /**
     * 비회원용 매장 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.17
     */
    @GetMapping({"/index-unsigned"})
    public String getIndexUnsigned() {
        return "index-unsigned";
    }

    /**
     * 매장 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.16
     */
    @GetMapping({"/index-store"})
    public String getIndexStore() {
        return "index-store";
    }

    /**
     * 메뉴 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.16
     */
    @GetMapping({"/index-menu"})
    public String getIndexMenu() {
        return "index-menu";
    }

    /**
     * 매장 가맹점 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-merchant-manager")
    public String getMerchantManager() {
        return "store/info/store-merchant-manager";
    }

    /**
     * 매장 주문 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-order-manager")
    public String getStoreOrderManager() {
        return "store-order-manager";
    }

    /**
     * 매장 배송 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-delivery-manager")
    public String getStoreDeliveryManager() {
        return "store-delivery-manager";
    }

    /**
     * 매장 결제 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-charge-manager")
    public String getStoreChargeManager() {
        return "store-charge-manager";
    }

    /**
     * 매장 쿠폰 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-coupon-manager")
    public String getStoreCouponManager() {
        return "store-coupon-manager";
    }

    /**
     * 매장 포인트 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-point-manager")
    public String getStorePointManager() {
        return "store-point-manager";
    }

    /**
     * 매장 리뷰 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-review-manager")
    public String getStoreReviewManager() {
        return "store-review-manager";
    }

    /**
     * 시스템 관리자를 위한 뷰 페이지를 맵핑.
     *
     * @author koesnam
     * @since 2023.07.04
     */
    @GetMapping("/admin")
    public String adminMain() {
        return "/admin/index";
    }
}
