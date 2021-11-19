package model.inventory;

public final class InventoryFactory<T> {

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
