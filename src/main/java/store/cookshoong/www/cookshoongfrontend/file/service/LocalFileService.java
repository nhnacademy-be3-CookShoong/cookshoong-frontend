package store.cookshoong.www.cookshoongfrontend.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.file.model.LocationCode;

/**
 * LocalStorage  업로드, 로드, 다운로드, 수정, 삭제.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.07
 */
@Service
@RequiredArgsConstructor
public class LocalFileService implements FileUtils{
    @Override
    public String getStorageType() {
        return LocationCode.LOCAL_S.getVariable();
    }

}
