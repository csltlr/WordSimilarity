package com.cslt.wordSimilarity.synonymsWordSim;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 本算法主要是来自论文《基于同义词词林的词语相似度计算方法�?田永乐，赵蔚
 * 
 * @author liurong
 * 
 */
public class SynonymsWord {

	private static double PI = 3.1415926;

	protected HashMap<String, List<String>> vocab = new HashMap<String, List<String>>();
	protected List<String> code = new ArrayList<String>();
	protected HashMap<String, Integer> treeNum = new HashMap<String, Integer>();

	public static String code_file = "./res/synonyms_dict/同义词编码.txt";
	public static String vocab_file = "./res/synonyms_dict/同义词词林.txt";

	public SynonymsWord() {
		try {
			this.LoadRes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.TreeBranchNum();
	}

	/**
	 * 导入同义词林和同义词的层�? 文件路径固定./res/synonyms/*
	 * 
	 * @throws IOException
	 */
	public void LoadRes() throws IOException {

		try {
			BufferedReader wordInput = new BufferedReader(
					new InputStreamReader(new FileInputStream(vocab_file),
							"UTF-8"));
			BufferedReader codeInput = new BufferedReader(
					new InputStreamReader(new FileInputStream(code_file),
							"UTF-8"));

			String str;
			// load the vocab and code into memory
			int num = 0;
			while ((str = wordInput.readLine()) != null) {
				String[] word = str.trim().split(" ");
				List<String> code_list = new ArrayList<String>();
				for (int i = 2; i < word.length; i++) {
					code_list.add(word[i]);
					// System.out.println(word[i]);
				}
				num++;
				this.vocab.put(word[0], code_list);
				//System.out.println(word[0]);
			}
		//	System.out.println(num);
			// load the code into memory
			while ((str = codeInput.readLine()) != null) {
				this.code.add(str.trim());
				// System.out.println(str);
			}

			wordInput.close();
			codeInput.close();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ./res/test_class.txt
	}

	/**
	 * 预先计算每一层的节点�?
	 */
	public void TreeBranchNum() {

		String temp, code_word;
		// 建立索引
		for (int i = 0; i < code.size(); i++) {
			code_word = code.get(i);
			//
			temp = code_word.substring(0, 1);
			if (!this.treeNum.containsKey(temp)) {
				this.treeNum.put(temp, 0);
			}
			//
			temp = code_word.substring(0, 2);
			if (!this.treeNum.containsKey(temp)) {
				this.treeNum.put(temp, 0);
			}
			//
			temp = code_word.substring(0, 4);
			if (!this.treeNum.containsKey(temp)) {
				this.treeNum.put(temp, 0);
			}
			temp = code_word.substring(0, 5);
			if (!this.treeNum.containsKey(temp)) {
				this.treeNum.put(temp, 0);
			}
			temp = code_word.substring(0, 7);
			if (!this.treeNum.containsKey(temp)) {
				this.treeNum.put(temp, 0);
			}
			// System.out.println(temp);
		}
		// 计算每层的节点数
		List<String> num2 = new ArrayList<String>();
		List<String> num3 = new ArrayList<String>();
		List<String> num4 = new ArrayList<String>();
		List<String> num5 = new ArrayList<String>();
		String str;
		//
		for (int i = 0; i < code.size(); i++) {
			code_word = code.get(i);
			// 第二�?
			temp = code_word.substring(0, 1);
			str = code_word.substring(0, 2);
			if (!num2.contains(str)) {
				num2.add(str);
				int n = this.treeNum.get(temp);
				n++;
				this.treeNum.put(temp, n);
			}
			// 第三�?
			temp = code_word.substring(0, 2);
			str = code_word.substring(0, 4);
			if (!num3.contains(str)) {
				num3.add(str);
				int n = this.treeNum.get(temp);
				n++;
				this.treeNum.put(temp, n);
			}
			// 第四�?
			temp = code_word.substring(0, 4);
			str = code_word.substring(0, 5);
			if (!num4.contains(str)) {
				num4.add(str);
				int n = this.treeNum.get(temp);
				n++;
				this.treeNum.put(temp, n);
			}
			// 第五�?
			temp = code_word.substring(0, 5);
			str = code_word.substring(0, 7);
			if (!num5.contains(str)) {
				num5.add(str);
				int n = this.treeNum.get(temp);
				n++;
				this.treeNum.put(temp, n);
			}
		}

		// Iterator iter = this.treeNum.entrySet().iterator();
		/*
		 * Iterator iter = this.treeNum.keySet().iterator(); while
		 * (iter.hasNext()) { String key = (String) iter.next(); Integer value =
		 * this.treeNum.get(key); if(key.equals("A")){ System.out.println(key +
		 * " " + value); } }
		 */

	}

	/**
	 * 计算两个词语的相似度，基于同义词�?
	 * 
	 * @param word1
	 * @param word2
	 * @return
	 */
	public double CalcSimilarWord(String word1, String word2) {
		// System.out.println(word1+" "+word2);
		double similar = 0.0;
		List<String> code_list1 = this.vocab.get(word1);
		List<String> code_list2 = this.vocab.get(word2);

		double max_temp = 0.0;
		double temp = 0.0;

		// 不在层级�?
		if (code_list1 == null || code_list2 == null) {
			return similar;
		}

		for (int i = 0; i < code_list1.size(); i++) {
			for (int j = 0; j < code_list2.size(); j++) {
				// System.out.println(code_list1.get(i)+" "+code_list2.get(j));
				temp = CalcSimilar(code_list1.get(i), code_list2.get(j));
				// System.out.println(code_list1.get(i)+" "+code_list2.get(j)+" "+temp);
				if (temp > max_temp) {
					max_temp = temp;
				}
			}
		}
		similar = max_temp;
		return similar;
	}

	public double CalcSimilar(String code1, String code2) {
		double a = 0.65, b = 0.8, c = 0.9, d = 0.96, e = 0.5, f = 0.1;
		double similar = 0.0;
		int n = 1, k = 0;

		int value1 = 0;
		int value2 = 0;
		String s1, s2;

		List<String> s1_level = this.SplitLevel(code1);
		List<String> s2_level = this.SplitLevel(code2);
		String strsub = "";

		int c1, c2;

		// 第一分支
		if (!s1_level.get(0).equals(s2_level.get(0))) {
			return f;
		}

		// 第二分支
		strsub = strsub + s1_level.get(0);
		if (!s1_level.get(1).equals(s2_level.get(1))) {
			k = Math.abs(s1_level.get(1).toCharArray()[0]
					- s2_level.get(1).toCharArray()[0]);
			n = this.treeNum.get(strsub);
			// System.out.println("2:k= "+k+",n= "+n);
			similar = a * Math.cos(n * this.PI / 180)
					* ((double) (n - k + 1) / n);
			return similar;
		}
		// 第三分支
		strsub = strsub + s1_level.get(1);
		if (!s1_level.get(2).equals(s2_level.get(2))) {
			c1 = Integer.parseInt(s1_level.get(2));
			c2 = Integer.parseInt(s2_level.get(2));
			k = Math.abs(c1 - c2);
			n = this.treeNum.get(strsub);

			// System.out.println("3:k= "+k+",n= "+n);

			similar = b * Math.cos(n * this.PI / 180)
					* ((double) (n - k + 1) / n);
			return similar;
		}
		// 第四分支
		strsub = strsub + s1_level.get(2);
		if (!s1_level.get(3).equals(s2_level.get(3))) {
			k = Math.abs(s1_level.get(3).toCharArray()[0]
					- s2_level.get(3).toCharArray()[0]);
			n = this.treeNum.get(strsub);
			// System.out.println("4:k= "+k+",n= "+n);
			similar = c * Math.cos(n * this.PI / 180)
					* ((double) (n - k + 1) / n);
			return similar;
		}
		// 第五分支
		strsub = strsub + s1_level.get(3);
		if (!s1_level.get(4).equals(s2_level.get(4))) {
			c1 = Integer.parseInt(s1_level.get(4));
			c2 = Integer.parseInt(s2_level.get(4));
			k = Math.abs(c1 - c2);
			n = this.treeNum.get(strsub);
			// System.out.println("5:k= "+k+",n= "+n);
			similar = d * Math.cos(n * this.PI / 180)
					* ((double) (n - k + 1) / n);
			return similar;
		}
		if (s1_level.get(5).equals("#") && s2_level.get(5).equals("#")) {
			similar = e;
			return similar;
		}

		similar = 1.0;
		return similar;
	}

	public List<String> SplitLevel(String code_num) {
		List<String> result = new ArrayList<String>();

		result.add(code_num.substring(0, 1));
		result.add(code_num.substring(1, 2));
		result.add(code_num.substring(2, 4));
		result.add(code_num.substring(4, 5));
		result.add(code_num.substring(5, 7));
		result.add(code_num.substring(7, 8));

		return result;
	}

	public static void main(String[] args) throws Exception {
		SynonymsWord sw = new SynonymsWord();

		double score = sw.CalcSimilarWord("身份证", "户口本");
		System.out.println(score);
	}
}
