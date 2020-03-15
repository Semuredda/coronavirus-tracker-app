package com.semhal.updatedhealthinfo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.semhal.updatedhealthinfo.model.LocationStats;
import com.semhal.updatedhealthinfo.service.CoronaVirusData;

@Controller
public class HomeResource {
	@Autowired
	CoronaVirusData coronaVirusData;
	
	@GetMapping("/")
	public String home(Model model) {
	
		List<LocationStats> locationStats = coronaVirusData.getAllStats();
		int totalCases = locationStats.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
		int totalNewCases = locationStats.stream().mapToInt(stat->stat.getDiffFromPrevDay()).sum();
		
		model.addAttribute("locationStat", locationStats);
		model.addAttribute("totalReportedCases", totalCases);
		model.addAttribute("totalPrevCases",totalNewCases);
		
		
		return "home";
	}
}
