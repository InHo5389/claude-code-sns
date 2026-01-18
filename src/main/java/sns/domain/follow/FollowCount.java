package sns.domain.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow_counts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "followers_count", nullable = false)
    private Long followersCount;

    @Column(name = "followees_count", nullable = false)
    private Long followeesCount;

    public static FollowCount create(Long userId) {
        FollowCount followCount = new FollowCount();
        followCount.userId = userId;
        followCount.followersCount = 0L;
        followCount.followeesCount = 0L;
        return followCount;
    }

    public static FollowCount createDefault(Long userId) {
        return create(userId);
    }
}
