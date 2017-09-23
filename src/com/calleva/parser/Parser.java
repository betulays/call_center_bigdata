package com.calleva.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Uses regular expressions to parse a Sentence into words or ngrams.
 */

public class Parser {

	public List<Sentence> parseSentence(String data) {

		List<Sentence> allSentence = new ArrayList<Sentence>();

		Pattern sentence = Pattern.compile("\\S.*?[?.!]"); // Regular expression
															// to obtain a
															// sentence
		Matcher m = sentence.matcher(data);

		while (m.find()) {
			Sentence aSentence = new Sentence(m.group());
			allSentence.add(aSentence);
		}

		return allSentence;
	}
}
