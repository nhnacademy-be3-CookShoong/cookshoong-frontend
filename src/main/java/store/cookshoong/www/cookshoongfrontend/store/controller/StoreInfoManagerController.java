package store.cookshoong.www.cookshoongfrontend.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 매장 정보 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel
 * @since 2023.07.09
 */
@Controller
@RequiredArgsConstructor
public class StoreInfoManagerController {

    /**
     * 매장 정보 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.05
     */
    @GetMapping("/store-info-manager")
    public String getStoreInfoManger() {
        return "store/info/store-info-manager";
    }
}
