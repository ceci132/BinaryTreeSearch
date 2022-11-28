package com.binaryTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

class Node {
    int parentId;
    int id;
    int data;
    ArrayList<Node> children = new ArrayList<>();

    Node(int id, int parentId, int data) {
        this.id = id;
        this.parentId = parentId;
        this.data = data;
    }

    void addChild(Node child) {
        this.children.add(child);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            PrintStream outputFile = new PrintStream("./out.txt");
            BufferedReader reader = new BufferedReader(new FileReader("./treedata.txt"));

            HashMap<Integer, Node> nodes = new HashMap<>();

            String line = reader.readLine();
            Node root = destruct(line);
            nodes.put(root.id, root);

            while ((line = reader.readLine()) != null) {
                Node data = destruct(line);
                nodes.put(data.id, data);
                nodes.get(data.parentId).addChild(data);
            }

            rec(root, 0, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Node destruct(String nodeData) {
        String[] data = nodeData.split(",");
        return new Node(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    static void rec(Node node, int depth, PrintStream ps) {
        if (!node.children.isEmpty()) {
            if (node.children.stream().anyMatch(node1 -> !node1.children.isEmpty())) {
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    rec(node.children.get(i), depth + 1, ps);
                }
            } else {
                ps.println(node.id + "," + depth);
            }
        }
    }
}