package store.cookshoong.www.cookshoongfrontend.file.exception;

import store.cookshoong.www.cookshoongfrontend.common.exception.NotFoundException;

/**
 * 없는 저장소 타입 요청에 대한 예외.
 *
 * @author seungyeon
 * @since 2023.08.08
 */
public class LocationTypeNotFoundException extends NotFoundException {
    public LocationTypeNotFoundException(){
        super("저장소 타입");
    }
}
