package com.cslt.wordSimilarity;

import com.cslt.wordSimilarity.w2vWordSim.WordVector;




public class WordSimW2v extends WordSim {

	private static WordVector wv = null;
	
	public WordSimW2v(){
		wv = new WordVector();
	}
	
	public static double GetWordSim(String word1, String word2){
		return wv.Distance(word1, word2);
	}
}
