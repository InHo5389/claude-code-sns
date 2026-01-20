package sns.domain.media;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediaRepository extends JpaRepository<Media, Long> {

    Optional<Media> findByIdAndDeletedAtIsNull(Long id);

    List<Media> findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId);

    @Query(value = "SELECT * FROM media WHERE attributes -> 'parts' @> :partNumberJson\\:\\:jsonb AND deleted_at IS NULL ORDER BY created_at DESC", nativeQuery = true)
    List<Media> findByPartNumber(@Param("partNumberJson") String partNumberJson);
}