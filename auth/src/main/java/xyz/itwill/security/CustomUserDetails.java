package xyz.itwill.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUsers;

//인증된 사용자 정보를 저장하기위한 클래스
// => UserDetails 인터페이스를 상속받아 작성
@Data
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String passwd;
	private String name;
	private String email;
	private String enabled;
	private List<GrantedAuthority> securityAuthList;
	
	private  SecurityUsers users;
	
	
	public CustomUserDetails(SecurityUsers users) {
		this.userid = users.getUserid();
		this.passwd = users.getPasswd();
		this.name = users.getName();
		this.email = users.getEmail();
		this.enabled = users.getEnabled();
		
		this.securityAuthList = new ArrayList<GrantedAuthority>();
		for(SecurityAuth auth : users.getSecurityAuthList()) {
			securityAuthList.add(new SimpleGrantedAuthority(auth.getAuth()));
		}
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		if(enabled.equals("0")) {
			return true;
		} else {
			return false;
		}
	}
	
}
