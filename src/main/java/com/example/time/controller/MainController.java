package com.example.time.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.time.model.Details;

@RestController
public class MainController {

	public String formatString(String str) {
		String newStr="";
		for(int i=0; i<str.length();i++) {
			if(Character.isUpperCase(str.charAt(i))) {
				newStr+=" ";
				newStr+=str.charAt(i);
			}else {
				newStr+=str.charAt(i);
			}
		}
		return newStr;
	}

	@GetMapping("/getTimeStories")
	public List<Details> Response() throws IOException {
		List<Details> details = new ArrayList<Details>();

		URL url = new URL("https://time.com/");

		Scanner sc = new Scanner(url.openStream());

		StringBuffer sb = new StringBuffer();
		while (sc.hasNext()) {
			sb.append(sc.next());
		}

		String result = sb.toString();
		result = result.substring(result.indexOf("latest-stories__item"), result
				.indexOf("<sectionclass=\"homepage-section-v2mag-subs\"data-module_name=\"MagazineSubscription\">"));

		String[] str;
		str = result.split("<liclass=\"latest-stories__item\">");
		for (int i = 0; i < 6; i++) {
			Details detail = new Details();
			String link;
			String title;
			link = "https://time.com" + str[i].split("<h3class=\"latest-stories__item-headline\">")[0]
					.substring(str[i].split("<h3class=\"latest-stories__item-headline\">")[0].indexOf("<ahref="))
					.replace("<ahref=\"", "").replace("\">", "");
			title = str[i].split("<h3class=\"latest-stories__item-headline\">")[1].substring(0,
					str[i].split("<h3class=\"latest-stories__item-headline\">")[1].indexOf("</"));
			title = formatString(title);
			detail.setlink(link);
			detail.settitle(title);
			details.add(detail);
		}

		return details;
	}

}

