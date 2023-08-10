package store.cookshoong.www.cookshoongfrontend.payment.util;

import lombok.Getter;

/**
 * 결제 타입에 대한 Enum.
 *
 * @author jeongjewan
 * @since 2023.08.03
 */
@Getter
public enum PaymentType {

    TOSS("TOSS"),
    PAYCO("PAYCO"),
    NPAY("NPAY");

    PaymentType(String type) {
        this.type = type;
    }

    private final String type;
}
