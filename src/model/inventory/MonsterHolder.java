package model.inventory;

import java.util.ArrayList;
import java.util.List;

public class MonsterHolder implements Inventory {

  private final List<Monster> monsters;

  public MonsterHolder() {
    this.monsters = new ArrayList<>(1);
  }

  @Override
  public void add() {
    if (!this.contains()) {
      this.monsters.add(new Monster());
    } else {
      throw new IllegalStateException("Monster already present");
    }
  }

  @Override
  public void remove() {
    this.monsters.clear();
  }

  @Override
  public boolean contains() {
    return !monsters.isEmpty();
  }

  @Override
  public List<Monster> get() {
    return new ArrayList<>(monsters);
  }
}
