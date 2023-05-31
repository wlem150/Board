package org.mine.service;

import java.util.List;

import org.mine.domain.BoardVO;
import org.mine.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService {
	private BoardMapper mapper;
	
	@Override
	public void register(BoardVO board) {
		mapper.insertSelectKey(board);
		
	}

    @Override
    public boolean modify(BoardVO board) {
        log.info("modify=====================");
        return mapper.update(board) == 1;
    }

	@Override
	public boolean remove(long bno) {	
		log.info("remove==============");
		return mapper.delete(bno) == 1;
	}

	@Override
	public BoardVO get(long bno) {
		log.info("get");
		return mapper.read(bno);
	}

	@Override
	public List<BoardVO> getList() {
		log.info("getList");
		return mapper.getList();
	}

}