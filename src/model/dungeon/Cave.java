package model.dungeon;

import java.util.HashSet;
import java.util.Set;

import model.inventory.InventoryType;

/**
 * This class represents a location in the model.dungeon which has either 1, 3 or 4 paths connected
 * to it, and it can hold treasures.
 */
class Cave<T> extends AbstractLocation<T> {

  /**
   * Constructor takes in id of the location and Connector object for paths.
   *
   * @param id     location id.
   * @param routes Connector object.
   */
  public Cave(int id, Connector routes) {
    super(id, routes);
    inventoryAllowed = initializeInventoryAllowed();
  }

  @Override
  public boolean isTunnel() {
    return false;
  }

  @Override
  public String toString() {
    if (this.contains(InventoryType.TREASURE) && this.contains(InventoryType.WEAPON)) {
      return "You are in a cave.\n" + "Collectibles - " + "Treasure: "
              + inventory.get(InventoryType.TREASURE).get().toString()
              .substring(1, (inventory.get(InventoryType.TREASURE)
                      .get().toString().length() - 1))
              + "  Arrows: " + inventory.get(InventoryType.WEAPON).get().size()
              + "\nDoors lead to the ";
    } else if (this.contains(InventoryType.TREASURE)) {
      return "You are in a cave.\n" + "Collectibles - " + "Treasure: "
              + inventory.get(InventoryType.TREASURE).get().toString()
              .substring(1, (inventory.get(InventoryType.TREASURE).get()
                      .toString().length() - 1)) + "\nDoors lead to the ";
    } else if (this.contains(InventoryType.WEAPON)) {
      return "You are in a cave.\n" + "Collectibles - "
              + "Arrows: " + inventory.get(InventoryType.WEAPON).get().size()
              + "\nDoors lead to the ";
    } else {
      return "You are in a cave.\nDoors lead to the ";
    }
  }

  private Set<InventoryType> initializeInventoryAllowed() {
    Set<InventoryType> inventory = new HashSet<>();
    inventory.add(InventoryType.TREASURE);
    inventory.add(InventoryType.MONSTER);
    inventory.add(InventoryType.WEAPON);

    return inventory;
  }
}
