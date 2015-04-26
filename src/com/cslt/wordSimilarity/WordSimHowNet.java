package com.cslt.wordSimilarity;

import com.cslt.wordSimilarity.howNetWordSim.WordSimilarity;




/**
 * WordSimHowNet定义了基于HowNet的词语相似度<br/>
 * 词类是对com.huilan.sqa.howNetWordSim中WordSimilarity类封装<br/>
 * 
 * @author liurong
 *
 */
public class WordSimHowNet extends WordSim {
     
	private WordSimilarity ws;
	
	public WordSimHowNet(String[] path){
		super(path);
		if(!this.res_path.equals("")){
			ws = new WordSimilarity();
	//		ws.loadRes();
		}	
	}
	
	public WordSimHowNet(){
		super();
		ws = new WordSimilarity();
	//	ws.loadRes();
	}
	public static double GetWordSim(String word1, String word2){
		return WordSimilarity.simWord(word1, word2);
	
	}
}
