package com.lx.bookmanage.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.ArrayUtils;
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
import com.lx.bookmanage.common.validator.Validatorutils;
import com.lx.bookmanage.model.Book;
import com.lx.bookmanage.repository.BookRopository;
import com.lx.bookmanage.validator.group.Addgroup;
import com.lx.bookmanage.validator.group.Updategroup;

@Controller
@RequestMapping("manage/managebook")
public class managebookController {
	@Autowired
	private BookRopository bookRopository;
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
			Book book = bookRopository.findOne(id);
			return Result.ok().put("data", book);
		} catch (Exception e) {
			return Result.ok().put("data", new HashMap<String, Object>());
		}
	}
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Book book) {
		try {
			if (book.getBookid() == 0) {
				// add
				Validatorutils.validateEntity(book, Addgroup.class);
				bookRopository.save(book);

				return Result.ok("添加成功");
			} else {
				// edit
				Validatorutils.validateEntity(book, Updategroup.class);
				bookRopository.save(book);

				return Result.ok("修改成功");
			}
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestBody int[] ids){
		
		for (Integer id : ids) {
			bookRopository.delete(id);
		}
		
		return Result.ok("删除成功");
	}
}
