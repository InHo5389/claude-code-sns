package sns.domain.quote;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sns.domain.post.Post;
import sns.domain.post.PostException;
import sns.domain.post.PostRepository;
import sns.domain.user.User;
import sns.domain.user.UserService;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public Post create(String content, Long userId, Long quoteId) {
        User user = userService.findById(userId);
        postRepository.findByIdAndDeletedAtIsNull(quoteId)
                .orElseThrow(() -> PostException.notFound(quoteId));

        if (postRepository.existsByUserIdAndQuoteIdAndDeletedAtIsNull(userId, quoteId)) {
            throw QuoteException.alreadyQuoted();
        }

        Post quote = Post.createQuote(content, user, quoteId);
        postRepository.incrementRepostCount(quoteId);

        return postRepository.save(quote);
    }

    public Page<Post> findByQuoteId(Long quoteId, Pageable pageable) {
        return postRepository.findQuotesByQuoteId(quoteId, pageable);
    }

    @Transactional
    public void delete(Long quoteId, Long userId) {
        Post quote = postRepository.findByIdAndDeletedAtIsNull(quoteId)
                .orElseThrow(() -> PostException.notFound(quoteId));

        if (!quote.isQuote()) {
            throw QuoteException.notQuote(quoteId);
        }

        if (!quote.isOwnedBy(userId)) {
            throw PostException.notOwner();
        }

        postRepository.decrementRepostCount(quote.getQuoteId());
        quote.delete();
    }
}
