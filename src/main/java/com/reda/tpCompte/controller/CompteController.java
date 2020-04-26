package com.reda.tpCompte.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reda.tpCompte.entities.Client;
import com.reda.tpCompte.entities.Compte;
import com.reda.tpCompte.service.IClientService;
import com.reda.tpCompte.service.ICompteService;
import com.reda.tpCompte.service.IOperationService;
import com.reda.tpCompte.service.IUserService;

@Controller
public class CompteController {

	@Autowired
	ICompteService compteService;
	@Autowired
	IOperationService operationService;
	@Autowired
	IClientService clientService;
	@Autowired
	IUserService userService;


	@GetMapping("/Comptes")
	public String comptes() {
		return "Comptes";
	}


	@GetMapping("/consulterClient")
	public String consulterClient(Model model,@RequestParam(name="client") Long id,
			@RequestParam(name="r",defaultValue="") String resultas,
			@RequestParam(name="page",defaultValue="0") int page) {
		if (!resultas.equals("")) {
			model.addAttribute("success", resultas);
		}
		try {
			Client c = clientService.consulterClient(id);
			model.addAttribute("client", c);
			Page<Compte> comptes = compteService.findByClient(id, page, 5);
			model.addAttribute("comptes", comptes);
			System.out.println("comptes :: " +comptes);
			model.addAttribute("pages", new int[comptes.getTotalPages()]);
			model.addAttribute("actuel", page);
		} catch (Exception e) {
			model.addAttribute("erreur", e.getMessage());
		}

		return "Comptes";
	}


	//	-------------------COMPTE OPERATIONS------------------------

	@RequestMapping("/saveCompte")
	public String saveCompte(Model model,@RequestParam(name="code") String code,@RequestParam(name="solde") Double solde,@RequestParam(name="montant") Double montantType,
			@RequestParam(name="cl") Long client,@RequestParam(name="type") int type) {
		String res = compteService.createCompte(code, solde, montantType, client, type);
		return "redirect:consulterClient?r="+res+"&client="+client;////////////////////////////////////////
	}

	@RequestMapping("/editCompte")
	public String editCompte(Model model,@RequestParam(name="code") String code,@RequestParam(name="cl") Long client,@RequestParam(name="montant") Double montantType,@RequestParam(name="type") int type) {
		String res = compteService.editeCompte(code,montantType, type,client);
		return "redirect:consulterClient?r="+res+"&client="+client;
	}

	@RequestMapping("/deleteCompte")
	public String deleteCompte(Model model,@RequestParam(name="code") String code,@RequestParam(name="cl") Long client) {
		String res = compteService.deleteCompte(code);
		return "redirect:consulterClient?r="+res+"&client="+client;
	}


}
