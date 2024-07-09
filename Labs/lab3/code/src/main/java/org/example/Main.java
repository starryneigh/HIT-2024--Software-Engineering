package org.example;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import org.example.graph.DirectedGraph;
import org.example.graph.Node;
import org.example.listener.PauseListener;
import org.example.util.Func;
import org.jetbrains.annotations.NotNull;

/**
 * Main class for the project.
 */
public class Main {
  static DirectedGraph graph;
  private static final SecureRandom secureRandom = new SecureRandom();

  /**
   * @Author xukeyan
   * @Description main function
   * @Date 9:20 2024/6/6
   * @Param [args]
   * @return void
   **/
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.print("please input the file path: ");
    Scanner fileScanner = new Scanner(System.in, "UTF-8");
    String filePath = fileScanner.nextLine();

    String str = Func.getStr(filePath);
    //System.out.println(str);

    List<String> wordList = Func.getWords(str);
    //System.out.println(wordList);

    graph = Func.buildDirectedGraph(wordList);
    //System.out.println(graph);

    Scanner scanner = new Scanner(System.in, "UTF-8");
    int choice;

    do {
      System.out.println();
      System.out.println("Select an option:");
      System.out.println("1. Show directed graph");
      System.out.println("2. Test bridge words");
      System.out.println("3. Generate new text");
      System.out.println("4. calcShortestPath");
      System.out.println("5. Random Walk");
      System.out.println("0. Exit");

      choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1:
          showDirectedGraph(graph);
          break;
        case 2:
          System.out.print("Enter word 1: ");
          String word1 = scanner.nextLine();
          System.out.print("Enter word 2: ");
          String word2 = scanner.nextLine();
          String result = queryBridgeWords(word1, word2);
          System.out.println(result);
          break;
        case 3:
          System.out.print("Enter the new text: ");
          String inputText = scanner.nextLine();
          String newText = generateNewText(inputText);
          System.out.print("Generated new text: ");
          System.out.println(newText);
          break;
        case 4:
          Scanner scanner1 = new Scanner(System.in, "UTF-8");
          System.out.print("Enter the first word: ");
          String word3 = scanner1.nextLine();
          System.out.print("Enter the second word(default): ");
          String word4 = scanner1.nextLine();

          String shortestPath = calcShortestPath(word3, word4);
          System.out.println("Shortest path: " + shortestPath);
          break;
        case 5:
          System.out.println("Performing random walk...");
          System.out.println("Random walking: " + randomWalk());
          break;
        case 0:
          System.out.println("Exiting...");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    } while (choice != 0);
  }

  /**
   * @Author xukeyan
   * @Description showDirectedGraph
   * @Date 9:25 2024/6/6
   * @Param [graph]
   * @return void
   **/
  public static void showDirectedGraph(@NotNull DirectedGraph graph)
          throws IOException, InterruptedException {
    String dotContent = graph.toDigraphString();
    //System.out.println(dotContent);
    Func.graphvizShow(dotContent);
  }

  /**
   * @Author xukeyan
   * @Description queryBridgeWords
   * @Date 9:25 2024/6/6
   * @Param [word1, word2]
   * @return java.lang.String
   **/
  public static String queryBridgeWords(String word1, String word2) {
    Node node1 = graph.getNode(word1);
    Node node2 = graph.getNode(word2);
    StringJoiner err = new StringJoiner(" and ", "No ", " in the graph!");
    if (node1 == null || node2 == null) {
      if (node1 == null) {
        err.add("\"" + word1 + "\"");
      }
      if (node2 == null) {
        err.add("\"" + word2 + "\"");
      }
      return err.toString();
    }
    List<String> bridgeWords = graph.getBridgeWords(node1, node2);
    if (bridgeWords.size() == 0) {
      return "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
    } else {
      StringBuilder sb = new StringBuilder();
      sb.append("The bridge words from \"")
              .append(word1)
              .append("\" to \"")
              .append(word2)
              .append("\" is: ");
      for (String word : bridgeWords) {
        sb.append(word).append(" ");
      }
      return sb.toString();
    }
  }

  /**
   * @Author xukeyan
   * @Description generateNewText
   * @Date 9:25 2024/6/6
   * @Param [inputText]
   * @return java.lang.String
   **/

  public static String generateNewText(String inputText) {
    String[] words = inputText.split("\\s+");

    StringBuilder newText = new StringBuilder();
    newText.append(words[0]);

    for (int i = 1; i < words.length; i++) {
      String word1 = words[i - 1];
      String word2 = words[i];

      Node node1 = graph.getNode(word1);
      Node node2 = graph.getNode(word2);

      if (node1 != null && node2 != null) {
        List<String> bridgeWords = graph.getBridgeWords(node1, node2);

        if (!bridgeWords.isEmpty()) {
          int randomIndex = secureRandom.nextInt(bridgeWords.size());
          String bridgeWord = bridgeWords.get(randomIndex);

          newText.append(" ").append(bridgeWord);
        }
      }

      newText.append(" ").append(word2);
    }

    return newText.toString();
  }

  /**
   * @Author xukeyan
   * @Description calcShortestPath
   * @Date 9:25 2024/6/6
   * @Param [word1, word2]
   * @return java.lang.String
   **/
  public static String calcShortestPath(String word1, String word2) {
    Node startNode = graph.getNode(word1); // 获取起始节点
    if (startNode == null) { // 如果起始节点不存在
      return "The word \"" + word1 + "\" is not in the graph."; // 返回错误消息
    }

    if (Objects.equals(word2, "")) { // 如果目标词为空
      StringBuilder result = new StringBuilder(); // 创建字符串构建器
      List<Node> nodes = graph.getNodes(); // 获取图中所有节点
      for (Node endNode : nodes) { // 遍历所有节点
        result.append(shortestPath(startNode, endNode, false)).append("\n"); // 调用 shortestPath 方法，并将结果追加到结果字符串中
      }
      return result.toString(); // 返回结果字符串
    }

    Node endNode = graph.getNode(word2); // 获取目标节点
    if (endNode == null) { // 如果目标节点不存在
      return "The word \"" + word2 + "\" is not in the graph."; // 返回错误消息
    }

    return shortestPath(startNode, endNode, true); // 调用 shortestPath 方法，并返回结果
  }

  private static String shortestPath(Node startNode, Node endNode, boolean showGraph) {
    String startWord = startNode.getWord(); // 获取起始词
    String endWord = endNode.getWord(); // 获取目标词

    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance)); // 创建优先队列
    Map<Node, Integer> distances = new HashMap<>(); // 创建节点到起始节点的距离映射
    Map<Node, Node> previousNodes = new HashMap<>(); // 创建节点的前驱节点映射

    for (Node node : graph.getNodes()) { // 遍历图中所有节点
      distances.put(node, Integer.MAX_VALUE); // 将节点到起始节点的距离初始化为无穷大
      previousNodes.put(node, null); // 将节点的前驱节点初始化为空
    }

    distances.put(startNode, 0); // 起始节点到自身的距离为0
    queue.add(startNode); // 将起始节点加入优先队列

    while (!queue.isEmpty()) { // 当优先队列不为空时循环
      Node currentNode = queue.poll(); // 取出优先队列中的当前节点

      if (currentNode.equals(endNode)) { // 如果当前节点是目标节点，则跳出循环
        break;
      }

      for (Node neighbor : graph.getNeighbors(currentNode)) { // 遍历当前节点的邻居节点
        int weight = graph.getEdgeWeight(currentNode, neighbor); // 获取当前节点到邻居节点的边权重
        int distanceThroughCurrent = distances.get(currentNode) + weight; // 计算经过当前节点到达邻居节点的距离

        if (distanceThroughCurrent < distances.get(neighbor)) { // 如果经过当前节点到达邻居节点的距离比已记录的距离小
          distances.put(neighbor, distanceThroughCurrent); // 更新距离映射
          previousNodes.put(neighbor, currentNode); // 更新前驱节点映射
          queue.add(neighbor); // 将邻居节点加入队列
        }
      }
    }

    if (previousNodes.get(endNode) == null) { // 如果目标节点的前驱节点为空，表示无法从起始节点到达目标节点
      return "There is no path from \"" + startWord + "\" to \"" + endWord + "\"."; // 返回错误消息
    }

    List<Node> shortestPath = new ArrayList<>(); // 创建最短路径列表
    Node currentNode = endNode; // 初始化当前节点为目标节点

    while (currentNode != null) { // 循环直到当前节点为空
      shortestPath.add(currentNode); // 将当前节点加入最短路径列表
      currentNode = previousNodes.get(currentNode); // 获取当前节点的前驱节点
    }

    Collections.reverse(shortestPath); // 反转最短路径列表，使其按照起始节点到目标节点的顺序排列

    int totalWeight = 0; // 初始化总权重为0
    StringBuilder pathBuilder = new StringBuilder("Shortest path from \""
            + startWord + "\" to \"" + endWord + "\":"); // 创建路径字符串构建器

    for (int i = 0; i < shortestPath.size(); i++) { // 遍历最短路径列表
      pathBuilder.append(shortestPath.get(i).getWord()); // 将节点的词加入路径字符串

      if (i < shortestPath.size() - 1) { // 如果不是最后一个节点
        int weight = graph.getEdgeWeight(shortestPath.get(i), shortestPath.get(i + 1)); // 获取边的权重
        totalWeight += weight; // 更新总权重
        pathBuilder.append(" → ").append(weight); // 将边的权重加入路径字符串
      }
    }

    pathBuilder.append("\nTotal weight of the path: ").append(totalWeight); // 将总权重加入路径字符串

    if (showGraph) { // 如果需要展示图形
      String dotContent = graph.toDigraphStringPath(shortestPath); // 将最短路径转换为有向图的字符串表示
      try {
        Func.graphvizShow(dotContent); // 使用图形库展示图形
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e); // 捕获可能的异常并抛出运行时异常
      }
    }

    return pathBuilder.toString(); // 返回最短路径的字符串表示
  }
  /**
   * @Author xukeyan
   * @Description randomWalk
   * @Date 9:26 2024/6/6
   * @Param []
   * @return java.lang.String
   **/
  public static String randomWalk() {
    PauseListener pauseListener = new PauseListener();
    // 启动监听线程
    Thread listenerThread = new Thread(pauseListener);
    listenerThread.start();

    // 随机选择一个起始节点
    List<Node> nodes = graph.getNodes();
    int randomIndex = secureRandom.nextInt(nodes.size());
    Node currentNode = nodes.get(randomIndex);

    // 记录遍历的节点和边
    List<Node> visitedNodes = new ArrayList<>();
    StringBuilder visitedEdges = new StringBuilder();

    // 添加起始节点到已访问节点列表
    visitedNodes.add(currentNode);

    System.out.println("Press Enter to pause...");
    while (!pauseListener.isPaused()) {

      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        System.err.println("Thread was interrupted!");
      }


      List<Node> neighbors = graph.getNeighbors(currentNode);
      if (neighbors.isEmpty()) {
        break; // 当前节点没有出边，停止遍历
      }

      // 随机选择一个邻居节点
      randomIndex = secureRandom.nextInt(neighbors.size());
      Node nextNode = neighbors.get(randomIndex);

      // 记录经过的边
      String edge = currentNode.getWord() + " -> " + nextNode.getWord();
      if (visitedEdges.toString().contains(edge)) {
        visitedNodes.add(nextNode);
        visitedEdges.append(edge);
        break;
      }
      //visitedEdges.add(edge);
      visitedEdges.append(edge);

      // 添加下一个节点到已访问节点列表
      visitedNodes.add(nextNode);

      // 更新当前节点为下一个节点
      currentNode = nextNode;
    }

    // 将遍历的节点输出为文本
    StringBuilder resultBuilder = new StringBuilder();
    for (Node node : visitedNodes) {
      resultBuilder.append(node.getWord()).append(" ");
    }

    String result = resultBuilder.toString().trim();
    // 将结果写入磁盘

    try {
      Func.writeToFile("random_walk.txt", result);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }
}
