package com.cslt.wordSimilarity;

import com.cslt.wordSimilarity.howNetWordSim.WordSimilarity;
import com.cslt.wordSimilarity.synonymsWordSim.SynonymsWord;
import com.cslt.wordSimilarity.w2vWordSim.WordVector;




public class WordSimMulti extends WordSim {
	
	private static WordVector wv = null;
	private static WordSimilarity ws = null;
    private static SynonymsWord sw = null;
    
    private static boolean WordVector_on=false;
    private static boolean hownet_on=false;
    private static boolean SynonymsWord_on=false;
    
    private static String xml = "";
    
    public static double wv_theta = 1;
    public static double ws_theta = 1;
    public static double sw_theta = 1;
    
    public static double sigmoid_theta = 5;
    
    public static double Sigmoid(double value){
//    	if(value > 0.8){
//    		return value;
//    	}
//    	else{
//    		return 0;
//    	}
    	//System.out.println("value"+(sigmoid_theta-sigmoid_theta*value));
    	return 1/(Math.exp(sigmoid_theta-sigmoid_theta*value));
    }
    public static void set_theta(double wordvector, double hownet, double synonyms){
    	wv_theta = wordvector;
        ws_theta = hownet;
        sw_theta = synonyms;
    }
    static{
    	lodafile(xml);
    }
    public static void lodafile(String xml){
    	if(WordSim.Switch == true){
	    	System.out.println("资源导入....");
	    	
	    	long startTime=System.currentTimeMillis();   //获取开始时间
	    	System.out.print("导入word vector res..");
	    	wv = new WordVector();
	    	long endTime=System.currentTimeMillis(); //获取结束时间
	        System.out.println("("+(endTime-startTime)+"ms)");
	        
	        startTime=System.currentTimeMillis();   //获取开始时间
	    	System.out.print("导入word hownet res...");
	    	ws = new WordSimilarity();
	    	endTime=System.currentTimeMillis(); //获取结束时间
	        System.out.println("("+(endTime-startTime)+"ms)");
	        
	        startTime=System.currentTimeMillis();   //获取开始时间
	    	//System.out.print("导入word synonyms res...");
	    	sw = new SynonymsWord();
	    	endTime=System.currentTimeMillis(); //获取结束时间
	        System.out.println("("+(endTime-startTime)+"ms)");
    	
            System.out.println("资源导入完毕");
        }
    }
    public WordSimMulti(){
//    	if(WordSim.Switch == true){
//	    	System.out.println("资源导入....");
//	    	
//	    	long startTime=System.currentTimeMillis();   //获取开始时间
//	    	System.out.print("导入word vector res..");
//	    	wv = new WordVector();
//	    	long endTime=System.currentTimeMillis(); //获取结束时间
//	        System.out.println("("+(endTime-startTime)+"ms)");
//	        
//	        startTime=System.currentTimeMillis();   //获取开始时间
//	    	System.out.print("导入word hownet res...");
//	    	ws = new WordSimilarity();
//	    	endTime=System.currentTimeMillis(); //获取结束时间
//	        System.out.println("("+(endTime-startTime)+"ms)");
//	        
//	        startTime=System.currentTimeMillis();   //获取开始时间
//	    	System.out.print("导入word synonyms res...");
//	    	sw = new SynonymsWord();
//	    	endTime=System.currentTimeMillis(); //获取结束时间
//	        System.out.println("("+(endTime-startTime)+"ms)");
//    	
//            System.out.println("资源导入完毕");
//        }
    }
    
    public static double GetWordSim(String word1, String word2){
        if(word1.equals(word2)){
        	 return 0.0;
        }
    	if(WordSim.Switch == true){
    		double s_wv = 0.0;
    		double s_ws = 0.0;
    		double s_sw = 0.0;
    		int num = 0;
    		if(WordVector_on){
			  s_wv = wv.Distance(word1, word2);
			  num++;
    		}
		//	System.out.println(word1+","+word2);
    		if(hownet_on){
			    s_ws = ws.simWord(word1, word2);
			    num++;
    		}
    		if(SynonymsWord_on){ 
			   s_sw = sw.CalcSimilarWord(word1, word2);
			   num++;
    		}
    		//System.out.println(word1+" "+word2+" | w2v:"+s_wv+",hownet:"+s_ws+",synony:"+s_sw+" | "+Sigmoid((wv_theta*s_wv + ws_theta*s_ws + sw_theta*s_sw)/num));
			//return 1.0 - (Double)Sigmoid((wv_theta*s_wv + ws_theta*s_ws + sw_theta*s_sw)*1.0/num);
    		return 1.0 - (Double)(wv_theta*s_wv + ws_theta*s_ws + sw_theta*s_sw)*1.0/num;
    	}else{
    		return 1;
    	}
	}
    
    public static void main(String[] args) {
    	WordSim.Switch=true;
    //	WordSimMulti wsm = new WordSimMulti();
    	
    	String word1 = "是 ";
    	String word2 = "的";
    	System.out.println(WordSimMulti.GetWordSim(word1, word2));
    	System.out.println(WordSimMulti.GetWordSim(word1, word2));
    	
		
	}
}
