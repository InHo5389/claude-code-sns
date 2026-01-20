package sns.controller.media;

import sns.domain.media.MediaType;

public record MediaInitRequest(
        MediaType mediaType,
        Long fileSize
) {
}
