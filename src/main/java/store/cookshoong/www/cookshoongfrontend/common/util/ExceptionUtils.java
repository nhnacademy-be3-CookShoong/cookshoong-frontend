package store.cookshoong.www.cookshoongfrontend.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 예외로부터의 정보를 다룰 떄 사용하는 유틸 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.24
 */
public class ExceptionUtils {
    private ExceptionUtils() {}

    /**
     * 예외로부터 스택 트레이스를 추출해내는 메서드
     *
     * @param throwable the throwable
     * @return the stack trace
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
