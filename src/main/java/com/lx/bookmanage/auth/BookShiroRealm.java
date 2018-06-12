package com.lx.bookmanage.auth;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.hibernate.validator.internal.util.privilegedactions.GetMethodFromPropertyName;
import org.springframework.beans.factory.annotation.Autowired;

import com.lx.bookmanage.model.Admin;
import com.lx.bookmanage.model.Student;
import com.lx.bookmanage.pojo.User;
import com.lx.bookmanage.repository.AdminRopository;
import com.lx.bookmanage.repository.StudentRopository;

public class BookShiroRealm extends AuthorizingRealm {
	@Autowired
	private StudentRopository studentRopository;

	@Autowired
	private AdminRopository adminRopository;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken shiroToken = (UsernamePasswordToken) token;

		User user = new User();
		
		if (shiroToken.getHost().equals("0")) {
			Student student = studentRopository.findStudentBySname(shiroToken.getUsername());

			if (student == null) {
				throw new AccountException("帐号或密码不正确！");
			}
			
			user.setId(student.getSno());
			user.setName(student.getSname());
			user.setPassword(student.getSpassword());
			user.setType("0");
		} else {
			Admin admin = adminRopository.findAdminByadminid(shiroToken.getUsername());
			if (admin == null) {
				throw new AccountException("帐号或密码不正确！");
			}
			
			user.setId(admin.getAdminid());
			user.setName(admin.getUsername());
			user.setPassword(admin.getApassword());
			user.setType("1");
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
				getName());

		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("ewaytek"));

		return authenticationInfo;
		

	}
}
