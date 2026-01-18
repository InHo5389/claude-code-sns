package sns.domain.repost;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.domain.post.Post;
import sns.domain.post.PostException;
import sns.domain.post.PostRepository;
import sns.domain.user.User;
import sns.domain.user.UserService;

@Service
@RequiredArgsConstructor
public class RepostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public Post create(Long userId, Long repostId) {
        User user = userService.findById(userId);
        Post originalPost = postRepository.findByIdAndDeletedAtIsNull(repostId)
                .orElseThrow(() -> PostException.notFound(repostId));

        if (originalPost.isOwnedBy(userId)) {
            throw RepostException.cannotRepostOwnPost();
        }

        if (postRepository.existsByUserIdAndRepostIdAndDeletedAtIsNull(userId, repostId)) {
            throw RepostException.alreadyReposted();
        }

        Post repost = Post.createRepost(user, repostId);
        postRepository.incrementRepostCount(repostId);

        return postRepository.save(repost);
    }

    public List<Post> findByRepostId(Long repostId) {
        return postRepository.findRepostsByRepostId(repostId);
    }

    @Transactional
    public void delete(Long repostId, Long userId) {
        Post repost = postRepository.findByIdAndDeletedAtIsNull(repostId)
                .orElseThrow(() -> PostException.notFound(repostId));

        if (!repost.isRepost()) {
            throw RepostException.notRepost(repostId);
        }

        if (!repost.isOwnedBy(userId)) {
            throw PostException.notOwner();
        }

        postRepository.decrementRepostCount(repost.getRepostId());
        repost.delete();
    }
}
