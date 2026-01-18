package sns.domain.post.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sns.domain.post.PostRedisRepository;
import sns.domain.post.PostRepository;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostViewScheduler {

    private final PostRedisRepository postRedisRepository;
    private final PostRepository postRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void syncPostViewsToDatabase() {
        Set<Long> dirtyPostIds = postRedisRepository.getDirtyPostIds();
        if (dirtyPostIds.isEmpty()) {
            return;
        }

        log.info("Syncing post views for {} posts", dirtyPostIds.size());

        dirtyPostIds.forEach(postId -> {
            try {
                Long postView = postRedisRepository.getPostView(postId);

                postRepository.findByIdAndDeletedAtIsNull(postId).ifPresent(post -> {
                    post.updateViewCount(postView);
                    postRepository.save(post);
                });


                postRedisRepository.removeDirtyPostId(postId);
            } catch (Exception e) {
                log.error("Failed to sync view count for post {}", postId, e);
            }
        });
    }

}
