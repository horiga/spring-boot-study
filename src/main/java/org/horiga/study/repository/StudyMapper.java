package org.horiga.study.repository;

import org.apache.ibatis.annotations.Param;
import org.horiga.study.domain.Study;

public interface StudyMapper {
	
	//@Select("select `id`, `name`, `regdt`, `modidt` from `study` where `id` = #{id}")
	Study findById(@Param("id") String id);
	
	//@Insert("insert into study(`id`, `name`, `regdt`) values(#{id}, #{name}, now())")
	void insert(
			@Param("id") String id,
			@Param("name") String name);
}
