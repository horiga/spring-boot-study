package org.horiga.study.repository;

import org.apache.ibatis.annotations.Param;
import org.horiga.study.domain.Study;
import org.horiga.study.mybatis.Mapper;

@Mapper
public interface StudyRepository {
	Study findById(@Param("id") String id);
	void insert(
			@Param("id") String id,
			@Param("name") String name);
}
