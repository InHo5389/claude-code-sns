package sns.domain.follow;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFolloweeIdAndDeletedAtIsNull(Long followerId, Long followeeId);

    List<Follow> findByFollowerIdAndDeletedAtIsNull(Long followerId);

    List<Follow> findByFolloweeIdAndDeletedAtIsNull(Long followeeId);

    boolean existsByFollowerIdAndFolloweeIdAndDeletedAtIsNull(Long followerId, Long followeeId);
}
