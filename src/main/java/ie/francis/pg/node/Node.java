/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.node;

public interface Node {
  String print(int depth);

  String code();

  String name();
}
