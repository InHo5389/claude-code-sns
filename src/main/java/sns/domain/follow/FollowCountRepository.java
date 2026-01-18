package sns.domain.follow;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowCountRepository extends JpaRepository<FollowCount, Long> {

    Optional<FollowCount> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followeesCount = fc.followeesCount + 1 WHERE fc.userId = :userId")
    int incrementFolloweesCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followeesCount = fc.followeesCount - 1 WHERE fc.userId = :userId AND fc.followeesCount > 0")
    int decrementFolloweesCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followersCount = fc.followersCount + 1 WHERE fc.userId = :userId")
    int incrementFollowersCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE FollowCount fc SET fc.followersCount = fc.followersCount - 1 WHERE fc.userId = :userId AND fc.followersCount > 0")
    int decrementFollowersCount(@Param("userId") Long userId);
}
