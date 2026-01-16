package sns.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sns.config.AuthUser;
import sns.controller.dto.FollowCountResponse;
import sns.controller.dto.FollowRequest;
import sns.controller.dto.UserResponse;
import sns.domain.follow.FollowCount;
import sns.domain.follow.FollowService;
import sns.domain.user.User;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/follows")
    public ResponseEntity<Void> follow(
            @AuthUser User currentUser,
            @Valid @RequestBody FollowRequest request) {
        followService.follow(currentUser.getId(), request.followeeId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/follows")
    public ResponseEntity<Void> unfollow(
            @AuthUser User currentUser,
            @Valid @RequestBody FollowRequest request) {
        followService.unfollow(currentUser.getId(), request.followeeId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/follows/followers")
    public ResponseEntity<List<UserResponse>> getFollowers(@AuthUser User currentUser) {
        List<UserResponse> followers = followService.getFollowers(currentUser.getId()).stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/api/follows/followees")
    public ResponseEntity<List<UserResponse>> getFollowees(@AuthUser User currentUser) {
        List<UserResponse> followees = followService.getFollowees(currentUser.getId()).stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(followees);
    }

    @GetMapping("/api/follows/count")
    public ResponseEntity<FollowCountResponse> getFollowCount(@AuthUser User currentUser) {
        FollowCount followCount = followService.getFollowCount(currentUser.getId());
        return ResponseEntity.ok(FollowCountResponse.from(followCount));
    }

    @PostMapping("/api/follows/check")
    public ResponseEntity<Boolean> isFollowing(
            @AuthUser User currentUser,
            @Valid @RequestBody FollowRequest request) {
        boolean isFollowing = followService.isFollowing(currentUser.getId(), request.followeeId());
        return ResponseEntity.ok(isFollowing);
    }
}
