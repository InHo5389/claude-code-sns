package sns.domain.media;

public record MultipartUploaded(
        int partNumber,
        String eTag
) {
}
