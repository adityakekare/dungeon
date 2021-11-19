package model.inventory;

import java.util.List;

public interface Inventory<T> {

  void add();

  void remove();

  boolean contains();

  List<T> get();
}
