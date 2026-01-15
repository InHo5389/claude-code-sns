package sns.domain.user;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public static UserException emailAlreadyExists(String email) {
        return new UserException("이미 존재하는 이메일입니다: " + email);
    }

    public static UserException notFound(Long id) {
        return new UserException("사용자를 찾을 수 없습니다: " + id);
    }

    public static UserException notFoundByEmail(String email) {
        return new UserException("사용자를 찾을 수 없습니다: " + email);
    }
}
