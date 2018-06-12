package com.lx.bookmanage.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lx.bookmanage.common.utils.Result;
import com.lx.bookmanage.common.utils.ShiroUtils;
import com.lx.bookmanage.common.validator.Assert;
import com.lx.bookmanage.common.validator.Validatorutils;
import com.lx.bookmanage.model.Book;
import com.lx.bookmanage.model.Borrow;
import com.lx.bookmanage.model.Student;
import com.lx.bookmanage.pojo.User;
import com.lx.bookmanage.repository.AdminRopository;
import com.lx.bookmanage.repository.BookRopository;
import com.lx.bookmanage.repository.BorrowRoposotory;
import com.lx.bookmanage.repository.StudentRopository;
import com.lx.bookmanage.validator.group.Addgroup;
import com.lx.bookmanage.validator.group.Updategroup;

@Controller
@RequestMapping("manage/student")
public class StudentController {

	@Autowired
	private StudentRopository studentRopository;
	@Autowired
	private BookRopository bookRopository;
    @Autowired
    private BorrowRoposotory borrowRoposotory;
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "bookname", defaultValue = "") final String bname,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit) {

		try {
			

			Map<String, Object> result = new HashMap<String, Object>();

			List<Borrow> list = borrowRoposotory.findBorrowBySno(ShiroUtils.getUserId());

			result.put("totalPage", 1);
			result.put("currPage", 1);
			result.put("totalCount", list.size());
			result.put("list", list);

			return Result.ok().put("page", result);

		} catch (Exception e) {
			return Result.ok().put("page", new HashMap<String, Object>());
		}
	}

	@RequestMapping("/info/{id}")
	@ResponseBody
	/**
	 * @author 李雪
	 * @param id
	 * @return
	 */
	public Map<String, Object> info(@PathVariable("id") int id) {
		try {
			//还书逻辑
			
       
			Borrow borrow =borrowRoposotory.findOne(id);
              if (borrow.getReturnbooktime()==null) {
        	   Book book = borrow.getBook();
				
				int updateQty = (book.getQty()) + 1;
				book.setQty(updateQty);
				bookRopository.save(book);
				borrow.setReturnbooktime(new Date());
				borrowRoposotory.save(borrow);
	            return Result.ok("还书成功");
			    
           
           } else {
        	      return Result.ok("该书已还");
                
			       }
             
			
				
			
		} catch (Exception e) {
			return Result.ok().put("data", new HashMap<String, Object>());
		}
	}

}
