package xyz.itwill.security;

import org.springframework.security.crypto.password.PasswordEncoder;

//��й�ȣ�� ���޹޾� ��ȣȭ ó���Ͽ� ��ȯ�ϰų� ��ȣȭ ó���� ��й�ȣ�� ���� ����� ��ȯ
//�ϴ� �޼ҵ带 �����ϴ� Ŭ����
// => PasswordEncoder �������̽��� ��ӹ޾� �ۼ�
public class CustomPasswordEncoder implements PasswordEncoder {
	//�Ű������� ���޹��� ���ڿ��� ��ȣȭ ó���Ͽ� ��ȯ�ϴ� �޼ҵ�
	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	//�Ű������� ���޹��� ��ȣȭ�� ���ڿ��� �Ϲ� ���ڿ��� ���Ͽ� ����� �������� ��ȯ�ϴ� �޼ҵ�
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

}