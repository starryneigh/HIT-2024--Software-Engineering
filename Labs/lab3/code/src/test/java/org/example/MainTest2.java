package org.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.example.util.Func;

import java.util.List;


public class MainTest2 {
//test2
    @Test
    public void test1() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String word1 = "gjh";
        String word2 = "explore";
        String expected = "The word \"gjh\" is not in the graph.";
        String result =  org.example.Main.calcShortestPath(word1, word2);
        assertEquals(expected, result);    }
    @Test
    public void test2() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String word1 = "to";
        String word2 = "";
        String expected = "There is no path from \"to\" to \"gjhgjh\".\n" +
                "There is no path from \"to\" to \"to\".\n" +
                "Shortest path from \"to\" to \"explore\":to → 1explore\n" +
                "Total weight of the path: 1\n" +
                "Shortest path from \"to\" to \"strange\":to → 1explore → 1strange\n" +
                "Total weight of the path: 2\n" +
                "Shortest path from \"to\" to \"new\":to → 1explore → 1strange → 1new\n" +
                "Total weight of the path: 3\n" +
                "Shortest path from \"to\" to \"worlds\":to → 1explore → 1strange → 1new → 1worlds\n" +
                "Total weight of the path: 4\n" +
                "Shortest path from \"to\" to \"seek\":to → 1seek\n" +
                "Total weight of the path: 1\n" +
                "Shortest path from \"to\" to \"out\":to → 1seek → 1out\n" +
                "Total weight of the path: 2\n" +
                "Shortest path from \"to\" to \"life\":to → 1explore → 1strange → 1new → 1life\n" +
                "Total weight of the path: 4\n" +
                "Shortest path from \"to\" to \"and\":to → 1explore → 1strange → 1new → 1life → 1and\n" +
                "Total weight of the path: 5\n" +
                "Shortest path from \"to\" to \"civilizations\":to → 1explore → 1strange → 1new → 1civilizations\n" +
                "Total weight of the path: 4\n" +
                "Shortest path from \"to\" to \"boldly\":to → 2boldly\n" +
                "Total weight of the path: 2\n" +
                "Shortest path from \"to\" to \"go\":to → 2boldly → 2go\n" +
                "Total weight of the path: 4\n" +
                "Shortest path from \"to\" to \"where\":to → 2boldly → 2go → 2where\n" +
                "Total weight of the path: 6\n" +
                "Shortest path from \"to\" to \"no\":to → 2boldly → 2go → 2where → 2no\n" +
                "Total weight of the path: 8\n" +
                "Shortest path from \"to\" to \"one\":to → 2boldly → 2go → 2where → 2no → 2one\n" +
                "Total weight of the path: 10\n" +
                "Shortest path from \"to\" to \"has\":to → 2boldly → 2go → 2where → 2no → 2one → 2has\n" +
                "Total weight of the path: 12\n" +
                "Shortest path from \"to\" to \"gone\":to → 2boldly → 2go → 2where → 2no → 2one → 2has → 2gone\n" +
                "Total weight of the path: 14\n" +
                "Shortest path from \"to\" to \"before\":to → 2boldly → 2go → 2where → 2no → 2one → 2has → 2gone → 2before\n" +
                "Total weight of the path: 16\n" +
                "Shortest path from \"to\" to \"dream\":to → 1dream\n" +
                "Total weight of the path: 1\n" +
                "Shortest path from \"to\" to \"big\":to → 1dream → 1big\n" +
                "Total weight of the path: 2\n" +
                "Shortest path from \"to\" to \"dreams\":to → 1dream → 1big → 1dreams\n" +
                "Total weight of the path: 3\n" +
                "Shortest path from \"to\" to \"chase\":to → 1chase\n" +
                "Total weight of the path: 1\n" +
                "Shortest path from \"to\" to \"after\":to → 1chase → 1after\n" +
                "Total weight of the path: 2\n" +
                "Shortest path from \"to\" to \"the\":to → 1exercise → 1the\n" +
                "Total weight of the path: 2\n" +
                "Shortest path from \"to\" to \"unknown\":to → 1exercise → 1the → 2unknown\n" +
                "Total weight of the path: 4\n" +
                "Shortest path from \"to\" to \"discover\":to → 1discover\n" +
                "Total weight of the path: 1\n" +
                "Shortest path from \"to\" to \"mysteries\":to → 1exercise → 1the → 1mysteries\n" +
                "Total weight of the path: 3\n" +
                "Shortest path from \"to\" to \"of\":to → 1exercise → 1the → 1mysteries → 1of\n" +
                "Total weight of the path: 4\n" +
                "Shortest path from \"to\" to \"universe\":to → 1exercise → 1the → 1universe\n" +
                "Total weight of the path: 3\n" +
                "Shortest path from \"to\" to \"exercise\":to → 1exercise\n" +
                "Total weight of the path: 1\n" +
                "Shortest path from \"to\" to \"imagination\":to → 1exercise → 1the → 1imagination\n" +
                "Total weight of the path: 3\n" +
                "Shortest path from \"to\" to \"expand\":to → 1exercise → 1the → 1imagination → 1expand\n" +
                "Total weight of the path: 4\n" +
                "Shortest path from \"to\" to \"consciousness\":to → 1exercise → 1the → 1imagination → 1expand → 1consciousness\n" +
                "Total weight of the path: 5\n" +
                "Shortest path from \"to\" to \"embrace\":to → 1explore → 1strange → 1new → 1life → 1and → 1embrace\n" +
                "Total weight of the path: 6\n";
        String result =  org.example.Main.calcShortestPath(word1, word2);
        assertEquals(expected, result);    }
    @Test
    public void test3() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String word1 = "to";
        String word2 = "gjh";
        String expected = "The word \"gjh\" is not in the graph.";
        String result =  org.example.Main.calcShortestPath(word1, word2);
        assertEquals(expected, result);    }
    @Test
    public void test4() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String word1 = "to";
        String word2 = "gjhgjh";
        String expected = "There is no path from \"to\" to \"gjhgjh\".";
        String result =  org.example.Main.calcShortestPath(word1, word2);
        assertEquals(expected, result);    }
    @Test
    public void test5() {
        Main main = new Main();
        String str = Func.getStr("1.txt");
        List<String> wordList = Func.getWords(str);
        main.graph = Func.buildDirectedGraph(wordList);
        String word1 = "to";
        String word2 = "strange";
        String expected = "Shortest path from \"to\" to \"strange\":to → 1explore → 1strange\nTotal weight of the path: 2";

        String result =  org.example.Main.calcShortestPath(word1, word2);
        assertEquals(expected, result);    }


}