package xyz.itwill.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityBoard;
import xyz.itwill.service.SecurityBoardService;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class SecurityBoardController {
	private final SecurityBoardService securityBoardService;
	
	@RequestMapping("/list")
	public String list(@RequestParam Map<String, Object> map, Model model) {
		 model.addAttribute("result", securityBoardService.getSecurityBoardList(map));
		 model.addAttribute("search", map);
		 return "board/list";
	}

	//@PreAuthorize : ��û ó�� �޼ҵ忡 �ʿ��� ������ �����ϱ� ���� ������̼�
	// => value �Ӽ��� SpEL�� �Ӽ������� ���� - �ٸ� �Ӽ��� ���� ��� �Ӽ����� ���� ����
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "board/register";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute SecurityBoard board) {
		board.setSubject(HtmlUtils.htmlEscape(board.getSubject()));
		board.setContent(HtmlUtils.htmlEscape(board.getContent()));
		securityBoardService.addSecurityBoard(board);		
		return "redirect:/board/list";
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, Object> map, Model model) {
		int idx=Integer.parseInt((String)map.get("idx"));
		model.addAttribute("securityBoard", securityBoardService.getSecurityBoardByIdx(idx));
		model.addAttribute("search", map);
		return "board/detail";
	}

	//�α��� ����ڰ� �������̰ų� �α��� ������� ���̵�� �Խñ� �ۼ��ڰ� ���� ��쿡�� 
	//��û ó�� �޼ҵ� ȣ��
	// => SpEL�� �̿��Ͽ� ���� ������ EL ������ ��� ����
	@PreAuthorize("hasRole('ROLE_ADMIN') or principal.userid eq #map['writer']")
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(@RequestParam Map<String, Object> map, Model model) {
		int idx=Integer.parseInt((String)map.get("idx"));
		model.addAttribute("securityBoard", securityBoardService.getSecurityBoardByIdx(idx));
		model.addAttribute("search", map);
		return "board/modify";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or principal.userid eq #board.writer")
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute SecurityBoard board, @RequestParam Map<String, Object> map) throws UnsupportedEncodingException {
		board.setSubject(HtmlUtils.htmlEscape(board.getSubject()));
		board.setContent(HtmlUtils.htmlEscape(board.getContent()));
		securityBoardService.modifySecurityBoard(board);
		
		String pageNum=(String)map.get("pageNum");
		String column=(String)map.get("column");
		String keyword=URLEncoder.encode((String)map.get("keyword"),"utf-8");
		return "redirect:/board/detail?idx="+board.getIdx()+"&pageNum="+pageNum
				+"&column="+column+"&keyword="+keyword;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or principal.userid eq #writer")
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String remove(@RequestParam int idx, @RequestParam String writer) {
		securityBoardService.removeSecurityBoard(idx);
		return "redirect:/board/list";
	}
}