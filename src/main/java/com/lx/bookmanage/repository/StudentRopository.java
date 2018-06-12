package com.lx.bookmanage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lx.bookmanage.model.Student;

public interface StudentRopository extends JpaRepository<Student,Integer>, JpaSpecificationExecutor<Student> {

	@Query("from Student u where u.sname = :sname")
	Student findStudentBySname(@Param("sname") String sname);

	@Transactional
	@Modifying(clearAutomatically=true)
	@Query("update Student u set u.spassword=:password where u.sno = :sno")
	int updatePassword(@Param("sno") int sno, @Param("password")  String password);
}
