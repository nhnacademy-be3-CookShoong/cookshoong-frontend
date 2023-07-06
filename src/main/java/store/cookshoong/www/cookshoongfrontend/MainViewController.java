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
    @GetMapping("/storeinfo")
    public String getStoreInfo() {
        return "storeinfo";
    }

    /**
     * 상점 등록 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("/storeregister")
    public String getStoreRegistry() {
        return "storeregister";
    }

    /**
     * 상점 정보 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storeinfomanager")
    public String getStoreInfoManager() {
        return "storemanager/storeinfomanager";
    }

    /**
     * 상점 휴업일 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storetimemanager")
    public String getStoreTimeManager() {
        return "storemanager/storetimemanager";
    }

    /**
     * 상점 메뉴 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storemenumunager")
    public String getStoreMenuManager() {
        return "storemanager/storemenumanager";
    }

    /**
     * 상점 주문 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storeordermanager")
    public String getStoreOrderManager() {
        return "storemanager/storeordermanager";
    }

    /**
     * 상점 리뷰 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storereviewmanager")
    public String getStoreReviewManager() {
        return "storemanager/storereviewmanager";
    }

    /**
     * 상점 가맹점 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storefranchiseemanager")
    public String getStoreFranchiseeManager() {
        return "storemanager/storefranchiseemanager";
    }

    /**
     * 상점 쿠폰 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storecouponmanager")
    public String getStoreCouponManager() {
        return "storemanager/storecouponmanager";
    }

    /**
     * 상점 포인트 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storepointmanager")
    public String getStorePointManager() {
        return "storemanager/storepointmanager";
    }

    /**
     * 상점 매출 관리 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.06
     */
    @GetMapping("/storesalesmanager")
    public String getStoreSalesManager() {
        return "storemanager/storesalesmanager";
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
