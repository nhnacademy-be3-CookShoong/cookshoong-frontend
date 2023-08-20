package store.cookshoong.www.cookshoongfrontend;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

import java.net.MalformedURLException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.util.CustomAuthorityUtils;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewStoreResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.service.ReviewStoreService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreForUserResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoresKeywordSearchResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreCategoryService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 메인 뷰 페이지 컨트롤러.
 *
 * @author koesnam
 * @contributor jeongjewan
 * @since 2023.07.04
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainViewController {
    private final StoreService storeService;
    private final StoreMenuManagerService storeMenuManagerService;
    private final StoreOptionManagerService storeOptionManagerService;
    private final AccountAddressService accountAddressService;
    private final AccountIdAware accountIdAware;
    private final StoreCategoryService storeCategoryService;
    private final CartService cartService;
    private final ReviewStoreService reviewStoreService;
    private static final Long DEFAULT_ADDRESS_ID = 1L;

    /**
     * 매장 기본 랜딩 페이지 맵핑.
     *
     * @param pageable the pageable
     * @param model    the model
     * @return the index
     */
    @GetMapping
    public String getIndex(@PageableDefault Pageable pageable, Principal principal, Model model) {
        Long addressId = DEFAULT_ADDRESS_ID;

        if (Objects.nonNull(principal)) {
            Long accountId = accountIdAware.getAccountId();
            addressId = accountAddressService.selectAccountAddressRenewalAt(accountId).getId();

            commonInfo(model, accountId);

            if (CustomAuthorityUtils.match("ROLE_BUSINESS", ((JwtAuthentication) principal).getAuthorities())) {
                List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
                model.addAttribute("businessStoreList", businessStoreList);
            }
        }
        List<SelectAllCategoriesResponseDto> categories = storeCategoryService.selectAllCategories();
        RestResponsePage<SelectStoresKeywordSearchResponseDto> stores = storeService.selectStoresNotOuted(addressId, pageable);
        RestResponsePage<SelectStoresKeywordSearchResponseDto> storesByRating = storeService.selectStoresByRating(addressId, pageable);
        model.addAttribute("categories", categories);
        model.addAttribute("addressId", addressId);
        model.addAttribute("stores", stores);
        model.addAttribute("storesByRating", storesByRating);

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
    public String getIndexByKeyword(@PageableDefault Pageable pageable, @RequestParam("keyword") String keywordText,  Model model) {

        Long accountId = accountIdAware.getAccountId();
        Long addressId = accountAddressService.selectAccountAddressRenewalAt(accountId).getId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

        List<SelectAllCategoriesResponseDto> categories = storeCategoryService.selectAllCategories();
        RestResponsePage<SelectStoresKeywordSearchResponseDto> searchedStores = storeService.selectStoresByKeyword(keywordText, addressId, pageable);
        RestResponsePage<SelectStoresKeywordSearchResponseDto> storesByRating = storeService.selectStoresByRating(addressId, pageable);
        model.addAttribute("categories", categories);
        model.addAttribute("addressId", addressId);
        model.addAttribute("keywordText", keywordText);
        model.addAttribute("stores", searchedStores);
        model.addAttribute("storesByRating", storesByRating);

        return "index/index";
    }

    /**
     * 매장 기본 랜딩 페이지 페이지 호출 컨트롤러.
     *
     * @param addressId  the addressId
     * @param page       the page
     * @param size       the size
     * @return the index
     */
    @GetMapping("/search/page/distance")
    public ResponseEntity<RestResponsePage<SelectStoresKeywordSearchResponseDto>> getStoresNotOuted(
        @RequestParam("addressId") Long addressId,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        RestResponsePage<SelectStoresKeywordSearchResponseDto> stores = storeService.selectStoresNotOuted(addressId, pageable);
        return ResponseEntity.ok(stores);
    }

    /**
     * 매장 검색 랜딩 페이지 페이지 호출 컨트롤러.
     *
     * @param keyword    the keywordText
     * @param addressId  the addressId
     * @param page       the page
     * @param size       the size
     * @return the index
     */
    @GetMapping("/search/page/keyword")
    public ResponseEntity<RestResponsePage<SelectStoresKeywordSearchResponseDto>> getStoresByKeyword(
        @RequestParam("keyword") String keyword,
        @RequestParam("addressId") Long addressId,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        RestResponsePage<SelectStoresKeywordSearchResponseDto> stores = storeService.selectStoresByKeyword(keyword, addressId, pageable);
        return ResponseEntity.ok(stores);
    }

    /**
     * 매장 페이지 맵핑.
     *
     * @param storeId the store id
     * @param model   the model
     * @return the index store
     */
    @GetMapping({"/index/store/{storeId}"})
    public String getIndexStore(@PathVariable("storeId") Long storeId, @PageableDefault Pageable pageable,
                                Model model) {

        Long accountId = accountIdAware.getAccountId();
        Long addressId = accountAddressService.selectAccountAddressRenewalAt(accountId).getId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

        commonInfo(model, accountId);

        Page<SelectReviewStoreResponseDto> reviewList = reviewStoreService.selectReviewByAccount(storeId, pageable);
        SelectStoreForUserResponseDto store = storeService.selectStoreForUser(addressId, storeId);
        List<SelectMenuGroupResponseDto> menuGroups = storeMenuManagerService.selectMenuGroups(storeId);
        List<SelectMenuResponseDto> menus = storeMenuManagerService.selectMenus(storeId);

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("store", store);
        model.addAttribute("menuGroups", menuGroups);
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
    public String getIndexMenu(@PathVariable("storeId") Long storeId,
                               @PathVariable("menuId") Long menuId,
                               Model model) {

        Long accountId = accountIdAware.getAccountId();
        Long addressId = accountAddressService.selectAccountAddressRenewalAt(accountId).getId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

        commonInfo(model, accountId);

        if (!cartService.existMenuInCartRedis(String.valueOf(accountIdAware.getAccountId()), NO_MENU)) {
            List<CartRedisDto> cartItems = cartService.selectCartMenuAll(String.valueOf(accountId));
            Long cartStoreId = cartItems.get(0).getStoreId();
            model.addAttribute("cartStoreId", cartStoreId);
        } else {
            model.addAttribute("cartStoreId", storeId);
        }

        SelectStoreForUserResponseDto store = storeService.selectStoreForUser(addressId, storeId);
        SelectMenuResponseDto menu = storeMenuManagerService.selectMenu(storeId, menuId);
        List<SelectOptionGroupResponseDto> optionGroups = storeOptionManagerService.selectOptionGroupsByMenu(storeId, menuId);
        List<SelectOptionResponseDto> options = storeOptionManagerService.selectOptions(storeId);

        model.addAttribute("accountId", accountIdAware.getAccountId());
        model.addAttribute("storeId", storeId);
        model.addAttribute("store", store);
        model.addAttribute("menu", menu);
        model.addAttribute("optionGroups", optionGroups);
        model.addAttribute("options", options);

        return "index/menu";
    }

    /**
     * 장바구니에 메뉴 등록.
     *
     * @param cartRedisDto 장바구니에 담기 정보
     * @return 해당 매장으로 반환
     */
    @PostMapping("/index/store/menu/cart")
    public String postCreateCart(@Valid CartRedisDto cartRedisDto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index/menu";
        }
        cartRedisDto.setAccountId(accountIdAware.getAccountId());

        cartService.createCart(
            String.valueOf(accountIdAware.getAccountId()), cartRedisDto.generateUniqueHashKey(), cartRedisDto);

        return "redirect:/index/store/" + cartRedisDto.getStoreId();
    }

    /**
     * 이미지 로드.
     *
     * @param imageName the image name
     * @return the image
     * @throws MalformedURLException the malformed url exception
     */
    @ResponseBody
    @GetMapping("/images/local")
    public Resource getImage(@RequestParam("imageName") String imageName) throws MalformedURLException {
        return new UrlResource("file:" + imageName);
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

    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), NO_MENU)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }
        model.addAttribute("accountAddresses", accountAddresses);
    }
}
