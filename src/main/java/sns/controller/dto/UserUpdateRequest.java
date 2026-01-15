package sns.controller.dto;

import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
        String password,

        @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다")
        String nickname
) {
}
