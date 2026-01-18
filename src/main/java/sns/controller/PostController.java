package sns.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sns.config.AuthUser;
import sns.controller.dto.PostCreateRequest;
import sns.controller.dto.PostResponse;
import sns.controller.dto.PostUpdateRequest;
import sns.domain.post.Post;
import sns.domain.post.PostService;
import sns.domain.user.User;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseEntity<PostResponse> create(
            @AuthUser User user,
            @RequestBody PostCreateRequest request) {
        Post post = postService.create(request.content(), user.getId());
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @GetMapping("/api/posts")
    public ResponseEntity<Page<PostResponse>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> posts = postService.findAll(pageable)
                .map(PostResponse::from);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        Post post = postService.findByIdAndIncrementViewCount(id);
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @GetMapping("/api/users/{userId}/posts")
    public ResponseEntity<Page<PostResponse>> findByUserId(
            @PathVariable Long userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> posts = postService.findByUserId(userId, pageable)
                .map(PostResponse::from);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<PostResponse> update(
            @AuthUser User user,
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request) {
        Post post = postService.update(id, request.content(), user.getId());
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> delete(
            @AuthUser User user,
            @PathVariable Long id) {
        postService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/posts/{id}/view")
    public ResponseEntity<Void> incrementView(@PathVariable Long id) {
        postService.incrementPostView(id);
        return ResponseEntity.noContent().build();
    }
}
