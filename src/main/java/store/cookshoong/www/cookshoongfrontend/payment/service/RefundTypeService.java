package store.cookshoong.www.cookshoongfrontend.payment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.RefundTypeAdapter;
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
public class RefundTypeService {

    private final RefundTypeAdapter refundTypeAdapter;

    public void createChargeType(CreateTypeRequestDto createTypeRequestDto) {

        refundTypeAdapter.executeRefundType(createTypeRequestDto);
    }

    public TypeResponseDto selectChargeType(String id) {

        return refundTypeAdapter.fetchRefundType(id);
    }

    public List<TypeResponseDto> selectChargeTypeAll() {

        return refundTypeAdapter.fetchRefundTypeAll();
    }

    public void deleteChargeType(String id) {

        refundTypeAdapter.eraseRefundType(id);
    }
}
