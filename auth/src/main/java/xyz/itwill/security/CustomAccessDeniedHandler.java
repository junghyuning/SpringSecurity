package xyz.itwill.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

//접근제한에 대한 다양한 처리를 위해 AccessDeniedHandler 인터페이스를 구현하는 자식클래스 생성
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//접근제한에 대한 명령 실행 - 계정 잠금기능 활성화 등의 명령 작성
		response.sendRedirect(request.getContextPath()+"accessDenied");
		
	}
	
}
