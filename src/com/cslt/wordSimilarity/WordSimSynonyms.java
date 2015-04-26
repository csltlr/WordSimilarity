package com.cslt.wordSimilarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cslt.wordSimilarity.synonymsWordSim.SynonymsWord;




public class WordSimSynonyms extends WordSim {
    
	private static SynonymsWord sw = null;
	
	public WordSimSynonyms(){
		sw = new SynonymsWord();
	}
	
	public static double GetWordSim(String word1, String word2){
		return sw.CalcSimilarWord(word1, word2);
	}
	
	
	public static void main(String[] args) {
		HashMap<String, List> map = new HashMap<String, List>();
		List list=new ArrayList();
		list.add("1");
		list.add("2");
		map.put("好", list);
		
	}
}
