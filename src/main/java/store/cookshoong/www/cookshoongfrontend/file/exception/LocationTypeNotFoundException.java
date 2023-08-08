package store.cookshoong.www.cookshoongfrontend.file.exception;

import store.cookshoong.www.cookshoongfrontend.common.exception.NotFoundException;

/**
 * {설명을 작성해주세요}.
 *
 * @author seungyeon
 * @since 2023.08.08
 */
public class LocationTypeNotFoundException extends NotFoundException {
    public LocationTypeNotFoundException(String message){
        super("저장소 타입");
    }
}
