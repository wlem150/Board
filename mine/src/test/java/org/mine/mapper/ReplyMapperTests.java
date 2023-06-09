package org.mine.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.domain.Criteria;
import org.mine.domain.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void test() {
		Criteria cri = new Criteria(2,10);
		List<ReplyVO> list = mapper.getListWithPaging(cri, 17L);
		list.forEach(reply -> log.info(list));
	}
}
