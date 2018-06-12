package com.lx.bookmanage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lx.bookmanage.model.Borrow;

public interface BorrowRoposotory extends JpaRepository<Borrow, Integer>,JpaSpecificationExecutor<Borrow>{

	@Query("select u from Borrow u join u.student s where s.sno = :sno")
	List<Borrow> findBorrowBySno(@Param("sno") Integer sno);
	
	@Query("select u from Borrow u join u.student s where s.sno = :sno")
	Page<Borrow> findBorrowBySno(@Param("sno") Integer sno, Pageable pageable);
}
