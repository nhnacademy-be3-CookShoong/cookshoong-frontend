package store.cookshoong.www.cookshoongfrontend.cart.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.cart.adapter.CartAdapter;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;

/**
 * Front 에서 Redis 장바구니에 대한 Service.
 *
 * @author jeongjewan
 * @since 2023.07.25
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartAdapter cartAdapter;

    /**
     * 회원이 장바구니에 메뉴를 담는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     * @param cartRedisDto  장바구니에 담은 메뉴에 대한 정보 Dto
     */
    public void createCart(String cartKey, String menuKey, CartRedisDto cartRedisDto) {

        cartAdapter.executeCart(cartKey, menuKey, cartRedisDto);
    }

    /**
     * DB 장바구니 정보를 Redis 에 저장하는 메서드.
     *
     * @param accountId      회원 아이디
     */
    public void createDbUploadRedis(String cartKey, Long accountId) {

        cartAdapter.executeDbUploadRedis(cartKey, accountId);
    }

    /**
     * 빈 장바구니를 만드는 메서드.
     *
     * @param cartKey       redis key
     * @param noKey         redis hashKey
     */
    public void createEmptyCart(String cartKey, String noKey) {

        cartAdapter.executeEmptyCart(cartKey, noKey);
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 옵션을 변경.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     * @param cartRedisDto  장바구니에 담은 메뉴에 변경된 정보 Dto
     */
    public void modifyCartMenu(String cartKey, String menuKey, CartRedisDto cartRedisDto) {

        cartAdapter.changeCartMenu(cartKey, menuKey, cartRedisDto);
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 수량을 증가.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void modifyCartMenuIncrement(String cartKey, String menuKey) {

        cartAdapter.changeCartMenuIncrement(cartKey, menuKey);
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 수량을 감소.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void modifyCartMenuDecrement(String cartKey, String menuKey) {

        cartAdapter.changeCartMenuDecrement(cartKey, menuKey);
    }

    /**
     * 회원의 장바구니에 담긴 모든 메뉴를 조회.
     *
     * @param cartKey       redis key
     * @return              장바구니 담긴 모든 메뉴를 반환.
     */
    public List<CartRedisDto> selectCartMenuAll(String cartKey) {

        return cartAdapter.fetchCartMenuAll(cartKey);
    }

    /**
     * 회원의 장바구니에 담긴 모든 메뉴를 조회.
     *
     * @param cartKey       redis key
     * @return              장바구니 담긴 모든 메뉴를 반환.
     */
    public CartRedisDto selectCartMenu(String cartKey, String menuKey) {

        return cartAdapter.fetchCartMenu(cartKey, menuKey);
    }

    /**
     * 회원 장바구니에 담긴 모든 메뉴에 수량을 조회.
     *
     * @param cartKey       redis key
     * @return              장바구니 담긴 모든 메뉴 수량 반환
     */
    public Long selectCartMenuCountAll(String cartKey) {

        return cartAdapter.fetchCartMenuCountAll(cartKey);
    }

    /**
     * 회원 장바구니에 담긴 해당 메뉴를 삭제.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void removeCartMenu(String cartKey, String menuKey) {

        cartAdapter.eraseCartMenu(cartKey, menuKey);
    }

    /**
     * 회원이 장바구니에 담은 모든 메뉴를 삭제.
     *
     * @param cartKey       redis key
     */
    public void removeCartMenuAll(String cartKey) {

        cartAdapter.eraseCartMenuAll(cartKey);
    }

    /**
     * Redis 장바구니에 redis Key 존재여부 확인하는 메소드.
     *
     * @param cartKey       redis Key
     * @return              존재여부를 반환
     */
    public boolean existKeyInCartRedis(String cartKey) {

        return cartAdapter.existKeyInCartRedis(cartKey);
    }

    /**
     * Redis 장바구니에 redis Key 에 hashKey 존재여부 확인하는 메소드.
     *
     * @param cartKey       redis Key
     * @return              존재여부를 반환
     */
    public boolean existMenuInCartRedis(String cartKey, String menuKey) {

        return cartAdapter.existMenuInCartRedis(cartKey, menuKey);
    }

    /**
     * DB 장바구니에 회원 장바구니 존재여부 확인하는 메소드.
     *
     * @param accountId     회원아이디
     * @return              존재여부를 반환
     */
    public boolean hashDbCart(Long accountId) {

        return cartAdapter.hasDbCart(accountId);
    }

    /**
     * 장바구니에 들어있는 메뉴에 총 가격을 계산하는 메서드.
     *
     * @param cartItems     장바구니 내역들
     * @return              메뉴에 총 가격을 반환
     */
    public Integer calculateTotalPrice(List<CartRedisDto> cartItems) {
        int totalPrice = 0;
        for (CartRedisDto item : cartItems) {
            int menuPrice = Integer.parseInt(item.getTotalMenuPrice());
            totalPrice += menuPrice;
        }
        return totalPrice;
    }
}
