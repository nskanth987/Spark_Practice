package com.java.practice.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringMatcher {

  public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    List<String> list1 = new ArrayList<String>();
    list.add("axpaj");
    list.add("apxaj");
    list.add("dnrbt");
    list.add("pjxdn");
    list1.add("abd");
    System.out
        .println(findMatchedCount(5, list, "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt"));
    System.out
        .println(findMatchedCount(3, list1, "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt"));
  }

  static int findMatchedCount(int len, List<String> list, String query) {
    int start, queryLength = query.length();
//    Set<String> matched = new HashSet<String>();
    int matched = 0;
    for (String str : list) {
      start = 0;
      char startChar = str.charAt(0), endChar = str.charAt(len - 1);
      int[] strFreq = frequency(str);
      while (start + len <= queryLength) {
        if (startChar == query.charAt(start) && endChar == query.charAt(start + len - 1)) {
          int[] matchFreq = frequency(query.substring(start, start + len));
          if (Arrays.equals(strFreq, matchFreq)) {
//            matched.add(str);
            matched++;
            break;
          }
        }
        start++;
      }
    }
    return matched;
  }

  static int[] frequency(String s) {
    int[] freq = new int[26];
    for (char c : s.toCharArray()) {
      freq[c - 97] += 1;
    }
    return freq;
  }
}
