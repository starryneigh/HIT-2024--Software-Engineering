package org.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.example.util.Func;

import java.util.List;

public class MainTest {


    @Test
    public void testValidBridgeWordsExist() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String result = main.queryBridgeWords("to", "strange");
        assertEquals("The bridge words from \"to\" to \"strange\" is: explore ", result);
    }

    @Test
    public void testNoBridgeWordsExist() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String result = org.example.Main.queryBridgeWords("boldly", "before");
        assertEquals("No bridge words from \"boldly\" to \"before\"!", result);
    }
    @Test
    public void testWordsEqual() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String result = org.example.Main.queryBridgeWords("out", "out");
        assertEquals("No bridge words from \"out\" to \"out\"!", result);
    }
    @Test
    public void testWordsConnect() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String result = org.example.Main.queryBridgeWords("to", "boldly");
        assertEquals("No bridge words from \"to\" to \"boldly\"!", result);
    }


    @Test
    public void testWord1DoesNotExist() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String result = org.example.Main.queryBridgeWords("gjh", "to");
        assertEquals("No \"gjh\" in the graph!", result);
    }
    @Test
    public void testWordsDoesNotExist() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String result = org.example.Main.queryBridgeWords("hello", "world");
        assertEquals("No \"hello\" and \"world\" in the graph!", result);
    }
    @Test
    public void testWordsNull() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String result = org.example.Main.queryBridgeWords("world", " ");
        assertEquals("No \"world\" and \" \" in the graph!", result);
    }


}