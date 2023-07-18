package store.cookshoong.www.cookshoongfrontend.shop.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;

/**
 * 매장 메뉴 관리 페이지 컨트롤러.
 *
 * @author papel
 * @since 2023.07.11
 */
@Controller
@RequiredArgsConstructor
public class StoreMenuManagerController {

    private final StoreMenuManagerService storeMenuManagerService;

    /**
     * 매장 메뉴 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/store-menu-manager")
    public String getSelectStoreMenuManager(
        @ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto,
        @ModelAttribute("createMenuGroupRequestDto") CreateMenuGroupRequestDto createMenuGroupRequestDto,
        Model model) {
        model.addAttribute("createMenuRequestDto", createMenuRequestDto);
        model.addAttribute("createMenuGroupRequestDto", createMenuGroupRequestDto);
        return "store/menu/store-menu-manager";
    }

    /**
     * 매장 메뉴 그룹 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/store-menu-group-manager")
    public String postCreateMenuGroup(
        @Valid @ModelAttribute("createMenuGroupRequestDto") CreateMenuGroupRequestDto createMenuGroupRequestDto,
        BindingResult bindingResult) {
        storeMenuManagerService.createMenuGroup(1L, createMenuGroupRequestDto);
        return "redirect:/";
    }

    /**
     * 매장 메뉴 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/store-menu-manager")
    public String postCreateMenu(
        @Valid @ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto,
        BindingResult bindingResult) {
        storeMenuManagerService.createMenu(1L, createMenuRequestDto);
        return "redirect:/";
    }
}
