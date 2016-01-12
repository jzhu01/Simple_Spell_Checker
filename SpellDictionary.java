import java.util.*;

/**
 * Class that will organize the dictionary
 * @author Jen Zhu
 * @version HW10, Due: 11/30/15
 */
public class SpellDictionary {
	
	/** field for dictionary */
	public static HashSet<String> dictionary;
	
	/**
	 * Constructor for SpellDictionary
	 */
	public SpellDictionary() {
		dictionary = new HashSet<String>();
	}
	
	/**
	 * Method to return T/F if dictionary contains a specific word
	 * @param word - specifies the word being checked
	 * @returns T/F
	 */
	public boolean dictContains(String word) {
		if (dictionary.contains(word)) {
			return true;
		} else {
			return false;
		}
	}
	// DELETION METHOD
	/**
	 * Helper method for running deletion check
	 * @param word - specifies the word being passed in
	 * @returns a list of correct words
	 */
	private static ArrayList<String> delHelper(String word){
		ArrayList<String> correctwords = new ArrayList<String>();
		for ( int i = 0; i < word.length(); i++) {
			String newword = word.substring(0, i)+word.substring(i+1);
			correctwords.add(newword);
		}
		return correctwords;
	}
	
	/**
	 * Method to handle deletions
	 * @param originalWord - specifies the word being passed in
	 * @returns the list of correct words
	 */
	public LinkedList<String> delCheck(String originalWord){
		LinkedList<String> correctwords = new LinkedList<String>();
		ArrayList<String> newwords = delHelper(originalWord);
		for (String s: newwords){
			if (dictionary.contains(s) && !correctwords.contains(s) ) {
				correctwords.addFirst(s);
			} 		
		}
		return correctwords;
	}
	
	// INSERTION METHOD 
	/**
	 * Helper method for insertion sort
	 * @param word - specifies the original word to be altered
	 * @returns correctwords - a list of possible words 
	 */
	private static ArrayList<String> insHelper(String word) {
		ArrayList<String> correctwords = new ArrayList<String>();
		char[] alphabet = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		for (int i=0; i<word.length(); i++) {
			for (int j=0; j < alphabet.length; j++) {
				String newword = word.substring(0, i)+alphabet[j]+word.substring(i);
				correctwords.add(newword);
			}
		}
		return correctwords;
	}
	
	/**
	 * Method to insert a character, and check for near misses
	 * @param originalword - specifies the original word
	 * @returns the list of correct words
	 */
	public LinkedList<String> insCheck(String originalword) {
		LinkedList<String> correctwords = new LinkedList<String>();
		ArrayList<String> newwords = insHelper(originalword);
		for (String s: newwords) {
			if (dictionary.contains(s) && !correctwords.contains(s)) {
				correctwords.addFirst(s);
			}
		}
		return correctwords;
	}
	
	// SUBSTITUTION METHOD
	/**
	 * Helper method for substitution
	 * @param word - specifies the word to be altered
	 * @returns the list of new words
	 */
	private static ArrayList<String> subHelper(String word) {
		char[] alphabet = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
										'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 
										's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		ArrayList<String> newwords = new ArrayList<String>();
		for (int i=0; i<word.length(); i++) {
			for (char temp: alphabet) {
				String newword = word.substring(0, i)+temp+word.substring(i+1);
				newwords.add(newword);
			}
		}
		return newwords;
	}
	
	/**
	 * Method to check substitution near misses. 
	 * @param originalword - specifies the original word being passed in
	 * @returns a list of correct words in the dictionary
	 */
	public LinkedList<String> subCheck(String originalword) {
		LinkedList<String> correctwords = new LinkedList<String>();
		ArrayList<String> newwords = subHelper(originalword);
		for (String s: newwords) {
			if (dictionary.contains(s) && !correctwords.contains(s)) {
					correctwords.addFirst(s);
			} 
		}
		return correctwords;
	}
	
	//TRANSPOSITIONS
	/**
	 * Method to help transposition checks, will do the work for the method
	 * @param word - specifies the word being passed into the method to be altered
	 * @returns the list of new strings after the alterations have been made
	 */
	private static ArrayList<String> transposHelper(String word){
		ArrayList<String> newwords = new ArrayList<String>();
		for (int i=0; i<word.length()-1; i++) {
			char char1 = word.charAt(i);
			char char2 = word.charAt(i+1);
			String newword = word.substring(0, i)+char2+char1+word.substring(i+2);
			newwords.add(newword);
		}
		return newwords;
	}
	
	/**
	 * Method to conduct the transposition checks
	 * @param originalword - specifies the original word that may or may not be correct
	 * @returns the linked list of correct words
	 */
	public LinkedList<String> transposeCheck(String originalword) {
		LinkedList<String> correctwords = new LinkedList<String>();
		ArrayList<String> newwords = transposHelper(originalword);
		for (String s: newwords) {		
			if (dictionary.contains(s) && !correctwords.contains(s)){
					correctwords.add(s);
			}
		}
		return correctwords;
	}
	
	// SPLIT METHOD
	/**
	 * Helper method, will do the splitting 
	 * @param word - original word to be split
	 * @returns the two new words as a single word with a space between them
	 */
	private static ArrayList<String> splitHelper(String word) {
		ArrayList<String> newwords = new ArrayList<String>();
		for (int i=0; i < word.length(); i++) {
			String newword = word.substring(0, i)+" "+word.substring(i);
			newwords.add(newword);
		}
		return newwords;
	}
	
	// NEED TO FIX!
	// PROBLEM: When words are split up, some characters/bits of 
	// words are recognized even though they're definitely not words
	public LinkedList<String> splitCheck(String originalword){
		LinkedList<String> correctwords = new LinkedList<String>();
		ArrayList<String> newwords = splitHelper(originalword);
		for (String s: newwords) {
			Collection<String> split = new ArrayList<String>();
			String[] splitarr1 = s.split(" ",2);
			
			for (String t: splitarr1) {
				split.add(t);
			}
			if (dictionary.containsAll(split) && !correctwords.containsAll(split)){
				correctwords.addAll(split);			}
		}
		return correctwords;
	}
	
	/** Manipulator for dictionary */
	public void setDict(String line){
		dictionary.add(line);
	}
	
}
