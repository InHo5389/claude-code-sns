package sns.domain.reply;

public class ReplyException extends RuntimeException {

    public ReplyException(String message) {
        super(message);
    }

    public static ReplyException notReply(Long id) {
        return new ReplyException("해당 게시글은 답글이 아닙니다: " + id);
    }
}
