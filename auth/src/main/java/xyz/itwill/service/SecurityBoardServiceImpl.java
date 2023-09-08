package xyz.itwill.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityBoard;
import xyz.itwill.repository.SecurityBoardRepository;
import xyz.itwill.repository.SecurityUsersRepository;

@Service
@RequiredArgsConstructor
public class SecurityBoardServiceImpl implements SecurityBoardService {
	private final SecurityUsersRepository securityUsersRepository;
	private final SecurityBoardRepository securityBoardRepository;

	@Override
	public void addSecurityBoard(SecurityBoard board) {
		if(securityUsersRepository.selectSecurityUsersByUserid(board.getWriter()) == null) {
			throw new IllegalArgumentException("�Խñ� �ۼ��ڸ� ã�� �� �����ϴ�.");
		}
		securityBoardRepository.insertSecurityBoard(board);
	}

	@Override
	public void modifySecurityBoard(SecurityBoard board) {
		if(securityUsersRepository.selectSecurityUsersByUserid(board.getWriter()) == null) {
			throw new IllegalArgumentException("�Խñ� �ۼ��ڸ� ã�� �� �����ϴ�.");
		}
		
		if(securityBoardRepository.selectSecurityBoardByIdx(board.getIdx()) == null) {
			throw new IllegalArgumentException("�Խñ��� ã�� �� �����ϴ�.");
		}
		securityBoardRepository.updateSecurityBoard(board);
	}

	@Override
	public void removeSecurityBoard(int idx) {
		if(securityBoardRepository.selectSecurityBoardByIdx(idx) == null) {
			throw new IllegalArgumentException("�Խñ��� ã�� �� �����ϴ�.");
		}
		securityBoardRepository.deleteSecurityBoard(idx);
	}

	@Override
	public SecurityBoard getSecurityBoardByIdx(int idx) {
		SecurityBoard board=securityBoardRepository.selectSecurityBoardByIdx(idx);
		if(board == null) {
			throw new IllegalArgumentException("�Խñ��� ã�� �� �����ϴ�.");
		}
		return board;
	}

	@Override
	public Map<String, Object> getSecurityBoardList(Map<String, Object> map) {
		
		return null;
	}
}




