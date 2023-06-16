package org.mine.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample2Mapper {
	@Insert("insert into tbl_sample2 (col2) values(#{data})")
	public void insertCol2(String data);
}
