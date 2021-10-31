package dungeon;

import java.util.Objects;

public class Path implements Comparable<Path>{

  private final int source;
  private final int destination;

  public Path(int source, int destination) {
    this.source = Math.min(source, destination);
    this.destination = Math.max(source, destination);
  }

  public int getSource() {
    return source;
  }

  public int getDestination() {
    return destination;
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Path)){
      return false;
    }

    Path otherPath = (Path) o;

    if(otherPath.getSource() == this.getSource() && otherPath.getDestination()
            == this.getDestination()){
      return true;
    }
    else if(otherPath.getSource() == this.getDestination() && otherPath.getDestination()
            == this.getSource()){
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(source + destination);
  }

  @Override
  public int compareTo(Path o) {
    if(this.source < o.source){
      return -1;
    }
    else if(this.source > o.source){
      return 1;
    }
    else{
      if(this.destination < o.destination){
        return -1;
      }
      else if(this.destination > o.destination){
        return 1;
      }
      else{
        return 0;
      }
    }
  }

  @Override
  public String toString() {
    return String.format(source + " => " + destination);
  }
}
