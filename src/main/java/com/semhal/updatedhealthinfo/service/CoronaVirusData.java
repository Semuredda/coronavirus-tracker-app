package com.semhal.updatedhealthinfo.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.semhal.updatedhealthinfo.model.LocationStats;

@Service
public class CoronaVirusData {
	
	private static String Virus_Data_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private List<LocationStats> allStats = new ArrayList<>();


	public List<LocationStats> getAllStats() {
		return allStats;
	}


	@PostConstruct
	@Scheduled(cron = "0 0 */1 * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		 List<LocationStats> newStat = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(Virus_Data_URL))
				.build();
		
		 HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
	
		 StringReader csvBodyReader = new StringReader(httpResponse.body());
		 
		 Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		 for (CSVRecord record : records) {
			
		     String state = record.get("Province/State");
		     String country = record.get("Country/Region");
		     
		     LocationStats locationstat = new LocationStats();
		     locationstat.setStates(state);
		     locationstat.setCountry(country);
		     System.out.println(Integer.parseInt(record.get(record.size()-2)));
		     int latestCases = Integer.parseInt(record.get(record.size()-2));
		     int prevCases = Integer.parseInt(record.get(record.size()-3));
		  
		     
		     locationstat.setLatestTotalCases(latestCases);
		     locationstat.setDiffFromPrevDay(latestCases - prevCases);
		     newStat.add(locationstat);
		    
		 }
		 this.allStats = newStat;
		     
	}
	
}
