package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;

/**
 * 메뉴 뷰 페이지 컨트롤러.
 *
 * @author papel
 * @since 2023.07.18
 */
@Controller
@RequiredArgsConstructor
public class MenuController {
    private final StoreMenuManagerService storeMenuManagerService;
    private final StoreOptionManagerService storeOptionManagerService;

    /**
     * 메뉴 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.16
     */
    @GetMapping({"/index/store/{storeId}/menu/{menuId}"})
    public String getIndexMenu(Model model, @PathVariable("storeId") Long storeId, @PathVariable("menuId") Long menuId) {
        SelectMenuResponseDto menu = storeMenuManagerService.selectMenu(storeId, menuId);
        model.addAttribute("menu", menu);
        List<SelectOptionResponseDto> options = storeOptionManagerService.selectOptions(storeId);
        model.addAttribute("options", options);

        return "index/menu";
    }
}
