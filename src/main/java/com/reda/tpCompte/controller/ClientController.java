package com.reda.tpCompte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.reda.tpCompte.entities.Client;
import com.reda.tpCompte.service.IClientService;

@Controller
public class ClientController {

	@Autowired
	IClientService clientService;


	@GetMapping("/Clients")
	public String clients(Model model,@RequestParam(name="page",defaultValue="0") int page,@RequestParam(name="x",defaultValue="0")int c,
			@RequestParam(name="cle",defaultValue="")String cle) {
		Page<Client> clients = clientService.findByCle(cle, page, 5);

		if (c==1) {
			model.addAttribute("msg", "Client ajouté avec success");
		}else if(c==2){
			model.addAttribute("msg", "Client modifié avec success");
		}else if(c==3){
			model.addAttribute("msg", "Client supprimé avec success");
		}
		model.addAttribute("clients", clients);
		model.addAttribute("pages", new int[clients.getTotalPages()]);
		model.addAttribute("actuel", page);
		model.addAttribute("cle", cle);
		return "Clients";
	}

	//	-------------------CLIENT OPERATIONS------------------------

	@RequestMapping("/saveClient")
	public String saveclient(Model model,@RequestParam(name="nom") String nom
			,@RequestParam(name="cin",defaultValue="") String cin
			,@RequestParam(name="email",defaultValue="") String email) {
		clientService.creerClient(nom,cin,email);
		return "redirect:Clients?c=1";
	}

	@RequestMapping("/editClient")
	public String editclient(Model model,@RequestParam(name="nom") String nom,@RequestParam(name="c") Long code
			,@RequestParam(name="cin",defaultValue="") String cin
			,@RequestParam(name="email",defaultValue="") String email) {
		clientService.editClient(code,nom,cin,email);
		return "redirect:Clients?c=2";
	}

	@RequestMapping("/deleteClient")
	public String deleteClient(Model model,@RequestParam(name="c") Long code) {
		clientService.deleteClient(code);
		return "redirect:Clients?c=3";
	}

}
