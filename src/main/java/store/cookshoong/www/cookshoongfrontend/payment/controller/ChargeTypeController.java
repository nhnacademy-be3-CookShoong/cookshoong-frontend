package store.cookshoong.www.cookshoongfrontend.payment.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreateTypeRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.ModifyTypeRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TypeResponseDto;
import store.cookshoong.www.cookshoongfrontend.payment.service.ChargeTypeService;

/**
 * 결제 타입에 대한 Controller.
 *
 * @author jeongjewan
 * @since 2023.07.08
 */
@Slf4j
@Controller
@RequestMapping("/admin/management/charges")
@RequiredArgsConstructor
public class ChargeTypeController {

    private final ChargeTypeService chargeTypeService;

    /**
     * 결제 타입 등록 화면 메서드.
     *
     * @param createTypeRequestDto  결제 타입에 생성되는 데이터
     * @param model                 HTML 로 보낼 데이터
     * @return                      등록 페이지로 반환
     */
    @GetMapping
    public String getCreateChargeType(@ModelAttribute("type") CreateTypeRequestDto createTypeRequestDto,
                                       Model model) {

        List<TypeResponseDto> chargeTypes = chargeTypeService.selectChargeTypeAll();

        model.addAttribute("chargeTypes", chargeTypes);

        model.addAttribute("url", "charge-type");

        return "payment/charge-type-form";
    }

    /**
     * 결제 타입 생성 메서드.
     *
     * @param createTypeRequestDto  결제 타입에 생성되는 데이터
     * @param bindingResult         입력에 대한 검증 결과
     * @param model                 HTML 로 보낼 데이터
     * @return                      결제 타입 페이지로 반환
     */
    @PostMapping
    public String postDoCreateChargeType(@ModelAttribute("type") @Valid CreateTypeRequestDto createTypeRequestDto,
                                       BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("url", "charge-type");
            return "payment/charge-type-form";
        }

        chargeTypeService.createChargeType(createTypeRequestDto);

        return "redirect:admin/management/charges";
    }

    /**
     * 헤당 결제 타입을 삭제하는 메서드.
     *
     * @param id        결제 타입 아이디
     * @return          모든 결제 타입에 대한 목록 화면으로 반환
     */
    @GetMapping("/{id}/delete")
    public String postDeleteChargeType(@PathVariable String id) {

        chargeTypeService.deleteChargeType(id);

        return "redirect:admin/management/charges";
    }
}
