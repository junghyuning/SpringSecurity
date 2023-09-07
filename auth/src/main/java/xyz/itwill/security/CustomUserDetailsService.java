package xyz.itwill.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityUsers;
import xyz.itwill.repository.SecurityUsersRepository;

//���� ó�� �� ������ ����� ������ ������ UserDetails ��ü�� ��ȯ�ϴ� ����� �����ϴ� Ŭ����
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
