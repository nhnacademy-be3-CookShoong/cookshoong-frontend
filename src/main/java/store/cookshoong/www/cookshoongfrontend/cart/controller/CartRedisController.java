package store.cookshoong.www.cookshoongfrontend.cart.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.EMPTY_CART;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartOptionDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartViewDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderPossibleResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.service.OrderService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 회원 장바구니에 대한 저장, 수정, 삭제 에 대한 Controller.
 *
 * @author jeongjewan
 * @since 2023.07.25
 */
@Slf4j
@Controller
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartRedisController {
    private final StoreService storeService;
    private final CartService cartService;
    private final StoreMenuManagerService storeMenuManagerService;
    private final StoreOptionManagerService storeOptionManagerService;
    private final AccountAddressService accountAddressService;
    private final AccountIdAware accountIdAware;
    private final OrderService orderService;

    /**
     * 회원 장바구니에 담겨있는 메뉴를 보여주는 Controller.
     *
     * @param model HTML 로 보낼 데이터
     * @return 회원이 담은 장바구니 페이지로 반환
     */
    @GetMapping
    public String getCartMenuAll(Model model) {
        model.addAttribute("orderPossible", false);
        model.addAttribute("explain", "장바구니가 비어있습니다.");

        Long accountId = accountIdAware.getAccountId();
        List<CartRedisDto> cartItems = cartService.selectCartMenuAll(String.valueOf(accountIdAware.getAccountId()));
        List<CartViewDto> optionGroupInfoAndOption = new ArrayList<>();

        cartCountInfo(model, accountId);

        if (!cartService.existMenuInCartRedis(String.valueOf(accountIdAware.getAccountId()), EMPTY_CART)) {
            createCartDetail(model, cartItems, optionGroupInfoAndOption);
            checkOrderPossible(accountId, model);
        }

        model.addAttribute("cartItems", cartItems);

        String lineSeparator = System.getProperty("line.separator");
        model.addAttribute("lineSeparator", lineSeparator);

        return "cart/cart-list";
    }

    private void checkOrderPossible(Long accountId, Model model) {
        Long storeId = (Long) model.getAttribute("storeId");
        SelectOrderPossibleResponseDto checkOrder = orderService.isOrderPossible(storeId, accountId);
        model.addAttribute("orderPossible", checkOrder.isOrderPossible());
        model.addAttribute("explain", checkOrder.getExplain());
    }

    private void createCartDetail(Model model, List<CartRedisDto> cartItems, List<CartViewDto> optionGroupInfoAndOption) {
        int totalPrice = cartService.calculateTotalPrice(cartItems);
        Long storeId = cartItems.get(0).getStoreId();
        List<SelectOptionGroupResponseDto> optionGroups = storeOptionManagerService.selectOptionGroups(storeId);
        List<SelectOptionResponseDto> options = storeOptionManagerService.selectOptions(storeId);
        String storeName = cartItems.get(0).getStoreName();

        getOptionGroupInfoAndOption(model, cartItems, optionGroupInfoAndOption);
        model.addAttribute("storeId", storeId);

        model.addAttribute("optionGroups", optionGroups);
        model.addAttribute("options", options);

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("storeName", storeName);
    }

    private void getOptionGroupInfoAndOption(Model model, List<CartRedisDto> cartItems, List<CartViewDto> optionGroupInfoAndOption) {
        for(CartRedisDto c : cartItems) {
            CartViewDto cartViewDto =
                new CartViewDto(storeMenuManagerService.selectMenu(c.getStoreId(), c.getMenu().getMenuId()).getOptionGroups() );
            optionGroupInfoAndOption.add(cartViewDto);
        }

        model.addAttribute("cartOptionGroups", optionGroupInfoAndOption);
    }

    private void cartCountInfo(Model model, Long accountId) {
        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        model.addAttribute("accountAddresses", accountAddresses);
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

        CartMenuCountDto cartMenuCountDto = cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), EMPTY_CART)) {

            model.addAttribute("count", CART_COUNT_ZERO);
        } else {

            model.addAttribute("count", cartMenuCountDto.getCount());
        }
    }

    /**
     * 장바구니에 담겨져 있는 메뉴에 옵션을 변경하는 Controller.    <br>
     * js 에서 fetch 를 사용함으로 ResponseEntity 를 사용.
     *
     * @param menuKey       redis Key
     * @param cartOptionDto 장바구니에 담길 객체
     * @return 수정이 잘 이루어지면 Ok 를 반환
     */
    @PutMapping("/modify-menu/{menuKey}")
    public ResponseEntity<Void> putCartMenuAll(@PathVariable String menuKey,
                                               @RequestBody List<CartOptionDto> cartOptionDto) {

        CartRedisDto cart = cartService.selectCartMenu(String.valueOf(accountIdAware.getAccountId()), menuKey);
        List<CartOptionDto> optionDtoList = new ArrayList<>();

        if (cartOptionDto.size() == 0) {
            return ResponseEntity.ok().build();
        } else if (cart.getOptions() == null) {
            cart.setOptions(optionDtoList);
        }

        cart.getOptions().clear();
        cart.getOptions().addAll(cartOptionDto);

        cartService.modifyCartMenu(String.valueOf(accountIdAware.getAccountId()), menuKey, cart);

        return ResponseEntity.ok().build();
    }

    /**
     * 메뉴에 대한 수량을 증가시키는 Controller.
     *
     * @param menuKey redis hashKey
     * @return 수량 변경 후 장바구니 페이지로 Redirect
     */
    @PutMapping("/menu-count-up/{menuKey}")
    public String putCartMenuIncrement(@PathVariable String menuKey) {

        cartService.modifyCartMenuIncrement(String.valueOf(accountIdAware.getAccountId()), menuKey);

        return "redirect:/carts";
    }

    /**
     * 메뉴에 대한 수량을 감소시키는 Controller.
     *
     * @param menuKey redis hashKey
     * @return 수량 변경 후 장바구니 페이지로 Redirect
     */
    @PutMapping("/menu-count-down/{menuKey}")
    public String putCartMenuDecrement(@PathVariable String menuKey) {

        cartService.modifyCartMenuDecrement(String.valueOf(accountIdAware.getAccountId()), menuKey);

        return "redirect:/carts";
    }

    /**
     * 장바구니에 담긴 해당 메뉴를 삭제하는 Controller.
     *
     * @param menuKey redis Key
     * @return 메뉴 삭제 후 장바구니 페이지로 Redirect
     */
    @DeleteMapping("/menu-delete/{menuKey}")
    public String deleteCartMenu(@PathVariable String menuKey) {

        cartService.removeCartMenu(String.valueOf(accountIdAware.getAccountId()), menuKey);

        return "redirect:/carts";
    }

    /**
     * 장바구니에 담긴 모든 메뉴를 삭제하는 Controller.
     *
     * @return 모든 메뉴 삭제 후 장바구니 페이지로 Redirect
     */
    @DeleteMapping("/menu-del-all")
    public String deleteCartMenuAll() {

        cartService.removeCartMenuAll(String.valueOf(accountIdAware.getAccountId()));

        return "redirect:/carts";
    }
}
