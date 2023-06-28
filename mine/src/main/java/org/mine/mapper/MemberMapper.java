package org.mine.mapper;

import org.mine.domain.MemberVO;

public interface MemberMapper {
	public MemberVO read(String userId);
}
