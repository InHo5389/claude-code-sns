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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sns.config.AuthUser;
import sns.controller.dto.PostResponse;
import sns.controller.dto.ReplyCreateRequest;
import sns.domain.post.Post;
import sns.domain.reply.ReplyService;
import sns.domain.user.User;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/posts/{postId}/replies")
    public ResponseEntity<PostResponse> create(
            @AuthUser User user,
            @PathVariable Long postId,
            @RequestBody ReplyCreateRequest request) {
        Post reply = replyService.create(request.content(), user.getId(), postId);
        return ResponseEntity.ok(PostResponse.from(reply));
    }

    @GetMapping("/api/posts/{postId}/replies")
    public ResponseEntity<Page<PostResponse>> findByParentId(
            @PathVariable Long postId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> replies = replyService.findByParentId(postId, pageable)
                .map(PostResponse::from);
        return ResponseEntity.ok(replies);
    }

    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<Void> delete(
            @AuthUser User user,
            @PathVariable Long id) {
        replyService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
