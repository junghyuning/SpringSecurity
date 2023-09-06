package xyz.itwill.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

//�������ѿ� ���� �پ��� ó���� ���� AccessDeniedHandler �������̽��� �����ϴ� �ڽ�Ŭ���� ����
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//�������ѿ� ���� ��� ���� - ���� ��ݱ�� Ȱ��ȭ ���� ��� �ۼ�
		response.sendRedirect(request.getContextPath()+"accessDenied");
		
	}
	
}
