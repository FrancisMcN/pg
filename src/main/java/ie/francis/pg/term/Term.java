/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.term;

import java.util.List;
import java.util.Set;

public interface Term {
  List<Term> expand();

  Set<Term> first();
}
