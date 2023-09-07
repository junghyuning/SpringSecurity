package xyz.itwill.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

//�α��� �����Ŀ� ����� ����� �����ϱ����� ũ����
// => �α��� ����Ƚ�� ���� ���� ���
//=> AuthenticationFailureHandler �������̽��� ��ӹ޾� �ۼ��ϰų� AuthenticationFailureHandler �������̽��� ��ӹ��� Ŭ������ ��ӹ޾� �ۼ�
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		request.getSession().setAttribute("userid", request.getParameter("userid"));
		setDefaultFailureUrl("/loginPage");
		super.onAuthenticationFailure(request, response, exception);
	}
}