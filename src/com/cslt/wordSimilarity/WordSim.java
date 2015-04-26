package com.cslt.wordSimilarity;

/**
 * Wordsim类是词语或tag相似度的语义计算的基类<br/>
 * 它主要是语义层间的词语的相似度计算，其子类实现同位词、上下位词和w2v的语义相似度计算。
 * @author liurong
 *
 */
public class WordSim {
   
	protected String res_path = "";//资源文件
	protected double distance = 0;//词语距离
	public static boolean Switch = true;
	
	public void test(){
		System.out.println("good");
	}
	public WordSim(String[] path){
		this.res_path = res_path;
	}
	public WordSim(){
		
	}
	/**
	 * 基类获取两个词语的近似度
	 * @return 相似度
	 */
	public static double GetWordSim(String word1, String word2){
		return -1;
	}
}
