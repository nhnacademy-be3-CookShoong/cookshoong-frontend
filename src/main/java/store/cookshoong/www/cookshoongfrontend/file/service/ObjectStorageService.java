package store.cookshoong.www.cookshoongfrontend.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.file.model.LocationCode;

/**
 * LocalStorage 에 업로드, 다운로드, 수정, 삭제.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.07
 */
@Service
@RequiredArgsConstructor
public class ObjectStorageService implements FileUtils{
    @Override
    public String getStorageType() {
        return LocationCode.OBJECT_S.getVariable();
    }

}
