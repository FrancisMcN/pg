/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pgold;

import ie.francis.pgold.printer.JavaPrinter;
import ie.francis.pgold.term.*;

public class Main {
  public static void main(String[] args) {

    NonTerminal program = new NonTerminal("program");
    NonTerminal expr = new NonTerminal("expr");
    NonTerminal list = new NonTerminal("list");
    NonTerminal atom = new NonTerminal("atom");

    Production programProduction = new Production(program).addTerm(new Star().addTerm(expr));

    Production exprProduction1 = new Production(expr).addTerm(list);
    Production exprProduction2 = new Production(expr).addTerm(atom);
    Production exprProduction3 = new Production(expr).addTerm(new Terminal("QUOTE")).addTerm(expr);

    Production listProduction =
        new Production(list)
            .addTerm(new Terminal("LPAREN"))
            .addTerm(new Star().addTerm(expr))
            .addTerm(new Terminal("RPAREN"));

    Production atomProduction1 = new Production(atom).addTerm(new Terminal("SYMBOL"));
    Production atomProduction2 = new Production(atom).addTerm(new Terminal("NUMBER"));
    Production atomProduction3 = new Production(atom).addTerm(new Terminal("BOOLEAN"));
    Production atomProduction4 = new Production(atom).addTerm(new Terminal("STRING"));
    Production atomProduction5 = new Production(atom).addTerm(new Terminal("Ɛ"));

    System.out.println(
        new JavaPrinter(
                programProduction,
                exprProduction1,
                exprProduction2,
                exprProduction3,
                listProduction,
                atomProduction1,
                atomProduction2,
                atomProduction3,
                atomProduction4,
                atomProduction5)
            .print());
  }
}
