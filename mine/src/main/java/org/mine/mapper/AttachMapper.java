package org.mine.mapper;

import java.util.List;

import org.mine.domain.BoardAttachVO;

public interface AttachMapper {
	public void insert(BoardAttachVO vo);
	public void delete(String uuid);
	public List<BoardAttachVO> fileByBno(long bno);
}
