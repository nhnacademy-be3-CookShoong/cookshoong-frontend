package store.cookshoong.www.cookshoongfrontend.payment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.ChargeTypeAdapter;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.RefundTypeAdapter;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreateTypeRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.ModifyTypeRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TypeResponseDto;

/**
 * 결제 타입에 대한 Service.
 *
 * @author jeongjewan
 * @since 2023.07.08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefundTypeService {

    private final RefundTypeAdapter refundTypeAdapter;

    public void createChargeType(CreateTypeRequestDto createTypeRequestDto) {

        refundTypeAdapter.createChargeType(createTypeRequestDto);
    }

    public TypeResponseDto selectChargeType(Long id) {

        return refundTypeAdapter.selectChargeType(id);
    }

    public List<TypeResponseDto> selectChargeTypeAll() {

        return refundTypeAdapter.selectChargeTypeAll();
    }

    public void modifyChargeType(Long id, ModifyTypeRequestDto modifyTypeRequestDto) {

        refundTypeAdapter.modifyChargeType(id, modifyTypeRequestDto);
    }

    public void deleteChargeType(Long id) {

        refundTypeAdapter.deleteChargeType(id);
    }
}
