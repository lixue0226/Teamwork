package com.lx.bookmanage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lx.bookmanage.model.Book;

public interface BookRopository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

}
