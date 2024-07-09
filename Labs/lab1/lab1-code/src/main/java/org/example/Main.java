package org.example;

public class Main {
    public static void main(String[] args) {
        // 获取当前类的类加载器
        ClassLoader classLoader = Main.class.getClassLoader();

        // 使用类加载器获取资源文件的 URL
        // 这里假设资源文件名为 "example.txt"
        String resourceName = "1.txt";
        java.net.URL resourceUrl = classLoader.getResource(resourceName);

        if (resourceUrl != null) {
            // 打印资源文件的路径
            System.out.println("资源文件路径: " + resourceUrl.getPath());
        } else {
            System.out.println("未找到资源文件");
        }
    }
}