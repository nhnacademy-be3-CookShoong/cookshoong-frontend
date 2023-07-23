package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;

/**
 * 매장 메뉴 관리 페이지 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Controller
@RequiredArgsConstructor
public class StoreMenuManagerController {

    private final StoreMenuManagerService storeMenuManagerService;

    /**
     * 매장 메뉴 관리 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/stores/{storeId}/store-menu-manager")
    public String getSelectStoreMenuManager(
        @ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto,
        @ModelAttribute("createMenuGroupRequestDto") CreateMenuGroupRequestDto createMenuGroupRequestDto,
        @PathVariable("storeId") Long storeId,
        Model model) {

        List<SelectMenuResponseDto> menus = storeMenuManagerService.selectMenus(storeId);
        model.addAttribute("menus", menus);
        List<SelectMenuGroupResponseDto> menuGroups = storeMenuManagerService.selectMenuGroups(storeId);
        model.addAttribute("menuGroups", menuGroups);

        model.addAttribute("createMenuRequestDto", createMenuRequestDto);
        model.addAttribute("createMenuGroupRequestDto", createMenuGroupRequestDto);
        return "store/menu/store-menu-manager";
    }

    /**
     * 매장 메뉴 그룹 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-menu-group-manager")
    public String postCreateMenuGroup(
        @Valid @ModelAttribute("createMenuGroupRequestDto") CreateMenuGroupRequestDto createMenuGroupRequestDto,
        @PathVariable("storeId") Long storeId,
        BindingResult bindingResult) {
        storeMenuManagerService.createMenuGroup(storeId, createMenuGroupRequestDto);
        return "redirect:/store-menu-manager";
    }

    /**
     * 매장 메뉴 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-menu-manager")
    public String postCreateMenu(
        @Valid @ModelAttribute("createMenuRequestDto") CreateMenuRequestDto createMenuRequestDto,
        @PathVariable("storeId") Long storeId,
        BindingResult bindingResult,
        @RequestPart("menuImage") MultipartFile menuImage) {
        storeMenuManagerService.createMenu(storeId, createMenuRequestDto, menuImage);
        return "redirect:/store-menu-manager";
    }
}
