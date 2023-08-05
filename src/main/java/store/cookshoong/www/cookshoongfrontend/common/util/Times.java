package store.cookshoong.www.cookshoongfrontend.common.util;

/**
 * 시간을 상수로 다루기 위한 유틸클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.27
 */
public class Times {
    private Times() {}

    private static final Long SECOND = 1L;
    public static final Long MINUTE = SECOND * 60;
    public static final Long HOUR = MINUTE * 60;
    public static final Long DAY = HOUR * 24;
    public static final Long WEEK = DAY * 7;
}
