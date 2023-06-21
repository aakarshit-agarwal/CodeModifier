package org.example;

public class Main {
    public static void main(String[] args) {

        QueryStringRemoverVisitor q = new QueryStringRemoverVisitor();

        System.out.println(q.parse());
    }
}

