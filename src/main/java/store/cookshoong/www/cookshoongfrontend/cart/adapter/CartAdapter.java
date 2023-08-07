package store.cookshoong.www.cookshoongfrontend.cart.adapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;

/**
 * Back API, Redis 장바구니에 전달하는 Adapter.
 *
 * @author jeongjewan
 * @since 2023.07.25
 */
@Component
@RequiredArgsConstructor
public class CartAdapter {

    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 회원이 장바구니를 담고, Redis 장바구니에 저장하는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     * @param cartRedisDto  저장될 객체
     */
    public void executeCart(String cartKey, String menuKey, CartRedisDto cartRedisDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("add-menu")
            .pathSegment("{menuKey}")
            .buildAndExpand(cartKey, menuKey)
            .toUri();

        HttpEntity<CartRedisDto> httpEntity =
            new HttpEntity<>(cartRedisDto);

        restTemplate.exchange(uri, POST, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 대한 옵션을 변경하는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     * @param cartRedisDto  변경될 객체
     */
    public void changeCartMenu(String cartKey, String menuKey, CartRedisDto cartRedisDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("modify-menu")
            .pathSegment("{menuKey}")
            .buildAndExpand(cartKey, menuKey)
            .toUri();

        HttpEntity<CartRedisDto> httpEntity =
            new HttpEntity<>(cartRedisDto);

        restTemplate.exchange(uri, PUT, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 대해 수량을 증가시키는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void changeCartMenuIncrement(String cartKey, String menuKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("menu-count-up")
            .pathSegment("{menuKey}")
            .buildAndExpand(cartKey, menuKey)
            .toUri();

        restTemplate.exchange(uri, PUT, null, new ParameterizedTypeReference<>() {});
    }

    /**
     * 회원이 장바구니에 담은 메뉴에 대해 수량을 감소시키는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void changeCartMenuDecrement(String cartKey, String menuKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("menu-count-down")
            .pathSegment("{menuKey}")
            .buildAndExpand(cartKey, menuKey)
            .toUri();

        restTemplate.exchange(uri, PUT, null, new ParameterizedTypeReference<>() {});
    }

    /**
     * 회원이 장바구니에 담은 모든 메뉴를 조회하는 메서드.
     *
     * @param cartKey       redis key
     * @return              장바구니에 담은 메뉴들을 반환
     */
    public List<CartRedisDto> fetchCartMenuAll(String cartKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .buildAndExpand(cartKey)
            .toUri();

        ResponseEntity<List<CartRedisDto>> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 회원이 장바구니에 담은 메뉴를 조회하는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     * @return              장바구니에 담은 메뉴를 반환
     */
    public CartRedisDto fetchCartMenu(String cartKey, String menuKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("menu")
            .pathSegment("{menuKey}")
            .buildAndExpand(cartKey, menuKey)
            .toUri();

        ResponseEntity<CartRedisDto> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 회원이 장바구니에 담은 모든 메뉴에 대한 수량을 조회하는 메서드.
     *
     * @param cartKey       redis key
     * @return              장바구니에 담긴 모든 메뉴 수량을 반환
     */
    public CartMenuCountDto fetchCartMenuCountAll(String cartKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("counts")
            .buildAndExpand(cartKey)
            .toUri();

        ResponseEntity<CartMenuCountDto> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 회원이 장바구니에 담은 해당 메뉴를 삭제하는 메서드.
     *
     * @param cartKey       redis key
     * @param menuKey       redis hashKey
     */
    public void eraseCartMenu(String cartKey, String menuKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("menu-delete")
            .pathSegment("{menuKey}")
            .buildAndExpand(cartKey, menuKey)
            .toUri();

        restTemplate.exchange(uri, DELETE, null, new ParameterizedTypeReference<>() {});
    }

    /**
     * 회원이 장바구니에 담은 모든 메뉴를 삭제.
     *
     * @param cartKey       redis key
     */
    public void eraseCartMenuAll(String cartKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("{cartKey}")
            .pathSegment("delete-all")
            .buildAndExpand(cartKey)
            .toUri();

        restTemplate.exchange(uri, DELETE, null, new ParameterizedTypeReference<>() {});
    }

    /**
     * Redis 장바구니에 회원에 대한 장바구니에 hashKey 가 존재하는지 확인.
     *
     * @param redisKey         redis Key
     * @return                  DB 장바구니 존재여부 반환
     */
    public boolean existMenuInCartRedis(String redisKey, String menuKey) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("carts")
            .pathSegment("redis")
            .pathSegment("{cartKey}")
            .pathSegment("exist")
            .pathSegment("{menuKey}")
            .pathSegment("menu")
            .buildAndExpand(redisKey, menuKey)
            .toUri();

        ResponseEntity<Boolean> exchange = restTemplate.exchange(
            uri, GET, null, new ParameterizedTypeReference<>() {});

        return Boolean.TRUE.equals(exchange.getBody());
    }
}
