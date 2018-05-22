package com.etiansoft.ole.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class LangOrderUtil {

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("lang.properties")));
		TreeMap<String, String> map = new TreeMap<String, String>(new PropertyComparator());
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (!line.trim().isEmpty()) {
				map.put(line.substring(0, line.indexOf('=')).trim(), line);
			}
		}

		Set<Entry<String, String>> entries = map.entrySet();
		for (Entry<String, String> entry : entries) {
			System.out.println(entry.getValue());
		}
		reader.close();
	}
}

class PropertyComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return o1.toLowerCase().compareTo(o2.toLowerCase());
	}
}