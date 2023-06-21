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
	
	
	// 오류 발생 근본 원인 : BoardMapper.xml 에 있는 insertSelectKey 
	
	// mapper 를 통해서 전달받는 bno 의 값이 1씩 증가하는 것이 아니라, 2씩 증가하였기 때문에, 오류가 발생
	// register 를 통해서 새로운 게시글이 만들어질 때 seq_board.nextval from dual 구문이 있어서 2번이 실행됨.
	// 그러나 board.getBno() 를 통해서 받은 bno 의 값은 1만 올라간 상황.
	// 그렇기 때문에, attach.insert() 를 해도, 찾을려고 하는 bno 값이 없기 때문에, FK 오류가 발생한 것 
	@Transactional
	@Override
	public void register(BoardVO board) {
		mapper.insertSelectKey(board);
		log.info(board.getBno() + "==========++++=++++++++++++============");
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		if (board.getBno() != null) {
			board.getAttachList().forEach(attach -> {
				log.info(board.getBno() + "==============");
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
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
