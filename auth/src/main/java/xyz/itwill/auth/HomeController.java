package xyz.itwill.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.itwill.security.CustomUserDetails;

//Spring Security : SpringMVC ���α׷��� ������ �ΰ� ����� �����ϴ� ���� �����ӿ�ũ

//����(Authentication) : SpringMVC ���α׷��� ����� �� �ִ� ����ڰ� �´����� Ȯ���ϴ� ����
// => ������ ���������� �����ϱ� ���� ����ڸ� �ĺ��� �� �ִ� ���� �ʿ� - Credential  
//�ΰ�(Authorization - ���� �ο�) : ������ ����ڰ� ��û�� �ڿ��� ���� �����Ѱ��� �����ϴ� ����
// => ���� ó�� �� ����Ǿ� ������ �ο� ���� �� ������ ������ �Ϲ������� ��Ȱ(Role) ���·� �ο�

//Spring Security�� ������ �ΰ��� ���� Principal ��ü�� ���̵�� Credential ��ü�� ��й�ȣ�� 
//����ϴ� Credential ����� ���� ��� ���

//Spring Security�� SpringMVC ���α׷��� �����Ͽ� �������� �� �ִ� ���
// => �پ��� ����(���α��� ����, ��ū ��� ����, OAuth2 ��� ����, LDAP ����)�� ����� ���� ��� ���� 
// => ���α׷� ������� ��Ȱ(Role)�� ���� ���� ���� ����
// => ���α׷����� �����ϴ� �ڿ��� ���� ���� ����
// => ����Ÿ ��ȣȭ
// => SSL ����
// => �Ϲ������� �˷��� ������ ���� ����

//Spring Security�� SpringMVC ���α׷��� �����ϴ� ���
//1.spring-security-web, spring-security-core, spring-security-config, spring-security-tablibs
//���̺귯���� ������Ʈ�� ���� ó�� - ���̺� : pom.xml
// => ��� ���̺귯�� ������ �����ϰ� �����ϸ� Spring �����ӿ�ũ ���� ���̺귯���� ������ ��� ���� ó��
//2.[web.xml] ���Ͽ� Spring Security ����� �����ϴ� ���� Ŭ������ ���ͷ� ����ϰ� ���Ͱ� ����Ǳ�
//���� URL �ּҸ� ���� ó�� 
//3.[web.xml] ���Ͽ� Spring Security ����� ���Ͱ� ����ϱ� ���� ������ �����ϴ� Spring Bean
//Configuration File ���� - ContextLoaderListener Ŭ������ ���� �� �ֵ��� ���� ��� ����
//4.Spring Security ���� Spring Bean Configuration File �ۼ�
// => Spring Security ���� ���Ͱ� ���۵Ǳ� ���� ������ Security ���ӽ����̽� �߰��Ͽ� 
//spring-security.xsd ������ ������Ʈ�� ����Ͽ� ���� 

//Spring Security Filter�� ����
//1.SecurityContextPersistenceFilter : SecurityContextRepository���� SecurityContext�� �������ų� �����ϴ� ����
//2.LogoutFilter : �α׾ƿ� ��û�� ó���ϴ� ����
//3.UsernamePasswordAuthenticationFilter : ���̵�� ��й�ȣ�� ����ϴ� Form ��� ���� ������ ó���ϴ� ����
//=> Authentication ��ü�� ����� AuthenticationManager���� ���� ó�� ����
//=> AuthenticationManager�� �������� ������ ���� ���� �ܰ踦 �Ѱ��ϴ� AuthenticationProvider����
//���� ó���� ���� - UserDetailService�� ���� ���񽺸� ����ؼ� ���� ó��
//4.ConcurrentSessionFilter : ���� ���ǰ� ���õ� ���� - ���� �α��� ����
//5.RememberMeAuthenticationFilter : ������ ������ų� ���� �Ǵ��� ��Ű �Ǵ� DB�� ����Ͽ�
//����� ��ū�� ������� ���� ó���ϴ� ����
//6.AnonymousAuthenticationFilter : ����� ������ �������� �ʾҴٸ� �͸� ����� ��ū�� ��ȯ�ϴ� ����
//7.SessionManagementFilter : �α��� �� Session�� ���õ� �۾��� ó���ϴ� ����
//8.ExceptionTranslationFilter : ���� ü�� ������ �߻��Ǵ� ���� �� �ΰ� ���� ���ܸ� ó���ϴ� ����
//9.FilterSecurityInterceptor : ���� �ο��� ������ ������ AccessDecisionManager���� ������ ����
//�ο� ���� �� ���� ��� ó���ϴ� ����
//10.HeaderWriterFilter: Request�� HTTP ����� �˻��� Header�� �߰��ϰų� ���ִ� ����
//11.CorsFilter : �㰡�� ����Ʈ�� Ŭ���̾�Ʈ�� ��û���� �˻��ϴ� ����
//12.CsrfFilter : CSRF Tocken�� ����Ͽ� CSRF ������ �����ִ� ����� �����ϴ� ����

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	/*
	private final SecurityUsersService securityUsersService;
	
	//Principal �������̽� >> Authentication �������̽� >> AbstractAuthenticationToken �߻�Ŭ���� 
	//>> UsernamePasswordAuthenticationToken Ŭ���� - ����Ŭ���� 
	
	//Principal ��ü : �α��ε� ����� ������ ���� ������ ����� ��ü
	// => Principal.getName() �޼ҵ�� ���̵� �����޾� ��� ����
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Principal principal) {
		if(principal != null) {
			//log.warn("���̵� = "+principal.getName());
			
			SecurityUsers users=securityUsersService.getSecurityUsers(principal.getName());
			
			log.warn("���̵� = "+users.getUserid());
			log.warn("�̸� = "+users.getName());
			log.warn("�̸��� = "+users.getEmail());
		}
		
		return "home";
	}
	*/

	//Authentication ��ü : �α��ε� ����� ������ ���� ������ ����� ��ü
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Authentication authentication) {
		if(authentication != null) {
			//Authentication.getPrincipal() : Authentication ��ü���� ����ڿ� ���� ������
			//Object Ÿ���� ��ü�� ��ȯ�ϴ� �޼ҵ� - �ݵ�� UserDetails ��ü�� ����ȯ�Ͽ� ���
			CustomUserDetails users=(CustomUserDetails)authentication.getPrincipal();
			log.warn("���̵� = "+users.getUserid());
			log.warn("�̸� = "+users.getName());
			log.warn("�̸��� = "+users.getEmail());
		}
		
		return "home";
	}

	@RequestMapping(value = "/guest/page", method = RequestMethod.GET)
	public String guestPage() {
		return "guest_page";
	}
	
	@RequestMapping(value = "/user/page", method = RequestMethod.GET)
	public String userPage() {
		return "user_page";
	}
	
	@RequestMapping(value = "/manager/page", method = RequestMethod.GET)
	public String managerPage() {
		return "manager_page";
	}
	
	@RequestMapping(value = "/admin/page", method = RequestMethod.GET)
	public String adminPage() {
		return "admin_page";
	}
}