package store.cookshoong.www.cookshoongfrontend.file.service;

/**
 * 파일을 다루기 위한 인터페이스.
 * LocalFileService 는 로컬 저장소,
 * ObjectStorageService 는 오브젝트 스토리지를 사용합니다.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.07
 */
public interface FileUtils {
    String getStorageType();
    default boolean matchStorageType(String locationType) {
        return locationType.equals(getStorageType());
    }

    String getImageSrc(String savedPath);
}
