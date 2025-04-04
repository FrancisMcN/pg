/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pgold.printer;

import ie.francis.pgold.term.NonTerminal;
import ie.francis.pgold.term.Optional;
import ie.francis.pgold.term.Plus;
import ie.francis.pgold.term.Star;
import ie.francis.pgold.term.Term;
import ie.francis.pgold.term.Terminal;

public interface Printer {
  String print();

  String print(Optional optional);

  String print(Plus plus);

  String print(Star star);

  String print(Terminal terminal);

  String print(NonTerminal nonTerminal);

  String printNonTerminalBody();

  String print(Term term);
}
