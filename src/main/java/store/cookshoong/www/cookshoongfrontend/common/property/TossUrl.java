package store.cookshoong.www.cookshoongfrontend.common.property;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Toss successUrl and failUrl.
 *
 * @author jeongjewan
 * @since 2023.08.01
 */
@Getter
@AllArgsConstructor
public class TossUrl {
    private String successUrl;
    private String failUrl;
}
