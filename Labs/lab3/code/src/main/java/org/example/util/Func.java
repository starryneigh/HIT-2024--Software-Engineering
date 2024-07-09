package org.example.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.example.Main;
import org.example.graph.DirectedGraph;

import static org.example.Main.queryBridgeWords;

public class Func {
  public static void testBridgeWords(String word1, String word2) {
    String result = queryBridgeWords(word1, word2);  // 调用queryBridgeWords方法查询桥接词
    System.out.println(result);  // 打印查询结果
  }

  public static void graphvizShow(String dotContent) throws IOException, InterruptedException {
    // 将 DOT 内容写入临时文件
    File dotFile = new File("graph.dot");  // 创建File对象表示临时文件graph.dot
    PrintWriter writer = new PrintWriter(dotFile);  // 创建PrintWriter对象用于写入文件
    writer.println(dotContent);  // 将DOT内容写入文件
    writer.close();  // 关闭PrintWriter

    // 执行 Graphviz 的命令，将 DOT 文件转换为 PNG 图片
    String[] cmd = {"dot", "-Tpng", "-o", "graph.png", "graph.dot"};  // 定义Graphviz的命令数组
    ProcessBuilder pb = new ProcessBuilder(cmd);  // 创建ProcessBuilder对象，用于执行命令
    pb.directory(new File("."));  // 设置命令执行的目录为当前目录
    Process process = pb.start();  // 启动进程执行命令
    process.waitFor();  // 等待命令执行完成

    // 显示生成的 PNG 图片
    String os = System.getProperty("os.name").toLowerCase();  // 获取操作系统名称
    if (os.contains("windows")) {  // 如果是Windows系统
      Runtime.getRuntime().exec("cmd /c start graph.png");  // 使用cmd命令打开PNG图片
    } else if (os.contains("mac")) {  // 如果是Mac系统
      Runtime.getRuntime().exec("open graph.png");  // 使用open命令打开PNG图片
    } else {  // 其他系统（Linux等）
      Runtime.getRuntime().exec("xdg-open graph.png");  // 使用xdg-open命令打开PNG图片
    }

    if (!dotFile.delete()) {
      System.err.println("Warning: Failed to delete temporary file graph.dot");
    }
  }

  public static String getStr(String resourceName) {
    // 获取当前类的类加载器
    ClassLoader classLoader = Main.class.getClassLoader();
    // 使用类加载器获取资源文件的 URL
    java.net.URL resourceUrl = classLoader.getResource(resourceName);
    if (resourceUrl != null) {
      // 打印资源文件的路径
      System.out.println("file path: " + resourceUrl.getPath());
    } else {
      System.out.println("file not found");
    }
    assert resourceUrl != null;
    return ReadFile.readTxt(resourceUrl.getPath());  // 调用ReadFile类的readTxt方法读取资源文件内容并返回
  }

  public static DirectedGraph buildDirectedGraph(List<String> wordList) {
    DirectedGraph graph = new DirectedGraph();  // 创建DirectedGraph对象
    for (String word : wordList) {
      graph.addNode(word);  // 添加节点到图中
    }
    graph.buildAdjacencyMatrix();  // 构建邻接矩阵
    for (int i = 1; i < wordList.size(); i++) {
      String source = wordList.get(i - 1);  // 获取起始节点的单词
      String destination = wordList.get(i);  // 获取目标节点的单词
      graph.addEdge(source, destination);  // 添加边到图中
    }
    return graph;  // 返回构建好的有向图对象
  }

  public static List<String> getWords(String str) {
    str = str.toLowerCase();  // 将字符串转换为小写
    String englishLine = str.replaceAll("[^a-zA-Z\\s]", " ");  // 将非字母字符替换为空格
    String[] words = englishLine.split("\\s+");  // 将字符串按空格分割为单词数组
    List<String> wordList = new ArrayList<>();  // 创建ArrayList对象，用于存储单词
    for (String word : words) {
      if (word.length() > 0) {
        wordList.add(word);  // 将非空单词添加到单词列表中
      }
    }
    return wordList;  // 返回单词列表
  }

  public static void writeToFile(String fileName, String content) throws IOException {
    PrintWriter writer = new PrintWriter(fileName);  // 创建PrintWriter对象用于写入文件
    writer.println(content);  // 将内容写入文件
    writer.close();  // 关闭PrintWriter

    System.out.println("Write to file successfully.");
    System.out.println("Content: " + content);
  }
}