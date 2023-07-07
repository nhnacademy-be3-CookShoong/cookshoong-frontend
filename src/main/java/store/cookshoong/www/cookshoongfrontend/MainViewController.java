package store.cookshoong.www.cookshoongfrontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 각 메인 뷰 페이지를 연결하는 컨트롤러.
 *
 * @author koesnam
 * @since 2023.07.04
 */
@Controller
public class MainViewController {

    /**
     * 메인 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    /**
     * 상점 목록 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("/store")
    public String getStore() {
        return "store";
    }

    /**
     * 상점 상세 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("/store-info")
    public String getStoreInfo() {
        return "store-info";
    }

    /**
     * 상점 등록 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("/store-register")
    public String getStoreRegistry() {
        return "store-register";
    }

    /**
     * 상점 정보 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-info-manager")
    public String getStoreInfoManager() {
        return "storemanager/store-info-manager";
    }

    /**
     * 상점 휴업일 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-time-manager")
    public String getStoreTimeManager() {
        return "storemanager/store-time-manager";
    }

    /**
     * 상점 메뉴 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-menu-manager")
    public String getStoreMenuManager() {
        return "storemanager/store-menu-manager";
    }

    /**
     * 상점 주문 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-order-manager")
    public String getStoreOrderManager() {
        return "storemanager/store-order-manager";
    }

    /**
     * 상점 리뷰 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-review-manager")
    public String getStoreReviewManager() {
        return "storemanager/store-review-manager";
    }

    /**
     * 상점 가맹점 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-merchant-manager")
    public String getStoreFranchiseeManager() {
        return "storemanager/store-merchant-manager";
    }

    /**
     * 상점 쿠폰 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-coupon-manager")
    public String getStoreCouponManager() {
        return "storemanager/store-coupon-manager";
    }

    /**
     * 상점 포인트 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-point-manager")
    public String getStorePointManager() {
        return "storemanager/store-point-manager";
    }

    /**
     * 상점 매출 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/store-sales-manager")
    public String getStoreSalesManager() {
        return "storemanager/store-sales-manager";
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
