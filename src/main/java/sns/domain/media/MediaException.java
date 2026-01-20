package sns.domain.media;

public class MediaException extends RuntimeException {

    public MediaException(String message) {
        super(message);
    }

    public static MediaException notFound(Long id) {
        return new MediaException("미디어를 찾을 수 없습니다: " + id);
    }

    public static MediaException notFoundByUploadId(String uploadId) {
        return new MediaException("업로드 ID로 미디어를 찾을 수 없습니다: " + uploadId);
    }

    public static MediaException notOwner() {
        return new MediaException("미디어 소유자만 수정/삭제할 수 있습니다.");
    }

    public static MediaException invalidStatus(MediaStatus current, MediaStatus expected) {
        return new MediaException("잘못된 상태입니다. 현재: " + current + ", 예상: " + expected);
    }

    public static MediaException alreadyCompleted() {
        return new MediaException("이미 완료된 미디어입니다.");
    }
}
