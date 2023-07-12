package store.cookshoong.www.cookshoongfrontend.store.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.service.StoreService;

/**
 * 매장의 등록 페이지에서 사용될 컨트롤러.
 *
 * @author papel
 * @since 2023.07.09
 */
@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 매장 등록 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.09
     */
    @GetMapping("/store-register")
    public String getSelectStore(CreateStoreRequestDto createStoreRequestDto, Model model) {
        model.addAttribute("createStoreRequestDto", createStoreRequestDto);
        return "store/register/store-register";
    }

    /**
     * 매장 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.09
     */
    @PostMapping("/store-register")
    public String postCreateStore(@Valid @ModelAttribute("createStoreRequestDto") CreateStoreRequestDto createStoreRequestDto,
                                  BindingResult bindingResult) {
        storeService.createStore(createStoreRequestDto);
        return "redirect:/";
    }
}
