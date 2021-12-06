package model.inventory;

import java.util.List;

/**
 * Interface for implementing a contained for types of inventory.
 *
 * @param <T> Generic type of inventory.
 */
public interface Inventory<T> {

  /**
   * Method to add inventory to the container.
   */
  void add();

  /**
   * Method to remove inventory from the container.
   */
  void remove();

  /**
   * Method to check whether the inventory is present.
   *
   * @return true if inventory is present.
   */
  boolean contains();

  /**
   * Method to get the list of inventory.
   *
   * @return list of inventory.
   */
  List<T> get();
}
