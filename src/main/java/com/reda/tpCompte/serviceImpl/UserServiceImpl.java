package com.reda.tpCompte.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reda.tpCompte.dao.UserDao;
import com.reda.tpCompte.dao.UsersRoleDao;
import com.reda.tpCompte.entities.Users;
import com.reda.tpCompte.entities.Users_Roles;
import com.reda.tpCompte.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private UsersRoleDao usersRoleDao;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void creerUser(String username, String password, String role) {
		Users u = userDao.findOne(username);
		if (u!=null) {
			throw new RuntimeException("User exist déjà !");
		}else {
			password = getBCPE().encode(password);
			u = new Users(username, password, 1,0);
			Users_Roles ur = new Users_Roles(username, role);
			if (role.equals("ADMIN")) {
				u.setRole(1);
				Users_Roles ur2 = new Users_Roles(username, "USER");
				usersRoleDao.save(ur2);
			}
			userDao.save(u);
			usersRoleDao.save(ur);
		}
	}

	@Override
	public void blockerUser(String username) {
		Users u = userDao.findOne(username);
		if (u!=null) {
			u.setActive(0);
			userDao.save(u);
		}else {
			throw new RuntimeException("User introuvable");
		}
		
	}

	@Override
	public void deBlockerUser(String username) {
		Users u = userDao.findOne(username);
		if (u!=null) {
			u.setActive(1);
			userDao.save(u);
		}else {
			throw new RuntimeException("User introuvable");
		}
	}

	@Override
	public void changePass(String username, String password, String apassword) {
		Users u = userDao.findOne(username);
		if (u!=null) {
			if (getBCPE().matches(apassword,u.getPassword())) {
				password = getBCPE().encode(password);
				u.setPassword(password);
				userDao.save(u);
			} else {
				throw new RuntimeException("Mot de passe incorrect");
			}
		}else {
			throw new RuntimeException("User introuvable");
		}
	}

	@Override
	public void changeRole(String username, String role) {
		Users u = userDao.findOne(username);
		if (u!=null) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+role);
			if (role.equals("ADMIN")) {
				if (u.getRole()!=1) {
					u.setRole(1);
					userDao.save(u);
					Users_Roles ur = new Users_Roles(username, role);
					usersRoleDao.save(ur);
				}
			}else {
				u.setRole(0);
				userDao.save(u);
				usersRoleDao.deleteByUsernameAndRole(username, "ADMIN");
			}
			
		}else {
			throw new RuntimeException("User introuvable");
		}
	}

	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void deleteUser(String username) {
		Users u = userDao.findOne(username);
		if (u!=null) {
			userDao.delete(username);
			usersRoleDao.deleteByUsername(username);
		}else {
			throw new RuntimeException("User introuvable");
		}
	}

	@Override
	public Page<Users> findAllPage(int page, int size) {
		Page<Users> users = userDao.findAll(new PageRequest(page, size));
		return users;
	}

}
