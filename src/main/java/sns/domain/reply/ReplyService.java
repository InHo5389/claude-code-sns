package sns.domain.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.domain.post.Post;
import sns.domain.post.PostException;
import sns.domain.post.PostRepository;
import sns.domain.user.User;
import sns.domain.user.UserService;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public Post create(String content, Long userId, Long parentId) {
        User user = userService.findById(userId);
        postRepository.findByIdAndDeletedAtIsNull(parentId)
                .orElseThrow(() -> PostException.notFound(parentId));

        Post reply = Post.createReply(content, user, parentId);
        postRepository.incrementReplyCount(parentId);

        return postRepository.save(reply);
    }

    public Page<Post> findByParentId(Long parentId, Pageable pageable) {
        return postRepository.findRepliesByParentId(parentId, pageable);
    }

    @Transactional
    public void delete(Long replyId, Long userId) {
        Post reply = postRepository.findByIdAndDeletedAtIsNull(replyId)
                .orElseThrow(() -> PostException.notFound(replyId));

        if (!reply.isReply()) {
            throw ReplyException.notReply(replyId);
        }

        if (!reply.isOwnedBy(userId)) {
            throw PostException.notOwner();
        }

        postRepository.decrementReplyCount(reply.getParentId());
        reply.delete();
    }
}
