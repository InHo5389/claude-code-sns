package sns.controller.media;

import sns.domain.media.MediaStatus;

import java.util.Map;

public record MediaUpdateRequest(
        String path,
        MediaStatus status,
        Map<String, Object> attributes
) {
}