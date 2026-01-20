package sns.domain.media;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sns.domain.BaseEntity;

@Entity
@Table(name = "media")
@Getter
public class Media extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false, length = 20)
    private MediaType mediaType;

    @Column(name = "path", length = 2000)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MediaStatus status;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "upload_id", length = 500)
    private String uploadId;

    @Column
    private Long fileSize;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes", columnDefinition = "jsonb")
    private Map<String, Object> attributes;

    protected Media() {
    }

    @Builder
    private Media(MediaType mediaType, String path, MediaStatus status, Long userId, String uploadId, Map<String, Object> attributes) {
        this.mediaType = mediaType;
        this.path = path;
        this.status = status;
        this.userId = userId;
        this.uploadId = uploadId;
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }

    public static Media create(MediaType mediaType,String path,Long userId,Long fileSize) {
        Media media = new Media();
        media.mediaType = mediaType;
        media.path = path;
        media.status = MediaStatus.INIT;
        media.userId = userId;
        media.fileSize = fileSize;
        return media;
    }

    public static Media create(MediaType mediaType, String path, Long userId, Long fileSize, String uploadId) {
        Media media = create(mediaType, path, userId, fileSize);
        media.uploadId = uploadId;
        return media;
    }

    public void updateStatus(MediaStatus status) {
        this.status = status;
    }


    public void updateAttributes(Map<String, Object> attributes) {
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }
}
