package store.cookshoong.www.cookshoongfrontend.payment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.ChargeTypeAdapter;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreateTypeRequestDto;
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
public class ChargeTypeService {

    private final ChargeTypeAdapter chargeTypeAdapter;

    public void createChargeType(CreateTypeRequestDto createTypeRequestDto) {

        chargeTypeAdapter.executeChargeType(createTypeRequestDto);
    }

    public TypeResponseDto selectChargeType(String id) {

        return chargeTypeAdapter.fetchChargeType(id);
    }

    public List<TypeResponseDto> selectChargeTypeAll() {

        return chargeTypeAdapter.fetchChargeTypeAll();
    }

    public void deleteChargeType(String id) {

        chargeTypeAdapter.eraseChargeType(id);
    }
}
