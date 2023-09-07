package xyz.itwill.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUsers;
import xyz.itwill.repository.SecurityUsersRepository;

@Service
@RequiredArgsConstructor
public class SecurityUsersSeviceImpl implements SecurityUsersService{
	private final SecurityUsersRepository securityUsersRepository;
	
	@Override
	public void addSecurityUsers(SecurityUsers users) {
		securityUsersRepository.insertSecurityUsers(users);
	}

	@Override
	public void addSecurityAuth(SecurityAuth auth) {
		// TODO Auto-generated method stub
		securityUsersRepository.insertSecurityAuth(auth);		
	}

	@Override
	public SecurityUsers getSecurityUsers(String userid) {
		// TODO Auto-generated method stub
		return securityUsersRepository.selectSecurityUsersByUserid(userid);		
	}

}
