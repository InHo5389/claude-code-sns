package sns.domain.post;

public class PostException extends RuntimeException {

    public PostException(String message) {
        super(message);
    }

    public static PostException notFound(Long id) {
        return new PostException("게시글을 찾을 수 없습니다: " + id);
    }

    public static PostException notOwner() {
        return new PostException("게시글 작성자만 수정/삭제할 수 있습니다.");
    }

    public static PostException editTimeExpired() {
        return new PostException("게시글 수정 가능 시간(1시간)이 지났습니다.");
    }

    public static PostException cannotEditRepost() {
        return new PostException("리포스트는 수정할 수 없습니다.");
    }
}
