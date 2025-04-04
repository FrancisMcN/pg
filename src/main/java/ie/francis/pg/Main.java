/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pg;

public class Main {

  public static void main(String[] args) {

    Grammar grammar = new Grammar();

    NonTerminal program = new NonTerminal("program");
    NonTerminal expr = new NonTerminal("expr");
    NonTerminal list = new NonTerminal("list");
    NonTerminal atom = new NonTerminal("atom");

    Terminal epsilon = new Terminal("ε");

    grammar.addProduction(program, new Rule().addTerm(expr));

    grammar
        .addProduction(expr, new Rule().addTerm(list))
        .addProduction(expr, new Rule().addTerm(atom))
        .addProduction(expr, new Rule().addTerm(epsilon));

    grammar.addProduction(list, new Rule().addTerm("(").addTerm(expr).addTerm(")"));

    grammar
        .addProduction(atom, new Rule().addTerm("SYMBOL"))
        .addProduction(atom, new Rule().addTerm("STRING"))
        .addProduction(atom, new Rule().addTerm("INT"))
        .addProduction(atom, new Rule().addTerm("BOOL"));

    grammar.generate();
    JavaPrinter p = new JavaPrinter(grammar);
    System.out.println(p.print());

    //        Generator generator = new Generator();
    //        generator.addProduction("program", "expr");
    //        generator.addProduction("expr", "list", "atom", "EPSILON");
    //        generator.addProduction("list", "( list )");
    //        generator.addProduction("atom", "SYMBOL", "INT", "STR", "BOOL");
    //
    //        System.out.println("--- eps ---");
    //        System.out.println(generator.eps());
    //
    //        System.out.println("--- first ---");
    //        Map<String, Set<String>> first = generator.first();
    //        for (Map.Entry<String, Set<String>> rule : first.entrySet()) {
    //            if (rule.getKey().matches("[A-Z]+")) {
    //                System.out.println(rule.getKey() + " " + rule.getValue());
    //            }
    //        }
    //
    //        System.out.println("--- follow ---");
    //        Map<String, Set<String>> follow = generator.follow();
    //        for (Map.Entry<String, Set<String>> rule : follow.entrySet()) {
    //            System.out.println(rule.getKey() + " " + rule.getValue());
    //        }
    //
    //        System.out.println("--- predict ---");
    //        Map<String, List<Set<String>>> predict = generator.predict();
    //        for (Map.Entry<String, List<Set<String>>> rules : predict.entrySet()) {
    //            for (Set<String> rule : rules.getValue()) {
    //                System.out.println(rules.getKey() + " " + rule);
    //            }
    //        }
    //
    //        System.out.println("--- generated java parser ---");
    //        JavaPrinter printer = new JavaPrinter(generator);
    //        System.out.println(printer.print());

  }
}
