package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 * Edited by Kaiyang Yao on 05/2020
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// Implemented in week 3
		if(sourceText == null) {
			return;
		}
		if(!starter.equals("")) {
			return;
		}

		String[] input = sourceText.split(" +");
		starter = input[0];
		String prevWord = starter;
		for(int i = 1; i < input.length; i++) {
			String w = input[i];

			if(containsWord(prevWord)) {
				getNode(prevWord).addNextWord(w);
			} else {
				ListNode newNode = new ListNode(prevWord);
				wordList.add(newNode);
				newNode.addNextWord(w);
			}

			prevWord = w;
		}


		// Deal with the last word
		if(containsWord(prevWord)) {
			getNode(prevWord).addNextWord(starter);
		} else {
			ListNode newNode = new ListNode(prevWord);
			wordList.add(newNode);
			newNode.addNextWord(starter);
		}
	}

	/**
	 * Check if the word list already contains the node with the given word
	 */
	private boolean containsWord(String word) {
		for(ListNode node : wordList) {
			if (node.getWord().equals(word)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the node with the given word in the word list
	 */
	private ListNode getNode(String word) {
		for(ListNode node: wordList) {
			if(node.getWord().equals(word)) {
				return node;
			}
		}
		return null;
	}


	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		// Implemented in week 3
		if(starter.equals("")) {
			return "";
		}
		if(numWords == 0) {
			return "";
		}

		String currWord = starter;
		StringBuilder output = new StringBuilder();
		output.append(currWord);
		int count = 1;
		while(count < numWords) {
			ListNode currNode = getNode(currWord);
			String w = currNode.getRandomNextWord(rnGenerator);
			output.append(" " + w);
			currWord = w;
			count++;
		}

		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// Implemented in week 3
		wordList.clear();
		starter = "";
		train(sourceText);
	}
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// Implemented in week 3
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


