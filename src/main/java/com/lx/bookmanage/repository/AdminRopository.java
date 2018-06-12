package com.lx.bookmanage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lx.bookmanage.model.Admin;
import com.lx.bookmanage.model.Student;

public interface AdminRopository extends JpaRepository<Admin, String>,JpaSpecificationExecutor<Admin> {
	@Query("from Admin u where u.username = :adminid")
	Admin findAdminByadminid(@Param("adminid") String sname);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query("update Admin u set u.apassword=:password where u.adminid = :adminid")
	int updatePassword(@Param("adminid") int adminid, @Param("password")  String password);
}
