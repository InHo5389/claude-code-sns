package sns.domain.post;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedAtIsNull(Long id);

    Page<Post> findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Page<Post> findByDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.parentId = :parentId AND p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    Page<Post> findRepliesByParentId(@Param("parentId") Long parentId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.quoteId = :quoteId AND p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    Page<Post> findQuotesByQuoteId(@Param("quoteId") Long quoteId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.repostId = :repostId AND p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    List<Post> findRepostsByRepostId(@Param("repostId") Long repostId);

    boolean existsByUserIdAndRepostIdAndDeletedAtIsNull(Long userId, Long repostId);

    boolean existsByUserIdAndQuoteIdAndDeletedAtIsNull(Long userId, Long quoteId);

    @Modifying
    @Query("UPDATE Post p SET p.replyCount = p.replyCount + 1 WHERE p.id = :id")
    void incrementReplyCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.replyCount = p.replyCount - 1 WHERE p.id = :id AND p.replyCount > 0")
    void decrementReplyCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.repostCount = p.repostCount + 1 WHERE p.id = :id")
    void incrementRepostCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.repostCount = p.repostCount - 1 WHERE p.id = :id AND p.repostCount > 0")
    void decrementRepostCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :id AND p.likeCount > 0")
    void decrementLikeCount(@Param("id") Long id);
}
