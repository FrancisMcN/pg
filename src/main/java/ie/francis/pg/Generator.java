/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pg;

import static ie.francis.pg.StringUtil.isNonTerminal;
import static ie.francis.pg.StringUtil.isTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Generator {

  public Map<String, List<String>> grammar = new HashMap<>();

  public Generator() {}

  public void addProduction(String nonTerminal, String... productions) {
    grammar.computeIfAbsent(nonTerminal, k -> new ArrayList<>()).addAll(Arrays.asList(productions));
  }

  public Set<String> eps() {
    Set<String> eps = new HashSet<>();

    // Initialise EPS with every non-terminal where N -> ε
    for (String nonTerminal : grammar.keySet()) {
      if (grammar.get(nonTerminal).contains("EPSILON")) {
        eps.add(nonTerminal);
      }
    }

    boolean changed;
    do {
      changed = false;
      // Loop through all non-terminals
      for (String nonTerminal : grammar.keySet()) {
        // Get all productions for each non-terminal
        List<String> productions = grammar.get(nonTerminal);
        // Loop through all productions for each non-terminal
        for (String production : productions) {
          // Split each production based on spaces, allows each terminal/non-terminal
          // to be examined
          List<String> rule = List.of(production.split(" "));
          // If all terms in a rule are in EPS, then add the corresponding non-terminal
          // to EPS
          if (eps.containsAll(rule)) {
            if (!eps.contains(nonTerminal)) {
              eps.add(nonTerminal);
              changed = true;
            }
          }
        }
      }

    } while (changed);

    return eps;
  }

  public Map<String, Set<String>> first() {

    Map<String, Set<String>> first = new HashMap<>();
    Set<String> eps = eps();

    // Initialise first sets
    // Create a set FIRST(x) = {x} for every terminal in the grammar
    for (String nonTerminal : grammar.keySet()) {
      for (String production : grammar.get(nonTerminal)) {
        for (String term : List.of(production.split(" "))) {
          // If string is all-lowercase then it must be a terminal
          if (isTerminal(term)) {
            Set<String> set = new HashSet<>();
            set.add(term);
            first.putIfAbsent(term, set);
          }
        }
      }
    }

    // Create a set FIRST(X) = {} for every non-terminal in the grammar
    for (String nonTerminal : grammar.keySet()) {
      first.putIfAbsent(nonTerminal, new HashSet<>());
    }

    boolean changed;
    do {
      changed = false;
      for (String nonTerminal : grammar.keySet()) {
        for (String production : grammar.get(nonTerminal)) {
          for (String term : List.of(production.split(" "))) {
            if (!first.get(nonTerminal).containsAll(first.get(term))) {
              first.get(nonTerminal).addAll(first.get(term));
              changed = true;
            }
            if (!eps.contains(term)) {
              break;
            }
          }
        }
      }

    } while (changed);

    return first;
  }

  public Map<String, Set<String>> follow() {

    Map<String, Set<String>> follow = new HashMap<>();
    Map<String, Set<String>> first = first();
    Set<String> eps = eps();

    // Create a set FOLLOW(X) = {} for every non-terminal in the grammar
    for (String nonTerminal : grammar.keySet()) {
      follow.putIfAbsent(nonTerminal, new HashSet<>());
    }

    boolean changed;
    do {
      changed = false;
      for (String nonTerminal : grammar.keySet()) {
        for (String production : grammar.get(nonTerminal)) {
          List<String> terms = List.of(production.split(" "));
          for (int i = 0; i < terms.size() - 1; i++) {
            String term = terms.get(i);
            // if Yi is a non-terminal then FOLLOW(Yi) := FOLLOW(Yi) ∪ first(Yi+1Yi+2⋯Yk )
            if (isNonTerminal(term)) {
              for (int j = i + 1; j < terms.size(); j++) {
                if (!follow.get(term).containsAll(first.get(terms.get(j)))) {
                  follow.get(term).addAll(first.get(terms.get(j)));
                  changed = true;
                }
              }
            }
          }

          for (int i = terms.size() - 1; i >= 0; i--) {
            String term = terms.get(i);
            if (isNonTerminal(term)) {
              // if Yi is a non-terminal FOLLOW(Yi) := FOLLOW(Yi) ∪ FOLLOW(X)
              if (!follow.get(term).containsAll(follow.get(nonTerminal))) {
                follow.get(term).addAll(follow.get(nonTerminal));
                changed = true;
              }
            }
            if (!eps.contains(term)) {
              break;
            }
          }
        }
      }

    } while (changed);

    return follow;
  }

  public Map<String, List<Set<String>>> predict() {

    Set<String> eps = eps();
    Map<String, Set<String>> first = first();
    Map<String, Set<String>> follow = follow();
    Map<String, List<Set<String>>> predict = new HashMap<>();

    // PREDICT(X→w ) := first(w ) if eps(w)=false , and first(w ) ∪ FOLLOW(X ) otherwise
    for (String nonTerminal : grammar.keySet()) {
      for (String production : grammar.get(nonTerminal)) {
        List<String> terms = List.of(production.split(" "));
        Set<String> set = new HashSet<>();
        String term = terms.get(0);
        if (!term.equals("EPSILON")) {
          if (!eps.contains(term)) {
            set.addAll(first.get(term));
          } else {
            set.addAll(first.get(term));
            set.addAll(follow.get(nonTerminal));
          }
          set.remove("EPSILON");
          predict.putIfAbsent(nonTerminal, new ArrayList<>());
          predict.get(nonTerminal).add(set);
        }
      }
    }

    return predict;
  }
}
