package sns.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.domain.user.User;
import sns.domain.user.UserService;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostRedisRepository postRedisRepository;
    private final UserService userService;

    public Post create(String content, Long userId) {
        User user = userService.findById(userId);
        Post post = Post.create(content, user);
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> PostException.notFound(id));
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findByDeletedAtIsNullOrderByCreatedAtDesc(pageable);
    }

    public Page<Post> findByUserId(Long userId, Pageable pageable) {
        return postRepository.findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(userId, pageable);
    }

    @Transactional
    public Post findByIdAndIncrementViewCount(Long id) {
        Post post = findById(id);
        postRepository.incrementViewCount(id);
        return post;
    }

    @Transactional
    public Post update(Long postId, String content, Long userId) {
        Post post = findById(postId);

        if (!post.isOwnedBy(userId)) {
            throw PostException.notOwner();
        }

        if (post.isRepost()) {
            throw PostException.cannotEditRepost();
        }

        if (!post.isEditable()) {
            throw PostException.editTimeExpired();
        }

        post.updateContent(content);
        return post;
    }

    @Transactional
    public void delete(Long postId, Long userId) {
        Post post = findById(postId);

        if (!post.isOwnedBy(userId)) {
            throw PostException.notOwner();
        }

        post.delete();
    }

    public void incrementPostView(Long postId) {
        postRedisRepository.incrementPostView(postId);
    }

    public Long getPostView(Long postId) {
        return postRedisRepository.getPostView(postId);
    }
}
