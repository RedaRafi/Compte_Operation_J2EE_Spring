package com.reda.tpCompte.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reda.tpCompte.entities.Users;
import com.reda.tpCompte.service.IUserService;

@Controller
public class UserController {

	@Autowired
	IUserService userService;

	@GetMapping("/Users")
	public String users(Model model,@RequestParam(name="page",defaultValue="0") int page,
			@RequestParam(name="r",defaultValue="0")int r
			,@RequestParam(name="e",defaultValue="")String e
			,@RequestParam(name="u",defaultValue="Utilisateur")String user) {
		Page<Users> users = userService.findAllPage(page,5);
		model.addAttribute("erreur", e);
		if (r==1) {
			model.addAttribute("msg", user +" ajouté avec success");
		}else if(r==2){
			model.addAttribute("msg", "Mot de passe modifié avec success");
		}else if(r==3){
			model.addAttribute("msg", user +" supprimé avec success");
		}else if(r==4){
			model.addAttribute("msg", user +" blocké avec success");
		}else if(r==5){
			model.addAttribute("msg", user +" déblocké avec success");
		}else if(r==6){
			model.addAttribute("msg", "Role modifié avec success");
		}
		model.addAttribute("users", users);
		model.addAttribute("pages", new int[users.getTotalPages()]);
		model.addAttribute("actuel", page);
		return "Users";
	}

	//	-------------------USER OPERATIONS------------------------
	@RequestMapping(value =  "/saveUser")
	public String creerUser(Model model,@RequestParam(name="user") String username,
			@RequestParam(name="p1") String password,@RequestParam(name="p2") String password1,
			@RequestParam(name="role") String role) {
		if (password.equals(password1)) {
			try {
				userService.creerUser(username, password, role);
				return "redirect:Users?r=1&u="+username;
			} catch (Exception e) {
				return "redirect:Users?e="+e.getMessage();
			}
		} else {
			return "redirect:Users?e=Les Mots de passe incompatible ";
		}
	}

	@RequestMapping("/blockUser")
	public String blockerUser(Model model,@RequestParam(name="user") String username) {
		try {
			userService.blockerUser(username);
			return "redirect:Users?r=4&u="+username;
		} catch (Exception e) {
			return "redirect:Users?e="+e.getMessage();
		}
	}

	@RequestMapping("/deBlockUser")
	public String deBlockerUser(Model model,@RequestParam(name="user") String username) {
		try {
			userService.deBlockerUser(username);
			return "redirect:Users?r=5&u="+username;
		} catch (Exception e) {
			return "redirect:Users?e="+e.getMessage();
		}
	}

	@RequestMapping("/deleteUser")
	public String deleteUser(Model model,@RequestParam(name="user") String username) {
		try {
			userService.deleteUser(username);
			return "redirect:Users?r=3&u="+username;
		} catch (Exception e) {
			return "redirect:Users?e="+e.getMessage();
		}
	}

	@RequestMapping("/changeUserPass")
	public String passUser(Model model,@RequestParam(name="user") String username,
			@RequestParam(name="p") String apassword,@RequestParam(name="p1") String password,@RequestParam(name="p2") String password1) {
		if (password.equals(password1)) {
			try {
				userService.changePass(username, password,apassword);
				return "redirect:Users?r=2&u="+username;
			} catch (Exception e) {
				return "redirect:Users?e="+e.getMessage();
			}
		} else {
			return "redirect:Users?e=Les Mots de passe incompatible";
		}
	}

	@RequestMapping("/changeUserRole")
	public String roleUser(Model model,@RequestParam(name="user") String username,@RequestParam(name="role") String role) {
		try {
			userService.changeRole(username, role);
			return "redirect:Users?r=6&u="+username;
		} catch (Exception e) {
			return "redirect:Users?e="+e.getMessage();
		}
	}

}
