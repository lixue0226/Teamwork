package com.lx.bookmanage.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lx.bookmanage.common.utils.Result;
import com.lx.bookmanage.common.utils.ShiroUtils;
import com.lx.bookmanage.common.validator.Validatorutils;
import com.lx.bookmanage.model.Book;
import com.lx.bookmanage.model.Borrow;
import com.lx.bookmanage.model.Student;
import com.lx.bookmanage.repository.BookRopository;
import com.lx.bookmanage.repository.BorrowRoposotory;
import com.lx.bookmanage.repository.StudentRopository;
import com.lx.bookmanage.validator.group.Addgroup;
import com.lx.bookmanage.validator.group.Updategroup;

@Controller
@RequestMapping("/manage/findbook")
public class FindBookController {
	@Autowired
	private BookRopository bookRopository;
	
	@Autowired
	private StudentRopository studentRopository;
	
	@Autowired
	private BorrowRoposotory borrowRoposotory;

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "bookname", defaultValue = "") final String bookname,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit) {

		try {
			Pageable pageable = new PageRequest(page - 1, limit);

			Specification<Book> condtions = new Specification<Book>() {

				@Override
				public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate booknamePredicate = cb.like(root.get("bookname").as(String.class), "%" + bookname + "%");
					query.where(booknamePredicate);
					return query.getRestriction();
				}

			};

			Map<String, Object> result = new HashMap<String, Object>();

			Page<Book> list = bookRopository.findAll(condtions, pageable);

			result.put("totalPage", list.getTotalPages());
			result.put("currPage", page);
			result.put("totalCount", list.getTotalElements());
			result.put("list", list.getContent());

			return Result.ok().put("page", result);

		} catch (Exception e) {
			return Result.ok().put("page", new HashMap<String, Object>());
		}
	}

	
	@RequestMapping("/info/{id}")
	@ResponseBody
	public Map<String, Object> info(@PathVariable("id") Integer id) {
		try {
			// 借书逻辑
			// 1、获取书籍信息
			Book book = bookRopository.findOne(id);
			
			// 2、获取当前学生
			Student student = studentRopository.findOne(ShiroUtils.getUserId());
			
			// 3、创建借阅记录
			Borrow borrow=new Borrow();
		
			borrow.setStudent(student);
			borrow.setBook(book);
			borrow.setBorrowidbooktime(new Date());
			
			// 4、保存借阅记录
			borrowRoposotory.save(borrow);
			
			// 5、书籍信息-1
			int updateQty = (book.getQty()) - 1;
			book.setQty(updateQty);
			bookRopository.save(book);
			
			
			return Result.ok("借书成功");
		} catch (Exception e) {
			return Result.ok().put("data", new HashMap<String, Object>());
		}
		/*try {
			//借书逻辑
			Book book = bookRopository.findOne(id);
			Student student = studentRopository.findOne(ShiroUtils.getUserId());
			int updateQty = (book.getQty()) - 1;
			book.setQty(updateQty);
			
			Borrow borrow=new Borrow();
			
			borrow.setStudent(student);
			borrow.setBorrowidbooktime(new Date());
			
			bookRopository.save(book);
       
			borrowRoposotory.save(borrow);


			return Result.ok("借书成功");
		} catch (Exception e) {
			return Result.ok().put("data", new HashMap<String, Object>());
		}*/
	}

		
	}
	

