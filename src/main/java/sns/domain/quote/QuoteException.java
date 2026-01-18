package sns.domain.quote;

public class QuoteException extends RuntimeException {

    public QuoteException(String message) {
        super(message);
    }

    public static QuoteException notQuote(Long id) {
        return new QuoteException("해당 게시글은 인용이 아닙니다: " + id);
    }

    public static QuoteException alreadyQuoted() {
        return new QuoteException("이미 인용한 게시글입니다.");
    }
}
