package store.cookshoong.www.cookshoongfrontend.file.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * ObjectStorage, LocalStorage를 구분짓기 위한 Resolver.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.07
 */
@Component
@RequiredArgsConstructor
public class FileUtilResolver {
    private final List<FileUtils> fileUtilList;

    public FileUtils getFileService(String locationType){
        return fileUtilList.stream()
            .filter(fileUtils -> fileUtils.matchStorageType(locationType))
            .findAny()
            .orElse(fileUtilList.get(0));
    }
}
