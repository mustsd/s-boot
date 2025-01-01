package com.github.mustsd.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全排列
 *
 * @author yangz
 * @date 2022-05-17 9:00
 */
public class ArrangeUtil {

  public static <T> List<List<T>> arrange(T[] targetArray) {
    boolean[] used = new boolean[targetArray.length];
    Arrays.fill(used, false);
    List<List<T>> result = new ArrayList<>();
    List<T> path = new ArrayList<>();
    backTrack(targetArray, used, path, result);
    return result;
  }

  private static <T> void backTrack(
      T[] targetArray, boolean[] used, List<T> path, List<List<T>> result) {
    if (path.size() == targetArray.length) {
      result.add(new ArrayList<>(path));
      return;
    }
    for (int i = 0; i < targetArray.length; i++) {
      // used[i - 1] == true，说明同⼀树枝nums[i - 1]使⽤过
      // used[i - 1] == false，说明同⼀树层nums[i - 1]使⽤过
      // 如果同⼀树层nums[i - 1]使⽤过则直接跳过
      if (i > 0 && targetArray[i] == targetArray[i - 1] && used[i - 1] == false) {
        continue;
      }
      // 如果同⼀树⽀nums[i]没使⽤过开始处理
      if (used[i] == false) {
        // 标记同⼀树⽀nums[i]使⽤过，防止同一树支重复使用
        used[i] = true;
        path.add(targetArray[i]);
        backTrack(targetArray, used, path, result);
        // 回溯，说明同⼀树层nums[i]使⽤过，防止下一树层重复
        path.remove(path.size() - 1);
        // 回溯
        used[i] = false;
      }
    }
  }
}
