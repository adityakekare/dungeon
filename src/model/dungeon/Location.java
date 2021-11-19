package model.dungeon;

import java.util.List;

import model.inventory.InventoryType;

/**
 * The interface represents a location in the cave. The location can be either a cave or a tunnel
 * based on the number of paths connected.
 */
public interface Location<T> {

  /**
   * Method for getting the id of the location.
   *
   * @return integer unique id.
   */
  int getId();

  /**
   * Method for getting the connected paths as boolean values from the location.
   *
   * @return Connector object which encapsulates a boolean array for paths connected.
   */
  Connector getRoutes();

  /**
   * Method for checking if the location is a tunnel based on whether there are exactly 2 paths
   * connected to the location.
   *
   * @return boolean value, true if location is tunnel.
   */
  boolean isTunnel();

  boolean contains(InventoryType inventoryType);

  void add(InventoryType inventoryType);

  List<T> get(InventoryType inventoryType);

  void remove(InventoryType inventoryType);
}
