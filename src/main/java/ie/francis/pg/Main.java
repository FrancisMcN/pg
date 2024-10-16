/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg;

import ie.francis.pg.node.*;

public class Main {
  public static void main(String[] args) {
    GrammarNode grammar =
        new GrammarNode()
            .setCode(
                "private Stack<Object> stack;\n\npublic Parser() { this.stack = new Stack<>(); }")
            .addNode(
                new NonTerminalNode("expr")
                    .addNodeToBody(
                        new PlusNode()
                            .addNode(
                                new TerminalNode("Symbol")
                                    .setCode("System.out.println(\"hello world\");"))))
            .addNode(
                new NonTerminalNode("optional")
                    .addNodeToBody(
                        new OptionalNode()
                            .addNode(
                                new TerminalNode("Boolean")
                                    .setCode("System.out.println(\"hello world\");"))
                            .addNode(
                                new TerminalNode("Float")
                                    .setCode("System.out.println(\"hello world\");")))
                    .addNodeToBody(
                        new OptionalNode()
                            .addNode(
                                new TerminalNode("Quote")
                                    .setCode("System.out.println(\"hello world\");"))))
            .addNode(
                new NonTerminalNode("list")
                    .addNodeToBody(
                        new StarNode()
                            .addNode(
                                new TerminalNode("Number")
                                    .setCode("System.out.println(\"hello world\");"))
                            .addNode(
                                new TerminalNode("String")
                                    .setCode("System.out.println(\"hello world\");"))));

    System.out.println(grammar.print(0));
  }
}
