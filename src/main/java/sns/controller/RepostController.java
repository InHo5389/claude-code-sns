package sns.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sns.config.AuthUser;
import sns.controller.dto.PostResponse;
import sns.domain.post.Post;
import sns.domain.repost.RepostService;
import sns.domain.user.User;

@RestController
@RequiredArgsConstructor
public class RepostController {

    private final RepostService repostService;

    @PostMapping("/api/posts/{postId}/reposts")
    public ResponseEntity<PostResponse> create(
            @AuthUser User user,
            @PathVariable Long postId) {
        Post repost = repostService.create(user.getId(), postId);
        return ResponseEntity.ok(PostResponse.from(repost));
    }

    @GetMapping("/api/posts/{postId}/reposts")
    public ResponseEntity<List<PostResponse>> findByRepostId(@PathVariable Long postId) {
        List<PostResponse> reposts = repostService.findByRepostId(postId).stream()
                .map(PostResponse::from)
                .toList();
        return ResponseEntity.ok(reposts);
    }

    @DeleteMapping("/api/reposts/{id}")
    public ResponseEntity<Void> delete(
            @AuthUser User user,
            @PathVariable Long id) {
        repostService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
