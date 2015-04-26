package com.cslt.wordSimilarity.w2vWordSim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * WordVector 类主要是利用word vector 计算两个词的距离<\br>
 * 词类的word vector的导入文件为google w2v工具训练输出的.v文件。
 * 词语的距离计算公式为cos余弦距离。
 * @author liurong
 *
 */
public class WordVector  {

	
	private HashMap<String, float[]> wordMap = new HashMap<String, float[]>();
	private HashMap<String, float[]> wordMapNorm = new HashMap<String, float[]>();
	
	public static String path = "./res/word2vec/qa15w-word-cn.v";
	
	private int words;
	private int size;
	private int topNSize = 40;
	
	public WordVector(){
		try {
			this.loadModelV();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 加载模型
	 * 
	 * @param path
	 *            模型的路径
	 * @throws IOException
	 */
 	public boolean loadModelV(String path) throws IOException {
		File file = new File(path);   	
        if (file.isFile() && file.exists()) {      
            try {               
                InputStreamReader in = new InputStreamReader(new FileInputStream(file), "utf-8");   
                BufferedReader bf = new BufferedReader(in);    
                
                String word;
        		float[] vectors = null;
        		float[] vector = null;
        		double len=0;
                
        		String temp;  
                int num = 0;
                String [] tp = null;
                if ((temp=bf.readLine()) !=null)
                {
                	tp = temp.split(" ");
                	if (tp.length == 2)
                	{
                		words = Integer.parseInt(tp[0]);
                		size = Integer.parseInt(tp[1]);
                	}
                	else
                	{
                		System.out.println("file format has some problem");
                		return false;
                	}
                }
                while ((temp=bf.readLine()) !=null || num < words) {   
                //	System.out.println(temp);
                	tp = temp.split(" ");
                	word = tp[0];
                	vectors = new float[size];
                	vector = new float[size];
                	if (tp.length != size+1)
                	{
                		System.out.println("file format has some problem");
                		return false;
                	}
                	for(int i=0; i<size; i++)
                	{
                		vectors[i] = Float.parseFloat(tp[i+1]);
                		vector[i] = vectors[i];
                		len += vectors[i]*vectors[i];
                	}
                	wordMap.put(word,vector);
                	len = Math.sqrt(len);
                	for(int i=0; i<size; i++)
                	{
                		vectors[i] = vectors[i]/(float)len;
                	}
                	wordMapNorm.put(word, vectors);
                    num++;
                }   
                bf.close();   
                in.close();                
                   
            }catch (FileNotFoundException e) {                 
                System.out.println("File(%s) not found");   
                e.printStackTrace();   
                   
            } catch (IOException e) {   
                System.out.println("Something wrong when reading file");   
                e.printStackTrace();   
            }    
        } 
        return true;
	}

	public boolean loadModelV() throws IOException {
		File file = new File(this.path);   	
        if (file.isFile() && file.exists()) {      
            try {               
                InputStreamReader in = new InputStreamReader(new FileInputStream(file), "utf-8");   
                BufferedReader bf = new BufferedReader(in);    
                
                String word;
        		float[] vectors = null;
        		float[] vector = null;
        		double len=0;
                
        		String temp;  
                int num = 0;
                String [] tp = null;
                if ((temp=bf.readLine()) !=null)
                {
                	tp = temp.split(" ");
                	if (tp.length == 2)
                	{
                		words = Integer.parseInt(tp[0]);
                		size = Integer.parseInt(tp[1]);
                	}
                	else
                	{
                		System.out.println("file format has some problem");
                		return false;
                	}
                }
                while ((temp=bf.readLine()) !=null || num < words) {   
                	//System.out.println(temp);
                	tp = temp.split(" ");
                	word = tp[0];
                	vectors = new float[size];
                	vector = new float[size];
                	if (tp.length != size+1)
                	{
                		System.out.println("file format has some problem");
                		return false;
                	}
                	for(int i=0; i<size; i++)
                	{
                		vectors[i] = Float.parseFloat(tp[i+1]);
                		vector[i] = vectors[i];
                		len += vectors[i]*vectors[i];
                	}
                	wordMap.put(word,vector);
                	len = Math.sqrt(len);
                	for(int i=0; i<size; i++)
                	{
                		vectors[i] = vectors[i]/(float)len;
                	}
                	wordMapNorm.put(word, vectors);
                    num++;
                }   
                bf.close();   
                in.close();                
                   
            }catch (FileNotFoundException e) {                 
                System.out.println("File(%s) not found");   
                e.printStackTrace();   
                   
            } catch (IOException e) {   
                System.out.println("Something wrong when reading file");   
                e.printStackTrace();   
            }    
        } 
        return true;
	}
	
	private static final int MAX_SIZE = 50;
	/**
	 * 得到词向量
	 * 
	 * @param word
	 * @return
	 */
	public float[] getWordVector(String word) {
	//	System.out.println(wordMap.get(word));
		return wordMap.get(word);
	}

	public int getTopNSize() {
		return topNSize;
	}

	public void setTopNSize(int topNSize) {
		this.topNSize = topNSize;
	}

	public HashMap<String, float[]> getWordMap() {
		return wordMap;
	}

	public int getWords() {
		return words;
	}
    public String getWord(float[] vector){
    	//wordMap.g
    	return null;
    }
	public int getSize() {
		return size;
	}
    
	public double Cosin(float[] vec1, float[] vec2)
	{
		double len1 = 0,len2 = 0, len3=0;
	    for (int a = 0; a < size; a++)
	    {
	        len1 += vec1[a] * vec1[a];
	        len2 += vec2[a] * vec2[a];
	        len3 += vec1[a] * vec2[a];
	    }
	    len1 = Math.sqrt(len1);
	    len2 = Math.sqrt(len2);

	    return len3/(len1*len2);
	}
    
	public double Distance(String word1, String word2)
	{
		float[] vector1 = null;
		float[] vector2 = null;
		
		vector1 = this.getWordVector(word1);
		vector2 = this.getWordVector(word2);
		
		if (vector1 == null || vector2 == null)
		{
		//	System.out.println("not find the word in vocab:"+word1+" or "+word2);
			return 0;
		}
		return this.Cosin(vector1, vector2);
	}
    
	public ArrayList<Entry<String,Float>> FindClassWord(String word, int max)
	{
		//HashMap<String, Float> wordScore = new HashMap<String, Float>();
	//	double len=0;
		//check the max 
		if(max > words-1)
		{
			max = words-1;
		}
		float[] vector = null;
		vector = this.wordMapNorm.get(word);
        return this.FindClassWord(vector, max);
	
	}
    
	public ArrayList<Entry<String,Float>> FindClassWord(float[] vector, int max)
	{
		HashMap<String, Float> wordScore = new HashMap<String, Float>();
	//	double len=0;
		//check the max 
		if(max > words-1)
		{
			max = words-1;
		}
		if (vector == null || vector.length != size)
		{
			System.out.println("the vector doesn't mach the word vector");
			return null;
		}
	    float[] value =null;
	    float dist = 0;
	    for (int a = 0; a < size; a++) dist += vector[a] * vector[a];
	    dist = (float) Math.sqrt(dist);
	    for (int a = 0; a < size; a++) vector[a] = vector[a]/dist;
	    dist = 0;
	    Iterator it = this.wordMapNorm.entrySet().iterator();  
		while(it.hasNext()){  
		    Entry  entry=(Entry)it.next();  
		    String key=entry.getKey().toString();  
		    value=(float[])entry.getValue();  
		    dist=0;
		    for (int a = 0; a < size; a++) dist += vector[a] * value[a];
		    wordScore.put(key, dist);
		}  
		//sort
		ArrayList<Entry<String,Float>> sort = new ArrayList<Entry<String,Float>>(wordScore.entrySet());
		Collections.sort(sort, new Comparator<Map.Entry<String, Float>>() {     
			   public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {  
				   Float d1 = o1.getValue();
				   Float d2 = o2.getValue();
				   int r = d2.compareTo(d1);
				   if (r != 0)
                       return r;
                   else
                       return o2.getKey().compareTo(o1.getKey());
		       }   
	    });   
	//	for(Entry<String,Float> e : l) {   
	//	      System.out.println(e.getKey() + "::::" + e.getValue());   
	//	      }  	  
	    return sort;
	}

	public static void main(String[] args) throws IOException {
		WordVector vec = new WordVector();
		//vec.loadModelBin("H:\\语音识别\\lm\\word2vvector\\google_word2vec\\vector\\char-cn.bin");
	//	vec.loadModelV("H:\\wordvector\\google-word\\vector\\qa15w-word-cn.v");
        System.out.println(vec.Distance("办理", "办"));
	//	ArrayList<Entry<String,Float>> l = vec.FindClassWord("人", 10);
	//    for(Entry<String,Float> e : l) {   
	//	       System.out.println(e.getKey() + "::::" + e.getValue());   
	//	      } 
	}

}
