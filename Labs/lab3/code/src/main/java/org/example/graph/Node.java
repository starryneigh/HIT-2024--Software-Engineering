package org.example.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
  String word;
  int id;
  public int distance; // 距离属性

  @Override
  public String toString() {
    return "Node{" + "word='" + word + '\'' + ", id=" + id + '}';
  }

  public Node() {
  }

  public Node(String word, int id) {
    this.word = word;
    this.id = id;
    this.distance = Integer.MAX_VALUE; // 初始化距离为无穷大
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  // 其他方法...
}