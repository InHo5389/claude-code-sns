package sns.domain.follow;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.domain.user.User;
import sns.domain.user.UserService;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowCountService followCountService;
    private final UserService userService;

    @Transactional
    public void follow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw FollowException.cannotFollowSelf();
        }

        userService.findById(followeeId);

        if (followRepository.existsByFollowerIdAndFolloweeIdAndDeletedAtIsNull(followerId, followeeId)) {
            throw FollowException.alreadyFollowing(followeeId);
        }

        Follow follow = Follow.builder()
                .followerId(followerId)
                .followeeId(followeeId)
                .build();

        followRepository.save(follow);

        followCountService.incrementFolloweesCount(followerId);
        followCountService.incrementFollowersCount(followeeId);
    }

    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        User followee = userService.findById(followeeId);

        Follow follow = followRepository.findByFollowerIdAndFolloweeIdAndDeletedAtIsNull(followerId, followeeId)
                .orElseThrow(() -> FollowException.notFollowing(followeeId));

        follow.delete();

        followCountService.decrementFolloweesCount(followerId);
        followCountService.decrementFollowersCount(followee.getId());
    }

    public List<User> getFollowers(Long userId) {
        return followRepository.findByFolloweeIdAndDeletedAtIsNull(userId).stream()
                .map(follow -> userService.findById(follow.getFollowerId()))
                .toList();
    }

    public List<User> getFollowees(Long userId) {
        return followRepository.findByFollowerIdAndDeletedAtIsNull(userId).stream()
                .map(follow -> userService.findById(follow.getFolloweeId()))
                .toList();
    }

    public FollowCount getFollowCount(Long userId) {
        return followCountService.getFollowCount(userId);
    }

    public boolean isFollowing(Long followerId, Long followeeId) {
        return followRepository.existsByFollowerIdAndFolloweeIdAndDeletedAtIsNull(followerId, followeeId);
    }
}
