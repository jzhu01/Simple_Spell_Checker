import java.io.*;
import java.util.*;

/**
 * Class that will implement the spellchecker interface
 * @author Jen Zhu
 * @version HW10, Due: 11/30/15
 */
public class SpellChecker {
	
	/**
	 * Method to read in the text file that has the dictionary
	 * @param filename - specifies the path to the file
	 * @returns - will return a BufferedReader input if the file is found
	 */
	public static Scanner findFile(String filename){
		try { 
			Scanner reader = new Scanner(new FileReader(filename));
			return reader;
		} catch (FileNotFoundException e){
			System.out.println("I'm sorry. We were unable to find your file.");
			System.out.println("Please try again. Goodbye.");
			System.exit(-1);
			return null;
		}
	}
	
	/**
	 * Method to read the dictionary
	 * @param input - specifies the buffered reader input being passed in
	 */
	public static void readFile(Scanner input, SpellDictionary dictionary) {
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String line1 = line.replaceAll("[^a-zA-z]","").toLowerCase();
			dictionary.setDict(line1);
		}
		input.close();
	}
	
	/**
	 * Method to read the words passed in as command line arguments
	 * @param input - specifies the BufferedReader input being passed in
	 * @returns the string of all the words needed to be gone through
	 */
	private static ArrayList<String> readText(BufferedReader input) {
		ArrayList<String> wordsToBeChecked = new ArrayList<String>();
		try {
			for (String line = input.readLine(); line != null; line = input.readLine()) {
				String[] wordlist = line.split(" ");
				for (String t: wordlist) {
					//System.out.println("Word is: "+t);
					wordsToBeChecked.add(t.replaceAll("[^a-zA-z]", "").toLowerCase());
				}
			}
		} catch (IOException e) {
			System.out.println("There was a problem reading the file.");
			System.out.println("Please try again. Goodbye.");
			System.exit(-1);
		}
		return wordsToBeChecked;
	}
	
	/**
	 * Method to do the checks
	 */
	public static LinkedList<String> checker(String word, SpellDictionary dictionary) {
		if (dictionary.dictContains(word)) {
			return null;
		} else {
			LinkedList<String> checks = new LinkedList<String>();
			// deletion checks
			LinkedList<String> temp = dictionary.delCheck(word);
			if (!temp.isEmpty()) {
				checks.addAll(temp);
			}
			temp.clear();
			// insertion checks
			temp = dictionary.insCheck(word);
			if (!temp.isEmpty()) {
				checks.addAll(temp);
			}
			temp.clear();
			// substitution checks
			temp = dictionary.subCheck(word);
			if (!temp.isEmpty()) {
				checks.addAll(temp);
			} 
			temp.clear();
			// transposition checks
			temp = dictionary.transposeCheck(word);
			if (!temp.isEmpty()) {
				checks.addAll(temp);
			}
			temp.clear();
			// split checks - NEED TO FIX!!
			temp = dictionary.splitCheck(word);
			if (!temp.isEmpty()) {
				//for (String s: temp) {
				//	System.out.println(s);
				//}
				checks.addAll(temp);
			}
			return checks;
		}
	}
	
	/**
	 * Main Helper Method
	 */
	private static void mainHelper(ArrayList<String> list, SpellDictionary dict, LinkedList<String> alreadycheckedwords) {
		for (String s: list) {
			if (alreadycheckedwords.contains(s)) {
				continue;
			} else {
				alreadycheckedwords.push(s);
				LinkedList<String> correctwords = checker(s, dict);
				if (correctwords == null) {
					System.out.println(s+" was spelled correctly.");
				} else {
					System.out.print(s+" is spelled incorrectly. Suggestions: ");
					for (String t: correctwords) {
						System.out.print(t+" ");
					}
					System.out.println("");
				}
			}
		}
	}
	
	/**
	 * Main method to run the interface
	 * @param args
	 */
	public static void main(String[] args) {
		// build the dictionary
		SpellDictionary dict = new SpellDictionary();
		//findFile("/usr/share/dict/words.txt");
		Scanner filereader = findFile("american-english");
		readFile(filereader, dict);
		
		// stack to store the words already checked
		LinkedList<String> wordsalreadychecked = new LinkedList<String>();
		
		// read the files that need to be spell checked
		if (args.length > 0) {
			ArrayList<String> wordsnotchecked = new ArrayList<String>();
			for (String s: args) {
				wordsnotchecked.add(s);
			}
			mainHelper(wordsnotchecked, dict, wordsalreadychecked);
		} else {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			ArrayList<String> wordsnotchecked = readText(input);
			mainHelper(wordsnotchecked, dict, wordsalreadychecked);
		}
	}

}
