package store.cookshoong.www.cookshoongfrontend.cart.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.cart.adapter.CartRedisAdapter;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;

/**
 * Front 에서 Redis 장바구니에 대한 Service.
 *
 * @author jeongjewan
 * @since 2023.07.25
 */
@Service
@RequiredArgsConstructor
public class CartRedisService {

    private final CartRedisAdapter cartRedisAdapter;

    /**
     * 회원이 장바구니에 메뉴를 담는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     * @param cartRedisDto  장바구니에 담은 메뉴에 대한 정보 Dto
     */
    public void createCart(String cartKey, String menuKey, CartRedisDto cartRedisDto) {

        cartRedisAdapter.executeCart(cartKey, menuKey, cartRedisDto);
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 옵션을 변경.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     * @param cartRedisDto  장바구니에 담은 메뉴에 변경된 정보 Dto
     */
    public void modifyCartMenu(String cartKey, String menuKey, CartRedisDto cartRedisDto) {

        cartRedisAdapter.changeCartMenu(cartKey, menuKey, cartRedisDto);
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 수량을 증가.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void modifyCartMenuIncrement(String cartKey, String menuKey) {

        cartRedisAdapter.changeCartMenuIncrement(cartKey, menuKey);
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 수량을 감소.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void modifyCartMenuDecrement(String cartKey, String menuKey) {

        cartRedisAdapter.changeCartMenuDecrement(cartKey, menuKey);
    }

    /**
     * 회원의 장바구니에 담긴 모든 메뉴를 조회.
     *
     * @param cartKey       redis key
     * @return              장바구니 담긴 모든 메뉴를 반환.
     */
    public List<CartRedisDto> selectCartMenuAll(String cartKey) {

        return cartRedisAdapter.fetchCartMenuAll(cartKey);
    }

    /**
     * 회원의 장바구니에 담긴 모든 메뉴를 조회.
     *
     * @param cartKey       redis key
     * @return              장바구니 담긴 모든 메뉴를 반환.
     */
    public CartRedisDto selectCartMenu(String cartKey, String menuKey) {

        return cartRedisAdapter.fetchCartMenu(cartKey, menuKey);
    }

    /**
     * 회원 장바구니에 담긴 모든 메뉴에 수량을 조회.
     *
     * @param cartKey       redis key
     * @return              장바구니 담긴 모든 메뉴 수량 반환
     */
    public Long selectCartMenuCountAll(String cartKey) {

        return cartRedisAdapter.fetchCartMenuCountAll(cartKey);
    }

    /**
     * 회원 장바구니에 담긴 해당 메뉴를 삭제.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void removeCartMenu(String cartKey, String menuKey) {

        cartRedisAdapter.eraseCartMenu(cartKey, menuKey);
    }

    /**
     * 회원이 장바구니에 담은 모든 메뉴를 삭제.
     *
     * @param cartKey       redis key
     */
    public void removeCartMenuAll(String cartKey) {

        cartRedisAdapter.eraseCartMenuAll(cartKey);
    }
}
