package com.example.time.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.time.model.Details;

@RestController
public class MainController {

	@GetMapping("/getTimeStories")
	public List<Details> Response() throws IOException {
		List<Details> details = new ArrayList<Details>();
		Document doc = Jsoup.connect("http://time.com/").get();
		Elements newsHeadlines = doc.getElementsByClass("latest-stories__item");
		for (Element headline : newsHeadlines) {
			Details detail = new Details();
			detail.settitle(headline.getElementsByClass("latest-stories__item-headline").text());
			detail.setlink(headline.select("a[href]").attr("abs:href"));
			details.add(detail);
		}
		return details;
	}

}
