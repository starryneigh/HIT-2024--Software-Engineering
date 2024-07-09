package org.example.graph;

import java.util.*;

/**
 * 有向图类
 */
public class DirectedGraph {
  ArrayList<Node> nodes;  // 存储图中的节点
  HashMap<String, Node> wordToNodeMap;  // 将单词映射到节点的哈希映射
  HashMap<Integer, String> idToWordMap;  // 将节点ID映射到单词的哈希映射
  int[][] adjacencyMatrix;  // 存储图的邻接矩阵
  int nextId = 0;  // 下一个节点的ID

  public Node getNode(String word) {
    return wordToNodeMap.get(word);  // 根据单词获取对应的节点
  }

  public String getWord(int id) {
    return idToWordMap.get(id);  // 根据节点ID获取对应的单词
  }

  /**
   * @Author xukeyan
   * @Description DirectedGraph
   * @Date 10:32 2024/6/6
   * @Param []
   * @return void
   **/
  public DirectedGraph() {
    nodes = new ArrayList<>();  // 初始化节点列表
    wordToNodeMap = new HashMap<>();  // 初始化单词到节点的映射
    idToWordMap = new HashMap<>();  // 初始化节点ID到单词的映射
    nextId = 0;  // 初始化下一个节点的ID
  }

  /**
   * @Author xukeyan
   * @Description addNode
   * @Date 10:32 2024/6/6
   * @Param [word]
   * @return void
   **/
  public void addNode(String word) {
    if (!wordToNodeMap.containsKey(word)) {
      Node newNode = new Node(word, nextId);  // 创建新的节点
      nodes.add(newNode);  // 将节点添加到节点列表中
      wordToNodeMap.put(word, newNode);  // 将单词映射到节点
      idToWordMap.put(nextId, word);  // 将节点ID映射到单词
      nextId++;  // 增加下一个节点的ID
    }
  }

  /**
   * @Author xukeyan
   * @Description addEdge
   * @Date 10:33 2024/6/6
   * @Param [source, destination]
   * @return void
   **/
  public void addEdge(String source, String destination) {
    Node sourceNode = wordToNodeMap.get(source);  // 获取源节点
    Node destNode = wordToNodeMap.get(destination);  // 获取目标节点
    adjacencyMatrix[sourceNode.id][destNode.id] += 1;  // 在邻接矩阵中增加边的权重
  }

  /**
   * @Author xukeyan
   * @Description buildAdjacencyMatrix
   * @Date 10:33 2024/6/6
   * @Param []
   * @return void
   **/
  public void buildAdjacencyMatrix() {
    int size = nodes.size();  // 获取节点列表的大小
    adjacencyMatrix = new int[size][size];  // 初始化邻接矩阵

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        adjacencyMatrix[i][j] = 0;  // 将邻接矩阵中的元素初始化为0
      }
    }
  }

  /**
   * @Author xukeyan
   * @Description toString
   * @Date 10:33 2024/6/6
   * @Param []
   * @return java.lang.String
   **/
  public String toString() {
    StringBuilder sb = new StringBuilder("Graph: {\n");  // 创建StringBuilder对象，用于构建字符串表示
    for (int i = 0; i < nodes.size(); i++) {
      for (int j = 0; j < nodes.size(); j++) {
        // 判断节点i与节点j之间是否存在边
        if (adjacencyMatrix[i][j] > 0) {
          sb.append(nodes.get(i).word).append(" -> ");  // 添加起始节点的单词
          // 构建边的表示，包括目标节点的单词和边的权重
          String temp = nodes.get(j).word + "(" + adjacencyMatrix[i][j] + ")";
          sb.append(temp).append("\n");  // 将边的表示添加到StringBuilder中
        }
      }
    }
    sb.append("}");  // 添加表示图结束的标记
    return sb.toString();  // 将StringBuilder转换为字符串并返回
  }

  /**
   * @Author xukeyan
   * @Description toDigraphString
   * @Date 10:34 2024/6/6
   * @Param []
   * @return java.lang.String
   **/
  public String toDigraphString() {
    StringBuilder sb = new StringBuilder();
    sb.append("digraph G {\n");  // 创建StringBuilder对象，用于构建有向图的字符串表示
    for (int i = 0; i < nodes.size(); i++) {
      String preNode = nodes.get(i).word;  // 获取前驱节点的单词
      for (int j = 0; j < nodes.size(); j++) {
        // 判断节点i到节点j之间是否存在边
        if (adjacencyMatrix[i][j] > 0) {
          String nextNode = nodes.get(j).word;  // 获取后继节点的单词
          // 构建有向边的表示，包括起始节点、目标节点和边的权重
          String temp = preNode + " -> " + nextNode
                  + "[label=\"" + adjacencyMatrix[i][j] + "\"];";
          sb.append(temp).append("\n");  // 将有向边的表示添加到StringBuilder中
        }
      }
    }
    sb.append("}\n");  // 添加表示有向图结束的标记
    return sb.toString();  // 将StringBuilder转换为字符串并返回
  }

  public List<String> getBridgeWords(Node node1, Node node2) {
    List<String> bridgeNodes = new ArrayList<>();  // 存储桥接词的列表

    // 遍历所有节点
    for (int i = 0; i < nodes.size(); i++) {
      // 判断节点i与node1、node2之间是否存在边
      if (adjacencyMatrix[node1.id][i] > 0 && adjacencyMatrix[i][node2.id] > 0) {
        bridgeNodes.add(nodes.get(i).word);  // 将节点i对应的单词添加到桥接词列表中
      }
    }
    return bridgeNodes;  // 返回桥接词列表
  }

  /**
   * @Author xukeyan
   * @Description getNeighbors
   * @Date 10:34 2024/6/6
   * @Param [text]
   * @return java.lang.String
   **/
  public List<Node> getNeighbors(Node node) {
    List<Node> neighbors = new ArrayList<>();
    int nodeId = node.getId();

    for (int i = 0; i < adjacencyMatrix.length; i++) {
      if (adjacencyMatrix[nodeId][i] > 0) {
        neighbors.add(nodes.get(i));
      }
    }

    return neighbors;
  }

  public List<Node> getNodes() {
    return Collections.unmodifiableList(nodes);  // 返回图的所有节点
  }

  /**
   * @Author xukeyan
   * @Description generateNewText
   * @Date 10:34 2024/6/6
   * @Param [shortestPath]
   * @return java.lang.String
   **/
  public String toDigraphStringPath(List<Node> shortestPath) {
    int size = nodes.size();  // 获取节点列表的大小
    int[][] pathMatrix = new int[size][size];  // 初始化路径矩阵
    int preId = -1;
    int nextId = -1;
    for (int i = 0; i < shortestPath.size() - 1; i++) {
      preId = shortestPath.get(i).id;
      nextId = shortestPath.get(i + 1).id;
      pathMatrix[preId][nextId] = 1;
    }

    StringBuilder sb = new StringBuilder();
    sb.append("digraph G {\n");  // 创建StringBuilder对象，用于构建有向图的字符串表示
    for (int i = 0; i < nodes.size(); i++) {
      String preNode = nodes.get(i).word;  // 获取前驱节点的单词
      for (int j = 0; j < nodes.size(); j++) {
        // 判断节点i到节点j之间是否存在边
        if (adjacencyMatrix[i][j] > 0) {
          String attr = " [label=\"" + adjacencyMatrix[i][j] + "\"";
          if (pathMatrix[i][j] == 1) {
            attr += ", color=red";
          }
          attr += "];";
          String nextNode = nodes.get(j).word;  // 获取后继节点的单词
          String temp = preNode + " -> " + nextNode + attr;  // 构建有向边的表示，包括起始节点、目标节点和边的权重
          sb.append(temp).append("\n");  // 将有向边的表示添加到StringBuilder中
        }
      }
    }
    sb.append("}\n");  // 添加表示有向图结束的标记
    return sb.toString();  // 将StringBuilder转换为字符串并返回
  }

  /**
   * @Author xukeyan
   * @Description getEdgeWeight
   * @Date 10:34 2024/6/6
   * @Param [shortestPath]
   * @return java.lang.String
   **/
  public int getEdgeWeight(Node source, Node destination) {
    return adjacencyMatrix[source.id][destination.id];
  }
}
