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

//�α��� �����Ŀ� ����� ����� �����ϱ����� Ŭ���� AuthenticationSuccessHandler�������̽��� ������
// => AuthenicationSuccessHandler �������̽� or �ش� �������̽��� ��ӹ޾� ������ Ŭ������ ��ӹ޾� �ۼ�
// => ������� ������ �α��γ�¥�� ����ó���ϴ� �ý���
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler
{

	// �α��� ������ ������ Ȯ���Ͽ� Ư�� �������� ������ ��û�ǵ��� ����
	// Authentication ��ü : ���� �� �ΰ�(����)�� ���õ� ������ ������ ��ü
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//�α��� ������� ������ �����ϱ����� List ��ü ����
		List<String> roleNames= new ArrayList<String>();
		
		//Authentication.getAuthorities() : ������ ������ ���õ� ��� ������ List��ü�� ��ȯ�ϴ� �޼��� -> ������ ������ ���� �� �����Ƿ� (ex. ������ = �Ϲݻ���� + �Ŵ��� + ������ ����)
		//GrantedAuthority ��ü : ����ڿ��� �ο��� ���ѿ� ���� ������ ������ ��ü
		for(GrantedAuthority authority : authentication.getAuthorities()) {
			//GrantedAuthority.getAuthorities() :GrantedAuthority ��ü�� ����� ������ ��ȯ�ϴ� �޼��� 
			//�ο��� ����(getAuthority)�� List��ü(roleNames)�� ����
			roleNames.add(authority.getAuthority()); 
		}
		
		
		//Collection<T>.contains(T obj) : Collection ��ü�� ����� ����� ���� ������ Ȯ���Ͽ� Collection ��ü�� ��Ұ� ���� ��� [false] �ִ°�� [true]��ȯ 
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect(request.getContextPath()+"/admin/page");
		}
		if(roleNames.contains("ROLE_MANAGER")) {
			response.sendRedirect(request.getContextPath()+"/manager/page");
			
		}
		if(roleNames.contains("ROLE_USER")) {
			response.sendRedirect(request.getContextPath()+"/user/page");
			
		}
		//�������� ��� ���� ���
		response.sendRedirect(request.getContextPath()+"/guest/page");			
	
		
	}
	
}
