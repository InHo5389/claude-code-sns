package sns.domain.post;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import sns.domain.BaseEntity;
import sns.domain.user.User;

@Entity
@Table(name = "posts")
@Getter
public class Post extends BaseEntity {

    private static final int EDIT_ALLOWED_HOURS = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "quote_id")
    private Long quoteId;

    @Column(name = "repost_id")
    private Long repostId;

    @Column(name = "repost_count", nullable = false)
    private Integer repostCount = 0;

    @Column(name = "reply_count", nullable = false)
    private Integer replyCount = 0;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;


    protected Post() {
    }

    @Builder
    private Post(String content, User user, Long parentId, Long quoteId, Long repostId, Long viewCount) {
        this.content = content;
        this.user = user;
        this.parentId = parentId;
        this.quoteId = quoteId;
        this.repostId = repostId;
        this.viewCount = viewCount;
    }

    public static Post create(String content, User user) {
        Post post = new Post();
        post.content = content;
        post.user = user;
        post.repostCount = 0;
        post.likeCount = 0;
        post.replyCount = 0;
        post.viewCount = 0L;

        return post;
    }

    public static Post createReply(String content, User user, Long parentId) {
        Post post = create(content, user);
        post.parentId = parentId;

        return post;
    }

    public static Post createQuote(String content, User user, Long quoteId) {
        Post post = create(content, user);
        post.quoteId = quoteId;

        return post;
    }

    public static Post createRepost(User user, Long repostId) {
        Post post = create("", user);
        post.repostId = repostId;

        return post;
    }

    public boolean isEditable() {
        return getCreatedAt().plusHours(EDIT_ALLOWED_HOURS).isAfter(LocalDateTime.now());
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isOwnedBy(Long userId) {
        return this.user.getId().equals(userId);
    }

    public boolean isRepost() {
        return this.repostId != null;
    }

    public boolean isReply() {
        return this.parentId != null;
    }

    public boolean isQuote() {
        return this.quoteId != null;
    }
}
