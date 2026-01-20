package sns.controller.media;

public record PresignedUrlResponse(
        String presignedUrl,
        MediaResponse media
) {
}