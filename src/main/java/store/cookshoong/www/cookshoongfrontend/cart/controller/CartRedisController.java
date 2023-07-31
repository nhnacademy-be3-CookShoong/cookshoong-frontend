package store.cookshoong.www.cookshoongfrontend.cart.controller;

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
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartOptionDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;

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

    private final CartService cartService;
    private final StoreOptionManagerService storeOptionManagerService;
    private final AccountIdAware account;
    private static final String CART = "cartKey=";

    /**
     *  회원 장바구니에 담겨있는 메뉴를 보여주는 Controller.
     *
     * @param model     HTML 로 보낼 데이터
     * @return          회원이 담은 장바구니 페이지로 반환
     */
    @GetMapping
    public String getCartMenuAll(Model model) {

        List<CartRedisDto> cartItems = cartService.selectCartMenuAll(CART + account.getAccountId());
        List<SelectOptionResponseDto> optionsInfo = storeOptionManagerService.selectOptions(1L);


        model.addAttribute("cartItems", cartItems);
        model.addAttribute("optionsInfo", optionsInfo);

        return "cart/cart-list";
    }

    /**
     * 장바구니에 담겨져 있는 메뉴에 옵션을 변경하는 Controller.    <br>
     * js 에서 fetch 를 사용함으로 ResponseEntity 를 사용.
     *
     * @param menuKey       redis Key
     * @param cartOptionDto 장바구니에 담길 객체
     * @return              수정이 잘 이루어지면 Ok 를 반환
     */
    @PutMapping("/modify-menu/{menuKey}")
    public ResponseEntity<Void> putCartMenuAll(@PathVariable String menuKey,
                                                @RequestBody List<CartOptionDto> cartOptionDto) {

        CartRedisDto cart = cartService.selectCartMenu(CART + account.getAccountId(), menuKey);

        cart.getOptions().clear();

        cart.getOptions().addAll(cartOptionDto);
        cartService.modifyCartMenu(CART + account.getAccountId(), menuKey, cart);

        return ResponseEntity.ok().build();
    }

    /**
     * 메뉴에 대한 수량을 증가시키는 Controller.
     *
     * @param menuKey       redis hashKey
     * @return              수량 변경 후 장바구니 페이지로 Redirect
     */
    @PutMapping("/menu-count-up/{menuKey}")
    public String putCartMenuIncrement(@PathVariable String menuKey) {

        cartService.modifyCartMenuIncrement(CART + account.getAccountId(), menuKey);

        return "redirect:/carts";
    }

    /**
     * 메뉴에 대한 수량을 감소시키는 Controller.
     *
     * @param menuKey       redis hashKey
     * @return              수량 변경 후 장바구니 페이지로 Redirect
     */
    @PutMapping("/menu-count-down/{menuKey}")
    public String putCartMenuDecrement(@PathVariable String menuKey) {

        cartService.modifyCartMenuDecrement(CART + account.getAccountId(), menuKey);

        return "redirect:/carts";
    }

    /**
     * 장바구니에 담긴 해당 메뉴를 삭제하는 Controller.
     *
     * @param menuKey       redis Key
     * @return              메뉴 삭제 후 장바구니 페이지로 Redirect
     */
    @DeleteMapping("/menu-delete/{menuKey}")
    public String deleteCartMenu(@PathVariable String menuKey) {

        cartService.removeCartMenu(CART + account.getAccountId(), menuKey);

        return "redirect:/carts";
    }

    /**
     * 장바구니에 담긴 모든 메뉴를 삭제하는 Controller.
     *
     * @return              모든 메뉴 삭제 후 장바구니 페이지로 Redirect
     */
    @DeleteMapping("/menu-del-all")
    public String deleteCartMenuAll() {

        cartService.removeCartMenuAll(CART + account.getAccountId());

        return "redirect:/carts";
    }
}
