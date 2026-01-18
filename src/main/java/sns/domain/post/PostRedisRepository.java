package sns.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String POST_VIEW_KEY_PREFIX = "post:view:";
    private static final String DIRTY_SET_KEY = "post_view_dirty_set";

    public void incrementPostView(Long postId) {
        String key = POST_VIEW_KEY_PREFIX + postId;
        redisTemplate.opsForValue().increment(key);
        redisTemplate.opsForSet().add(DIRTY_SET_KEY,postId.toString());
    }

    public Long getPostView(Long postId) {
        String key = POST_VIEW_KEY_PREFIX + postId;
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : 0L;
    }

    public Set<Long> getDirtyPostIds() {
        Set<String> dirtyPostIdStrings = redisTemplate.opsForSet().members(DIRTY_SET_KEY);
        if (dirtyPostIdStrings == null || dirtyPostIdStrings.isEmpty()) {
            return Set.of();
        }
        return dirtyPostIdStrings.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }

    public void removeDirtyPostId(Long postId) {
        redisTemplate.opsForSet().remove(DIRTY_SET_KEY, postId.toString());
    }

}
