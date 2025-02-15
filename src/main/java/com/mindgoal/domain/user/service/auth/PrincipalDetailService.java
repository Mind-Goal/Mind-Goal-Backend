package com.mindgoal.domain.user.service.auth;

import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import com.mindgoal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new PrincipalDetails(userRepository.findUserByEmail(email));
    }
}
