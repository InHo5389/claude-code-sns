package sns.controller.media;

import sns.domain.media.MediaType;

import java.util.Map;

public record MediaCreateRequest(
        MediaType mediaType,
        String path,
        Long postId,
        Map<String, Object> attributes
) {
}