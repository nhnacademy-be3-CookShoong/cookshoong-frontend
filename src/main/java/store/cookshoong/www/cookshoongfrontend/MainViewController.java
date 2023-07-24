package store.cookshoong.www.cookshoongfrontend;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreForUserResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoresKeywordSearchResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoresNotOutedResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;

/**
 * 메인 뷰 페이지 컨트롤러.
 *
 * @author koesnam
 * @since 2023.07.04
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainViewController {
    private final StoreService storeService;
    private final StoreMenuManagerService storeMenuManagerService;
    private final StoreOptionManagerService storeOptionManagerService;

    /**
     * 매장 기본 랜딩 페이지 맵핑.
     *
     * @param pageable the pageable
     * @param model    the model
     * @return the index
     */
    @GetMapping({"", "/index"})
    public String getIndexByDistance(Pageable pageable, Model model) {
        RestResponsePage<SelectStoresNotOutedResponseDto> stores = storeService.selectStoresNotOuted(1L, pageable);
        List<SelectStoresNotOutedResponseDto> distinctStores = stores.stream()
            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SelectStoresNotOutedResponseDto::getId))),
                ArrayList::new));
        model.addAttribute("allStore", distinctStores);
        model.addAttribute("stores", stores);
        return "index/index";
    }

    /**
     * 매장 검색 랜딩 페이지 맵핑.
     *
     * @param keywordText the keywordText
     * @param pageable    the pageable
     * @param model       the model
     * @return the index
     */
    @GetMapping("/index/search")
    public String getIndexByKeyword(@RequestParam("keyword") String keywordText, Pageable pageable, Model model) {
        RestResponsePage<SelectStoresKeywordSearchResponseDto> searchedStores = storeService.selectStoresByKeyword(keywordText, pageable);
        model.addAttribute("searchStores", searchedStores);
        return "index/index";
    }

    /**
     * 매장 페이지 맵핑.
     *
     * @param storeId the store id
     * @param model   the model
     * @return the index store
     */
    @GetMapping({"/index/store/{storeId}"})
    public String getIndexStore(@PathVariable("storeId") Long storeId, Model model) {
        SelectStoreForUserResponseDto store = storeService.selectStoreForUser(storeId);
        model.addAttribute("store", store);
        List<SelectMenuResponseDto> menus = storeMenuManagerService.selectMenus(storeId);
        model.addAttribute("menus", menus);
        return "index/store";
    }

    /**
     * 메뉴 페이지 맵핑.
     *
     * @param storeId the store id
     * @param menuId  the menu id
     * @param model   the model
     * @return the index menu
     */
    @GetMapping({"/index/store/{storeId}/menu/{menuId}"})
    public String getIndexMenu(@PathVariable("storeId") Long storeId, @PathVariable("menuId") Long menuId, Model model) {
        SelectMenuResponseDto menu = storeMenuManagerService.selectMenu(storeId, menuId);
        model.addAttribute("menu", menu);
        List<SelectOptionResponseDto> options = storeOptionManagerService.selectOptions(storeId);
        model.addAttribute("options", options);

        return "index/menu";
    }

    /**
     * 이미지 로드.
     *
     * @param imageName the image name
     * @return the image
     * @throws MalformedURLException the malformed url exception
     */
    @ResponseBody
    @GetMapping("/images")
    public Resource getImage(@RequestParam("imageName") String imageName) throws MalformedURLException {
        return new UrlResource("file:" + imageName);
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
        return "redirect:/coupon";
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
