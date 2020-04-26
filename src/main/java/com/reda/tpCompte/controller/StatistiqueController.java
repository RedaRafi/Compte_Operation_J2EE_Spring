package com.reda.tpCompte.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.reda.tpCompte.entities.Client;
import com.reda.tpCompte.entities.Compte;
import com.reda.tpCompte.service.IClientService;
import com.reda.tpCompte.service.ICompteService;
import com.reda.tpCompte.service.IOperationService;
import com.reda.tpCompte.service.IUserService;

@Controller
public class StatistiqueController {

	@Autowired
	ICompteService compteService;
	@Autowired
	IOperationService operationService;
	@Autowired
	IClientService clientService;

	//	------------------------------------------- CHARTS -------------------------------------


	@GetMapping("/statistiques")
	public String springMVC2(ModelMap modelMap,@RequestParam(name="year",defaultValue="0")int annee ,
			@RequestParam(name="client",defaultValue="")Long client,
			@RequestParam(name="compte",defaultValue="") String compte) {
		List<Double> operationList = operationService.findOperationsStat(annee, client, compte);
		modelMap.addAttribute("dataPointsList", operationList );
		List<Double> operationVers = operationService.findOperationsStat(annee, client, compte,"V");
		modelMap.addAttribute("dataPointsVerse", operationVers );
		List<Double> operationRetr = operationService.findOperationsStat(annee, client, compte,"R");
		modelMap.addAttribute("dataPointsRetrait", operationRetr );
		List<Integer> annees = operationService.getAnnee();
		modelMap.addAttribute("annees", annees);
		List<Client> clients = clientService.findAll();
		modelMap.addAttribute("clients", clients);
		List<Compte> comptes = compteService.findAll();
		modelMap.addAttribute("comptes", comptes);
		modelMap.addAttribute("client", client );
		modelMap.addAttribute("compte", compte );
		modelMap.addAttribute("annee", annee );
		return "stat";
	}

	@GetMapping("/statCompte")
	public String getComptes(ModelMap modelMap ,@RequestParam(name="client",defaultValue="")Long client) {
		List<Compte> comptes;
		if (client!=null) {
			comptes = compteService.findByClient(client);
		}else {
			comptes = compteService.findAll();
		}

		modelMap.addAttribute("comptes", comptes);
		return "stat :: compte";
	}

}
