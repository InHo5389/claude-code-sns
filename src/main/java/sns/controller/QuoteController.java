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
import sns.controller.dto.QuoteCreateRequest;
import sns.domain.post.Post;
import sns.domain.quote.QuoteService;
import sns.domain.user.User;

@RestController
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping("/api/posts/{postId}/quotes")
    public ResponseEntity<PostResponse> create(
            @AuthUser User user,
            @PathVariable Long postId,
            @RequestBody QuoteCreateRequest request) {
        Post quote = quoteService.create(request.content(), user.getId(), postId);
        return ResponseEntity.ok(PostResponse.from(quote));
    }

    @GetMapping("/api/posts/{postId}/quotes")
    public ResponseEntity<Page<PostResponse>> findByQuoteId(
            @PathVariable Long postId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> quotes = quoteService.findByQuoteId(postId, pageable)
                .map(PostResponse::from);
        return ResponseEntity.ok(quotes);
    }

    @DeleteMapping("/api/quotes/{id}")
    public ResponseEntity<Void> delete(
            @AuthUser User user,
            @PathVariable Long id) {
        quoteService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
