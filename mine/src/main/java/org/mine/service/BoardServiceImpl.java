package org.mine.service;

import java.util.List;

import org.mine.domain.BoardVO;
import org.mine.domain.Criteria;
import org.mine.mapper.AttachMapper;
import org.mine.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private AttachMapper attachMapper;
	
	@Override
	public void register(BoardVO board) {
		log.info("11111111111111111");
		mapper.insertSelectKey(board);
		
		log.info("11111111111111111");
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		log.info("22222222222222222");
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
		
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

	@Override
	public List<BoardVO> getListWithPaging(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotalCount(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

}
