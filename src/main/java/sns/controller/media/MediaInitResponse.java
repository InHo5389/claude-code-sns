package sns.controller.media;

import java.time.LocalDateTime;
import java.util.List;

import sns.domain.media.*;

public record MediaInitResponse(
        Long id,
        MediaType mediaType,
        String path,
        MediaStatus status,
        Long userId,
        String presignedUrl,
        String uploadId,
        List<PresignedUrlPart> presignedUrlParts,
        LocalDateTime createdAt
) {
    public static MediaInitResponse from(PresignedUrl presignedUrl) {
        Media media = presignedUrl.media();

        return new MediaInitResponse(
                media.getId(),
                media.getMediaType(),
                media.getPath(),
                media.getStatus(),
                media.getUserId(),
                presignedUrl.presignedUrl(),
                presignedUrl.uploadId(),
                presignedUrl.presignedUrlParts(),
                media.getCreatedAt()
        );
    }
}
