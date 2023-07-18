package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;

/**
 * 매장 뷰 페이지 컨트롤러.
 *
 * @author papel
 * @since 2023.07.18
 */
@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreMenuManagerService storeMenuManagerService;

    /**
     * 매장 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.16
     */
    @GetMapping({"/index-store"})
    public String getIndexStore(Model model) {
        List<SelectMenuResponseDto> menus = storeMenuManagerService.selectMenus(1L);
        model.addAttribute("menus", menus);
        return "index-store";
    }
}
