package sns.controller.dto;

import java.time.LocalDateTime;
import sns.domain.post.Post;

public record PostResponse(
        Long id,
        String content,
        AuthorResponse author,
        Long parentId,
        Long quoteId,
        Long repostId,
        Integer repostCount,
        Integer replyCount,
        Integer likeCount,
        boolean editable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getContent(),
                AuthorResponse.from(post.getUser()),
                post.getParentId(),
                post.getQuoteId(),
                post.getRepostId(),
                post.getRepostCount(),
                post.getReplyCount(),
                post.getLikeCount(),
                post.isEditable(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public record AuthorResponse(
            Long id,
            String nickname
    ) {
        public static AuthorResponse from(sns.domain.user.User user) {
            return new AuthorResponse(user.getId(), user.getNickname());
        }
    }
}
