package store.cookshoong.www.cookshoongfrontend.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 각 메뉴 정보 관리 페이지를 연결하는 컨트롤러.
 *
 * @author papel
 * @since 2023.07.10
 */
@Controller
@RequiredArgsConstructor
public class StoreMenuManagerController {

    /**
     * 매장 메뉴 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @GetMapping("/store-menu-manager")
    public String getStoreMenuManger() {
        return "store/menu/store-menu-manager";
    }
}
