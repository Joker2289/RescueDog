package kr.or.ddit.rms.user.protected_animal_board;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.rms.vo.Board_detailVO;
import kr.or.ddit.rms.vo.Protected_boardVO;

public class ProtectedBoardUserServiceImpl extends UnicastRemoteObject implements IProtectedBoardUserService{

	ProtectedBoardDaoImpl dao;
	public ProtectedBoardUserServiceImpl() throws RemoteException {
		dao = ProtectedBoardDaoImpl.getInstance();
	}	
	
	@Override
	public int insertBoard(Protected_boardVO bv) throws RemoteException {
		return dao.insertBoard(bv);
	}

	@Override
	public int deleteBoard(Protected_boardVO bv) throws RemoteException {
		return dao.deleteBoard(bv);
	}

	@Override
	public int updateBoard(Protected_boardVO bv) throws RemoteException {
		return dao.updateBoard(bv);
	}

	@Override
	public List<Protected_boardVO> getAllBoardList() throws RemoteException {
		return dao.getAllBoardList();
	}

	@Override
	public List<Protected_boardVO> getAllBoard_SerchList(Protected_boardVO vo) throws RemoteException {
		return dao.getAllBoard_SerchList(vo);
	}
	@Override
	public List<Protected_boardVO> getprotectedTextSearch(Protected_boardVO vo) throws RemoteException {
		return dao.getprotectedTextSearch(vo);
	}


}
