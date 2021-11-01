package dungeon;

public class Connector {

  private boolean north;
  private boolean south;
  private boolean west;
  private boolean east;

  public Connector(boolean[] isOpen) {
    this.north = isOpen[0];
    this.south = isOpen[1];
    this.west = isOpen[2];
    this.east = isOpen[3];
  }

  public boolean isNorth() {
    return north;
  }

  public boolean isSouth() {
    return south;
  }

  public boolean isWest() {
    return west;
  }

  public boolean isEast() {
    return east;
  }

  public void setNorth(boolean north) {
    this.north = north;
  }

  public void setSouth(boolean south) {
    this.south = south;
  }

  public void setWest(boolean west) {
    this.west = west;
  }

  public void setEast(boolean east) {
    this.east = east;
  }

  public int getNumRoutes(){
    int count = 0;
    if(this.isEast()){
      count += 1;
    }
    if(this.isNorth()){
      count += 1;
    }
    if(this.isSouth()){
      count += 1;
    }
    if(this.isWest()){
      count += 1;
    }

    return count;
  }

  @Override
  public String toString() {
    String result = "";
    if(this.north){
      result += "North";
    }
    if(this.south){
      result += ", South";
    }
    if(this.east){
      result += ", East";
    }
    if(this.west){
      result += ", West";
    }

    return result;
  }
}
