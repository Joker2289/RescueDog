package kr.or.ddit.rms.shelter.missing_animal_board;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.rms.vo.MissingVO;

public class Missing_animalSServiceImpl extends UnicastRemoteObject implements IMissing_animalSService {
	IMissing_animalSDao dao;

	public Missing_animalSServiceImpl() throws RemoteException {
		dao = Missing_animalSDaoImpl.getInstance();
	}

	@Override
	public List<MissingVO> getmissingAll() throws RemoteException {
		return dao.getmissingAll();
	}

	@Override
	public List<MissingVO> getSearchmissing(MissingVO vo) throws RemoteException {
		return dao.getSearchmissing(vo);
	}

	@Override
	public int getmissing(MissingVO vo) throws RemoteException {
		return dao.getmissing(vo);
	}

	@Override
	public int insertmissing(MissingVO vo) throws RemoteException {
		return dao.insertmissing(vo);
	}

	@Override
	public int updatemissing(MissingVO vo) throws RemoteException {
		return dao.updatemissing(vo);
	}

	@Override
	public List<MissingVO> getmissingTextSearch(MissingVO vo) throws RemoteException {
		return dao.getmissingTextSearch(vo);
	}

	@Override
	public int deletemissing(MissingVO vo) throws RemoteException {
		return dao.deletemissing(vo);
	}

	@Override
	public int updatemissing_free(MissingVO vo) throws RemoteException {
		return dao.updatemissing_free(vo);
	}

}
