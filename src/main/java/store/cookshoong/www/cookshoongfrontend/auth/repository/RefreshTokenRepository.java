package store.cookshoong.www.cookshoongfrontend.auth.repository;

import org.springframework.data.repository.CrudRepository;
import store.cookshoong.www.cookshoongfrontend.auth.entity.RefreshToken;

/**
 * Refresh 토큰을 레디스로부터 가져오기위한 Repository.
 *
 * @author koesnam (추만석)
 * @since 2023.07.17
 */
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
