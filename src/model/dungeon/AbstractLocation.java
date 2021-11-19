package model.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import model.inventory.Inventory;
import model.inventory.InventoryFactory;
import model.inventory.InventoryType;

/**
 * Abstract class which extends location and implements common methods of the subclasses.
 */
public abstract class AbstractLocation<T> implements Location<T> {

  protected final Map<InventoryType, Inventory<T>> inventory;
  private final int id;
  private final Connector routes;
  protected Set<InventoryType> inventoryAllowed;

  protected AbstractLocation(int id, Connector routes) {
    this.id = id;
    this.routes = routes;
    this.inventory = new HashMap<>();
  }

  @Override
  public void add(InventoryType inventoryType) {
    if (inventoryAllowed.contains(inventoryType)) {
      if (!this.contains(inventoryType)) {
        Inventory<T> newInventory = InventoryFactory.create(inventoryType);
        newInventory.add();
        inventory.put(inventoryType, newInventory);
      } else {
        inventory.get(inventoryType).add();
      }
    } else {
      throw new IllegalArgumentException("Given model.inventory cannot be added to this location");
    }
  }

  @Override
  public List<T> get(InventoryType inventoryType) {
    if (!inventory.containsKey(inventoryType)) {
      throw new NoSuchElementException("Inventory not present");
    }
//    else if(!model.inventory.get(inventoryType).contains()){
//      throw new NoSuchElementException("Inventory not present");
//    }
    return new ArrayList<>(inventory.get(inventoryType).get());
  }

  @Override
  public boolean contains(InventoryType inventoryType) {
    return inventory.containsKey(inventoryType);
  }

  @Override
  public void remove(InventoryType inventoryType) {
    if (!this.contains(inventoryType)) {
      throw new NoSuchElementException("Inventory not present");
    }
    inventory.get(inventoryType).remove();
    inventory.remove(inventoryType);
  }

  public int getId() {
    return id;
  }

  public Connector getRoutes() {
    return routes;
  }
}
