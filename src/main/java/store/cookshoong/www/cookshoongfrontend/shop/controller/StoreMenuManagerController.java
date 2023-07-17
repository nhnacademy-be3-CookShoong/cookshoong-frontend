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
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;

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
    private final StoreOptionManagerService storeOptionManagerService;

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
     * 매장 옵션 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/store-option-manager")
    public String getSelectStoreMenuRegister(
        @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        Model model) {
        model.addAttribute("createOptionRequestDto", createOptionRequestDto);
        model.addAttribute("createOptionGroupRequestDto", createOptionGroupRequestDto);
        return "store/menu/store-option-manager";
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


    /**
     * 매장 옵션 그룹 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/store-option-group-manager")
    public String postCreateOptionGroup(
        @Valid @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        BindingResult bindingResult) {
        storeOptionManagerService.createOptionGroup(1L, createOptionGroupRequestDto);
        return "redirect:/";
    }

    /**
     * 매장 옵션 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/store-option-manager")
    public String postCreateOption(
        @Valid @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        BindingResult bindingResult) {
        storeOptionManagerService.createOption(1L, 1L, createOptionRequestDto);
        return "redirect:/";
    }
}
