package sns.controller.dto;

import sns.domain.media.MultipartUploaded;

import java.util.List;

public record MediaUploadedRequest(
        Long mediaId,
        List<MultipartUploaded> parts
) {
}