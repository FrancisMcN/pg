/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.printer;

import ie.francis.pg.term.NonTerminal;
import ie.francis.pg.term.Optional;
import ie.francis.pg.term.Plus;
import ie.francis.pg.term.Star;
import ie.francis.pg.term.Term;
import ie.francis.pg.term.Terminal;

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
