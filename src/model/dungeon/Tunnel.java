package model.dungeon;

import java.util.HashSet;
import java.util.Set;

import model.inventory.InventoryType;

/**
 * Class for representing a location which doesn't contain treasures and has 2 connecting paths.
 */
class Tunnel<T> extends AbstractLocation<T> {

  protected Tunnel(int id, Connector routes) {
    super(id, routes);
    inventoryAllowed = initializeInventoryAllowed();
  }

  @Override
  public boolean isTunnel() {
    return true;
  }

  @Override
  public String toString() {
    if (this.contains(InventoryType.WEAPON)) {
      return "You are in a tunnel.\n" + "Collectibles - " + "Arrows: "
              + inventory.get(InventoryType.WEAPON).get().size() + "\nThe tunnel continues from ";
    }
    return "You are in a tunnel which continues from ";
  }

  private Set<InventoryType> initializeInventoryAllowed() {
    Set<InventoryType> inventory = new HashSet<>();
    inventory.add(InventoryType.WEAPON);

    return inventory;
  }
}
