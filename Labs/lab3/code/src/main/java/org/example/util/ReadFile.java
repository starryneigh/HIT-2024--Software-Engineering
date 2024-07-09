package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
  public static String readTxt(String filePath) {
    StringBuilder result = new StringBuilder();
    try {
      // 创建一个 File 对象
      File file = new File(filePath);
      // 创建一个 Scanner 对象来读取文件内容
      Scanner scanner = new Scanner(file);

      // 使用循环逐行读取文件内容并添加到列表中
      while (scanner.hasNextLine()) {
        result.append(scanner.nextLine());
      }

      // 关闭 Scanner 对象
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("文件未找到：" + e.getMessage());
    }
    return result.toString();
  }
}
