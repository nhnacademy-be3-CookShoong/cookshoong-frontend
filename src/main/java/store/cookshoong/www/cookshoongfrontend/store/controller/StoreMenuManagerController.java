package store.cookshoong.www.cookshoongfrontend.store.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.service.StoreMenuManagerService;

/**
 * 매장 메뉴 관리 페이지에서 사용 컨트롤러.
 *
 * @author papel
 * @since 2023.07.11
 */
@Controller
@RequiredArgsConstructor
public class StoreMenuManagerController {

    private final StoreMenuManagerService storeMenuManagerService;

    /**
     * 매장 메뉴 등록 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/store-menu-register")
    public String getSelectStoreMenuRegister(@ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto, Model model) {
        model.addAttribute("createMenuRequestDto", createMenuRequestDto);
        return "store/menu/store-menu-register";
    }

    /**
     * 매장 메뉴 추가 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @PostMapping("/store-menu-register")
    public String postCreateMenu(@Valid @ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto,
                                         BindingResult bindingResult) {
        storeMenuManagerService.createMenu(createMenuRequestDto);
        return "redirect:/";
    }

    /**
     * 매장 메뉴 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/store-menu-manager")
    public String getSelectStoreMenuManager(@ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto, Model model) {
        model.addAttribute("createMenuRequestDto", createMenuRequestDto);
        return "store/menu/store-menu-manager";
    }

    /**
     * 매장 메뉴 삭제 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @DeleteMapping("/store-menu-manager")
    public String deleteMenu() {
        return "redirect:/";
    }
}
