package com.calleva.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An object that contains a string that has ngram computation methods.
 */
public class Sentence {

	private String sentence = null;

	public Sentence(String sentence) {
		this.sentence = sentence;
	}

	public List<String> getWords() {
		return parseWords(sentence);
	}

	/**
	 * Returns a list of N-Grams for the sentence.
	 * 
	 * @param sentence
	 * @return
	 */
	public List<String> wordNGrams(Sentence sentence, int n) {

		List<String> nGrams = new ArrayList<String>();
		List<String> tempList = new ArrayList<String>();
		List<String> words = sentence.getWords();

		if (words.size() >= n) {
			for (int i = 0; i < n - 1; i++) {
				if (words.get(i).length() < 1) {
					continue;
				}
				tempList.add(words.get(i));
			}

			for (int i = n - 1; i < words.size(); i++) {
				if (words.get(i).length() < 1) {
					continue;
				}
				tempList.add(words.get(i));
				nGrams.add(makeNGram(tempList));
				tempList.remove(0);
			}
		}

		return nGrams;
	}

	private String makeNGram(List<String> list) {

		String result = "";

		for (int i = 0; i < list.size(); i++) {
			result += list.get(i) + " ";
		}

		return result.trim();
	}

	private List<String> parseWords(String sentence) {

		List<String> words = new ArrayList<String>();

		Pattern word = Pattern.compile("([A-Za-z0-9]*)(?=[ ,-?.!])"); // Regular
																		// expression
																		// to
																		// obtain
																		// a
																		// word
		Matcher m = word.matcher(sentence);

		while (m.find()) {
			if (!m.group().equals("")) {
				words.add(m.group());
			}
		}

		return words;
	}

}
