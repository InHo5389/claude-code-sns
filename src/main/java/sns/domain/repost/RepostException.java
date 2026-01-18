package sns.domain.repost;

public class RepostException extends RuntimeException {

    public RepostException(String message) {
        super(message);
    }

    public static RepostException alreadyReposted() {
        return new RepostException("이미 리포스트한 게시글입니다.");
    }

    public static RepostException cannotRepostOwnPost() {
        return new RepostException("자신의 게시글은 리포스트할 수 없습니다.");
    }

    public static RepostException notRepost(Long id) {
        return new RepostException("해당 게시글은 리포스트가 아닙니다: " + id);
    }
}
