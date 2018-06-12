package com.lx.bookmanage.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lx.bookmanage.common.utils.Result;
import com.lx.bookmanage.common.validator.Validatorutils;
import com.lx.bookmanage.model.Student;
import com.lx.bookmanage.repository.StudentRopository;
import com.lx.bookmanage.validator.group.Addgroup;
import com.lx.bookmanage.validator.group.Updategroup;

@Controller
@RequestMapping("/manage/managestudent")
public class ManageStudentController {
	@Autowired
	private StudentRopository studentRopository;

	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Student student) {
		try {
			if (student.getSno() == 0) {
				// add
				Validatorutils.validateEntity(student, Addgroup.class);

				Student exist = studentRopository.findStudentBySname(student.getSname());
				if (exist != null) {
					return Result.error("用户名已存在!");
				}

				Md5Hash hash = new Md5Hash(student.getSpassword(), "ewaytek", 2);
				student.setSpassword(hash.toString());

				studentRopository.save(student);

				return Result.ok("添加成功");
			} else {
				// edit
				Validatorutils.validateEntity(student, Updategroup.class);

				Student userOld = studentRopository.findOne(student.getSno());
				userOld.setSname(student.getSname());
				userOld.setClass_(student.getClass_());
				userOld.setDepartment(student.getDepartment());
				userOld.setSpassword(student.getSpassword());

				if (student.getSpassword() != null) {
					Md5Hash hash = new Md5Hash(student.getSpassword(), "ewaytek", 2);
					userOld.setSpassword(hash.toString());
				}

				studentRopository.save(userOld);

				return Result.ok("修改成功");
			}
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "sname", defaultValue = "") final String sname,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit) {

		try {
			Pageable pageable = new PageRequest(page - 1, limit);

			Specification<Student> condtions = new Specification<Student>() {

				@Override
				public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate usernamePredicate = cb.like(root.get("sname").as(String.class), "%" + sname + "%");
					query.where(usernamePredicate);
					return query.getRestriction();
				}

			};

			Map<String, Object> result = new HashMap<String, Object>();

			Page<Student> list = studentRopository.findAll(condtions, pageable);

			result.put("totalPage", list.getTotalPages());
			result.put("currPage", page);
			result.put("totalCount", list.getTotalElements());
			result.put("list", list.getContent());

			return Result.ok().put("page", result);

		} catch (Exception e) {
			return Result.ok().put("page", new HashMap<String, Object>());
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody int[] snos) {

		for (int sno : snos) {
			studentRopository.delete(sno);
		}

		return Result.ok("删除成功");
	}

	@RequestMapping("/info/{sno}")
	@ResponseBody
	public Map<String, Object> info(@PathVariable("sno") Integer sno) {
		try {
			Student student = studentRopository.findOne(sno);
			return Result.ok().put("data", student);
		} catch (Exception e) {
			return Result.ok().put("data", new HashMap<String, Object>());
		}
	}
}
