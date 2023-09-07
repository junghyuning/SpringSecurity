package xyz.itwill.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//로그인 성공후에 실행될 기능을 제공하기위한 클래스 AuthenticationSuccessHandler인터페이스를 구현함
// => AuthenicationSuccessHandler 인터페이스 or 해당 인터페이스를 상속받아 구현한 클래스를 상속받아 작성
// => 사용자의 마지막 로그인날짜를 변경처리하는 시스템
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler
{

	// 로그인 계정의 권한을 확인하여 특정 페이지를 무조건 요청되도록 설정
	// Authentication 객체 : 인증 및 인가(권한)와 관련된 정보를 저장한 객체
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//로그인 사용자의 권한을 저장하기위한 List 객체 생성
		List<String> roleNames= new ArrayList<String>();
		
		//Authentication.getAuthorities() : 인증된 계정과 관련된 모든 권한을 List객체로 반환하는 메서드 -> 권한은 여러개 가질 수 있으므로 (ex. 관리자 = 일반사용자 + 매니저 + 관리자 권한)
		//GrantedAuthority 객체 : 사용자에게 부여된 권한에 대한 정보를 저장한 객체
		for(GrantedAuthority authority : authentication.getAuthorities()) {
			//GrantedAuthority.getAuthorities() :GrantedAuthority 객체에 저장된 권한을 반환하는 메서드 
			//부여된 권한(getAuthority)을 List객체(roleNames)에 저장
			roleNames.add(authority.getAuthority()); 
		}
		
		
		//Collection<T>.contains(T obj) : Collection 객체에 저장된 요소의 존재 유무를 확인하여 Collection 객체에 요소가 없는 경우 [false] 있는경우 [true]반환 
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect(request.getContextPath()+"/admin/page");
		}
		if(roleNames.contains("ROLE_MANAGER")) {
			response.sendRedirect(request.getContextPath()+"/manager/page");
			
		}
		if(roleNames.contains("ROLE_USER")) {
			response.sendRedirect(request.getContextPath()+"/user/page");
			
		}
		//세권한이 모두 없을 경우
		response.sendRedirect(request.getContextPath()+"/guest/page");			
	
		
	}
	
}
