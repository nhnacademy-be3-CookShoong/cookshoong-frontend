package store.cookshoong.www.cookshoongfrontend.common.property;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Toss Api test KEY.
 *
 * @author jeongjewan
 * @since 2023.08.01
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TossSecret {

    private String clientId;
    private String secret;
}
