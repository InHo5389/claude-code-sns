package sns.controller.dto;

import jakarta.validation.constraints.NotNull;

public record FollowRequest(
        @NotNull(message = "followeeId는 필수입니다.")
        Long followeeId
) {
}
