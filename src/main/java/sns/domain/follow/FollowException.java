package sns.domain.follow;

public class FollowException extends RuntimeException {

    public FollowException(String message) {
        super(message);
    }

    public static FollowException cannotFollowSelf() {
        return new FollowException("자기 자신을 팔로우할 수 없습니다.");
    }

    public static FollowException alreadyFollowing(Long followeeId) {
        return new FollowException("이미 팔로우 중입니다: " + followeeId);
    }

    public static FollowException notFollowing(Long followeeId) {
        return new FollowException("팔로우 중이 아닙니다: " + followeeId);
    }
}
