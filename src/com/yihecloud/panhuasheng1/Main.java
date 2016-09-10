package com.yihecloud.panhuasheng1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;  
import java.util.zip.ZipFile;  
import java.util.zip.ZipInputStream;  

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String password = bufferedReader.readLine();
		if ("123456".equals(password)) {
			count();
		}
	}

	public static void count() throws IOException{
		String path = "C:\\Users\\WatsonPan\\Desktop\\test.txt.zip";
		ZipFile zipFile = new ZipFile(path);  
        InputStream inputStream = new BufferedInputStream(new FileInputStream(path));  
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry ;
        while ((zipEntry = zipInputStream.getNextEntry())!=null) {
        	StringBuffer headWord = new StringBuffer();
        	
        	List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>();
        	
        	if (!zipEntry.isDirectory()) {
        		long fileSize = zipEntry.getSize();  
        		if (fileSize > 0) {  
        			BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipEntry)));  
        			char cbuf[] = new char[10000000];
        			int off = 0;
        			int len = 10000000;
        			while ((br.read(cbuf, off, len)) != -1) {  
        				String bufString = new String(cbuf);
        				String word[] = bufString.split(";");
        				list.addAll(apent(word).entrySet());	
        			}  
        			br.close();  
        		}  
        	}  
        	System.out.print(head5(head(list)));
		}
	}
	public static HashMap<String, Integer> apent(String word[]){
		StringSameCount ssc = new StringSameCount();  
		for (String string : word) {
			ssc.hashInsert(string);  
		}
		HashMap<String, Integer> map = ssc.getHashMap();
		return map;
	}

	public static String head5(List<Map.Entry<String, Integer>> list){
		String string = "";
		int i=0;
		for (Iterator<Map.Entry<String, Integer>> ite = list.iterator(); i < 5; i++) {  
            Map.Entry<String, Integer> maps = ite.next();  
            string = string + maps.getKey() + "\n";
        }  
		return string;
	}
	
	public static List<Map.Entry<String, Integer>> head(List<Map.Entry<String, Integer>> list) {
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {  
            public int compare(Map.Entry obj1, Map.Entry obj2) {// 从高往低排序  
                if (Integer.parseInt(obj1.getValue().toString()) < Integer  
                        .parseInt(obj2.getValue().toString()))  {
                    return 1;  
                }
                if (Integer.parseInt(obj1.getValue().toString()) == Integer  
                        .parseInt(obj2.getValue().toString()))  {
                    return 0;  
                }else{  
                    return -1;  
                }
            }  
        });  
		return list;
	}
	
}

class StringSameCount {  
    private HashMap map;  
    private int counter;  
    public StringSameCount() {  
        map = new HashMap<String,Integer>();  
    }  
    public void hashInsert(String string) {  
        if (map.containsKey(string)) {   //判断指定的Key是否存在  
            counter = (Integer)map.get(string);  //根据key取得value  
            map.put(string, ++counter);  
        } else {  
            map.put(string, 1);  
        }  
    }  
    public HashMap getHashMap(){  
        return map;  
    }  
}  