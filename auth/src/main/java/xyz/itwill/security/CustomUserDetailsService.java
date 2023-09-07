package xyz.itwill.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityUsers;
import xyz.itwill.repository.SecurityUsersRepository;

//인증 처리 후 인증된 사용자 정보와 권한을 UserDetails 객체로 반환하는 기능을 제공하는 클래스
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final SecurityUsersRepository securityUsersRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecurityUsers users = securityUsersRepository.selectSecurityUsersByUserid(username);
		if(users == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(users);
	}

}
