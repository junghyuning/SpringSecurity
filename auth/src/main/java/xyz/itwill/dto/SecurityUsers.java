package xyz.itwill.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityUsers {
	private String userid;
	private String passwd;
	private String name;
	private String email;
	private String enabled;
	private List<SecurityAuth> securityAuthList;
}
