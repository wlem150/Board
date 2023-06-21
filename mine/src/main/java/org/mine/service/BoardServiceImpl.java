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
	
	
	// ���� �߻� �ٺ� ���� : BoardMapper.xml �� �ִ� insertSelectKey 
	
	// mapper �� ���ؼ� ���޹޴� bno �� ���� 1�� �����ϴ� ���� �ƴ϶�, 2�� �����Ͽ��� ������, ������ �߻�
	// register �� ���ؼ� ���ο� �Խñ��� ������� �� seq_board.nextval from dual ������ �־ 2���� �����.
	// �׷��� board.getBno() �� ���ؼ� ���� bno �� ���� 1�� �ö� ��Ȳ.
	// �׷��� ������, attach.insert() �� �ص�, ã������ �ϴ� bno ���� ���� ������, FK ������ �߻��� �� 
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
