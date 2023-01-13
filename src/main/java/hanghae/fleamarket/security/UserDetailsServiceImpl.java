package hanghae.fleamarket.security;

import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//  username/password 인증방식을 사용할 때 사용자를 조회하고 검증한 후 UserDetails를 반환한다.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("UserDetailsServiceImpl.loadUserByUsername : " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        //[ROLE_USER]인지 [ROLE_ADMIN]인지 반환
        return new UserDetailsImpl(user, user.getUsername());
    }

}