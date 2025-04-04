/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pgold.term;

import java.util.List;
import java.util.Set;

public interface Term {
  List<Term> expand();

  Set<Term> first();
}
