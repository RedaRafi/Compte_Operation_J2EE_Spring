package com.reda.tpCompte.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;

import com.reda.tpCompte.entities.Compte;
import com.reda.tpCompte.entities.CompteCourant;
import com.reda.tpCompte.entities.Operation;
import com.reda.tpCompte.service.ICompteService;
import com.reda.tpCompte.service.IOperationService;
import com.reda.tpCompte.util.DateUtil;

@Controller
public class OperationController {

	@Autowired
	ICompteService compteService;
	@Autowired
	IOperationService operationService;

	@GetMapping("/Operations")
	public String index() {
		return "Operations";
	}

	@GetMapping("/consulter")
	public String consulterCompte(Model model,@RequestParam(name="compte") String id,
			@RequestParam(name="page",defaultValue="0") int page,@RequestParam(name="x",defaultValue="") String erreur2,
			@RequestParam(name="r",defaultValue="") String resultas,
			@RequestParam(name="vers",defaultValue="") String versement,
			@RequestParam(name="retr",defaultValue="") String retrait,
			@RequestParam(name="dateMin",defaultValue="") String dateMinString,
			@RequestParam(name="dateMax",defaultValue="")  String dateMaxString
			) {
		model.addAttribute("max", dateMaxString);
		model.addAttribute("min", dateMinString);
		Date dateMin = null,dateMax = null;
		if (!dateMinString.equals("")) {
			dateMin = DateUtil.parse(dateMinString);
		}
		if (!dateMaxString.equals("")) {
			dateMax = DateUtil.parse(dateMaxString);
			dateMax=new Date(dateMax.getTime()+24*60*60*1000);
			if (dateMin!=null && dateMax.before(dateMin)) {
				model.addAttribute("erreur3", "la date max ne doit pas étre avant la date min ");
			}
		}
		if (erreur2.equals("0")) {
			model.addAttribute("success", "Opération terminée avec success");
		}else {
			model.addAttribute("erreur2", erreur2);
		}

		if (!resultas.equals("")) {
			model.addAttribute("success", resultas);
		}
		if (!versement.equals("")) {
			model.addAttribute("vers", "true");
		}else {
			model.addAttribute("notVers", "true");
		}
		if (!retrait.equals("")) {
			model.addAttribute("retr", "true");
		}else {
			model.addAttribute("notRetr", "true");
		}
		try {
			Compte c = compteService.consulterCompte(id);
			model.addAttribute("compte", c);
			model.addAttribute("type", c instanceof CompteCourant ? 1 : 0);
			List<Object> results = operationService.findOperationsFilterMinMax(versement,retrait,dateMin, dateMax, id, page, 6);
			List<Operation> operations = (List<Operation>) results.get(0);
			model.addAttribute("operations", operations);
			model.addAttribute("pages", new int[(Integer)results.get(1)]);
			model.addAttribute("actuel", page);
		} catch (Exception e) {
			model.addAttribute("erreur", e.getMessage());
		}

		return "Operations";
	}

	@RequestMapping(value = "/saveOperation", method = RequestMethod.POST)
	public String saveOp(Model model,@RequestParam(name="codeCompte") String compte1,
			@RequestParam(name="codeCompte2") String compte2,Double montant,String oper) {
		try {	
			switch (oper) {
			case "vers": operationService.verser(compte1, montant);

			break;
			case "retr": operationService.retrait(compte1, montant);

			break;
			case "virme": operationService.virement(compte1, compte2, montant);

			break;

			default:
				break;
			}
		} catch (Exception e) {
			model.addAttribute("erreur2", e.getMessage());
			return "redirect:consulter?compte="+compte1+"&x="+e.getMessage();
		}
		return "redirect:consulter?compte="+compte1+"&x=0";
	}


}
