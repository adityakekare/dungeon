package model.inventory;

/**
 * Factory method to create instances of inventory type holder classes. Contains a static method.
 *
 * @param <T> Generic type of inventory.
 */
public final class InventoryFactory<T> {

  /**
   * Static method for creating an instance of the given inventory type holder.
   *
   * @param inventoryType type of inventory required.
   * @return instance of given inventory type's holder.
   */
  public static Inventory create(InventoryType inventoryType) {
    switch (inventoryType.name()) {
      case "MONSTER":
        return new MonsterHolder();
      case "TREASURE":
        return new TreasureHolder();
      case "WEAPON":
        return new WeaponHolder();
      default:
        return null;
    }
  }
}
