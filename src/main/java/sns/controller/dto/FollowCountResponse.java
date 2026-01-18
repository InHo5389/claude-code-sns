package sns.controller.dto;

import sns.domain.follow.FollowCount;

public record FollowCountResponse(
        Long userId,
        Long followersCount,
        Long followeesCount
) {
    public static FollowCountResponse from(FollowCount followCount) {
        return new FollowCountResponse(
                followCount.getUserId(),
                followCount.getFollowersCount(),
                followCount.getFolloweesCount()
        );
    }
}
