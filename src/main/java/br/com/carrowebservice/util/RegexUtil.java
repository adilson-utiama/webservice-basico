package br.com.carrowebservice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	private static final Pattern regexAll = Pattern.compile("/carros");
	private static final Pattern regexById = Pattern.compile("/carros/([0-9]*)");
	
	public static Long matchId(String requestUrl){
		Matcher matcher = regexById.matcher(requestUrl);
		if(matcher.find() && matcher.groupCount() > 0){
			String string = matcher.group(1);
			if(string != null && string.trim().length() > 0){
				long id = Long.parseLong(string);
				return id;
			}
		}
		return null;
	}
	
	public boolean matchAll(String requestUrl){
		Matcher matcher = regexAll.matcher(requestUrl);
		if(matcher.find()){
			return true;
		}
		
		return false;
	}
}
