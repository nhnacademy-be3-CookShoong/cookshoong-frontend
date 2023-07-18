package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
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
    private final StoreOptionManagerService storeOptionManagerService;

    /**
     * 메뉴 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.16
     */
    @GetMapping({"/index-menu"})
    public String getIndexMenu(Model model) {
        List<SelectOptionResponseDto> options = storeOptionManagerService.selectOptions(1L);
        model.addAttribute("options", options);
        return "index/menu";
    }
}
