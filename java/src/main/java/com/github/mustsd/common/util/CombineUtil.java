package com.github.mustsd.common.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 通用组合工具 eg: String[] array = new String[] {"A", "B", "C", "D", "F"}; List<List<String>> result =
 * combine(array, 2); System.out.println(result);
 *
 * @author yangz
 * @date 2022-05-16 15:36
 */
public class CombineUtil {

  /**
   * @param targetArray
   * @param k
   * @return
   */
  public static <T> List<List<T>> combine(T[] targetArray, int k) {
    List<List<T>> result = new ArrayList<>();
    LinkedList<T> path = new LinkedList<>();
    backtracking(targetArray.length, k, 0, targetArray, path, result);
    return result;
  }

  private static <T> void backtracking(
      int n, int k, int startIndex, T[] targetArray, LinkedList<T> path, List<List<T>> result) {
    if (path.size() == k) {
      result.add(new ArrayList<>(path));
      return;
    }
    for (int i = startIndex; i <= n - (k - path.size()); i++) {
      path.add(targetArray[i]);
      backtracking(n, k, i + 1, targetArray, path, result);
      path.removeLast();
    }
  }
}
