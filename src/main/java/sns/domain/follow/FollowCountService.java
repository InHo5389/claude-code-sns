package sns.domain.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowCountService {

    private final FollowCountRepository followCountRepository;

    public FollowCount getFollowCount(Long userId) {
        return followCountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("FollowCount not found for user"));
    }

    public FollowCount getFollowCountOrDefault(Long userId) {
        return followCountRepository.findByUserId(userId)
                .orElse(FollowCount.createDefault(userId));
    }

    @Transactional
    @Retryable(DataIntegrityViolationException.class)
    public void incrementFolloweesCount(Long userId) {
        int updated = followCountRepository.incrementFolloweesCount(userId);
        if (updated == 0) {
            followCountRepository.save(FollowCount.create(userId)); // userId 중복일 경우 DataIntegrityViolationException
            followCountRepository.incrementFolloweesCount(userId);
        }
    }

    @Transactional
    public void decrementFolloweesCount(Long userId) {
        followCountRepository.decrementFolloweesCount(userId);
    }

    @Transactional
    @Retryable(DataIntegrityViolationException.class)
    public void incrementFollowersCount(Long userId) {
        int updated = followCountRepository.incrementFollowersCount(userId);
        if (updated == 0) {
            followCountRepository.save(FollowCount.create(userId));
            followCountRepository.incrementFollowersCount(userId);
        }
    }

    @Transactional
    public void decrementFollowersCount(Long userId) {
        followCountRepository.decrementFollowersCount(userId);
    }
}
