package sns.domain.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(String email, String password, String nickname) {
        if (userRepository.existsByEmail(email)) {
            throw UserException.emailAlreadyExists(email);
        }

        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> UserException.notFound(id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserException.notFoundByEmail(email));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User update(Long id, String password, String nickname) {
        User user = findById(id);
        user.update(password, nickname);
        return user;
    }

    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
