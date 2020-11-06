// --== CS400 File Header Information ==--
// Name: Xiaochen Fan
// Email: xfan72@wisc.edu
// Team: NC
// Back End Developer
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;


/**
 * 
 * A map class that has the ability to do Dijkstra's algorithm
 *
 */
public class USmap {

  private CS400Graph<State> US_MAP;

  public class State {
    private String name; // name of the state

    public State(String name) {
      this.name = name;
    }
  }

  protected Hashtable<String, State> states; // to store states that have been added into the map

  public USmap() {
    US_MAP = new CS400Graph<>();
    states = new Hashtable<>();
  }



  /**
   * Adding a State into the map with its neighbours and the distance to its neighbours
   * 
   * @param s is the State added in
   * @return true if it can be added, false otherwise;
   */
  public void addState(String name) {
    if (name == null) {
      throw new NullPointerException("the State is null");
    }
    if (states.containsKey(name)) {
      System.out.println("This State is already in the map");
      return;
    }
    State st = new State(name);
    US_MAP.insertVertex(st);

    states.put(name, st);

    // if (s == null) {
    // throw new NullPointerException("the State is null");
    // }
    // if (states.contains(s)) { // return false if the State is already in the map
    // return false;
    // }
    // US_MAP.insertVertex(s);
    // for (Neighb n : s.neighbours) {
    // US_MAP.insertEdge(s, s.neighbours.poll().target, n.distance);
    // }
    // states.put(s.name, s);
    // return true;
  }

  /**
   * Give a neighbour relation to two states
   *  
   * @param source is the name of state
   * @param target is the name of the other
   * @param distance is the distance between two states
   */
  public void addDis(String source, String target, int distance) {
    US_MAP.insertEdge(states.get(source), states.get(target), distance);
  }


  /**
   * Delete one state from the map
   * 
   * @param s is the name of state
   */
  public void removeState(String s) {
    US_MAP.removeVertex(states.get(s));
    // if (s == null)
    // throw new NullPointerException("the State is null");
    // State rState = states.get(s);
    // if (rState == null)
    // return false;
    // for (State st : states.values()) {
    // Neighb rNeighb = null;
    // for (Neighb n : st.neighbours)
    // if (n.target == rState)
    // rNeighb = n;
    // if (rNeighb != null)
    // st.neighbours.remove(rNeighb);
    // }
    // return states.remove(s) != null;
  }

  /**
   * Cancle the neighbour relation between two states
   * 
   * @param source is the name of state
   * @param target is the name of the other
   */
  public void removeNeighb(String source, String target) {
    US_MAP.removeEdge(states.get(source), states.get(target));
  }


  /**
   * Check if the state is in the map
   * 
   * @param s is the name of state
   * @return true if the state is found in the map, false otherwise
   */
  public boolean containState(String s) {
    if (s == null)
      throw new NullPointerException("Cannot contain null data vertex");
    return states.containsKey(s);
  }

  /**
   * Check if the two states are neighbours
   * 
   * @param source is name of one state 
   * @param target is name of the other
   * @return true if they are neighbours, and false otherwise
   */
  public boolean isNeighb(String source, String target) {
    return US_MAP.containsEdge(states.get(source), states.get(target));
  }

  /**
   * read the file with state information and build a map
   * 
   * @param fileN is the file.txt being read
   */
  public void addFile(String fileN) {
    try {
      Scanner sc = new Scanner(new File(fileN));
      Scanner sc2;
      while (sc.hasNext()) {
        sc2 = new Scanner(sc.nextLine());
        String name = sc2.next();
        this.addState(name);
        if (name.equals("")) {
          break;
        }

        while (sc2.hasNext()) {
          String nextS = sc2.next();
          if (sc2.hasNext()) {
            int nextD = Integer.valueOf(sc2.next());
            this.addState(nextS);
            this.addDis(name, nextS, nextD);
            this.addDis(nextS, name, nextD);
          }
        }
      }
      System.out.println("File " + "\"" + fileN + "\"" + " added");
    } catch (FileNotFoundException fnf) {
      System.out.println("File doesn't exist");
      System.out.println("File " + fileN + " adding failed");
    } catch (NoSuchElementException | NumberFormatException e) {
      e.printStackTrace();
      System.out.println("Your file contents are not in the right format");
      System.out.println("File " + fileN + " adding failed");
    }
  }

  /**
   * Calc the shortest path from one state to the other
   * 
   * @param start is the name of state where the path starts
   * @param end is the name of state where the path ends
   * @return the total distance as an integer
   */
  public int getShortPath(String start, String end) {
    return US_MAP.getPathCost(states.get(start), states.get(end));

  }

  /**
   * Print out the shortest path from one state to the other in order
   * 
   * @param start is the state where the path starts
   * @param end is the state where the path ends
   * @return a ordered List which shows the path 
   */
  public List<State> shortestPath(String start, String end) {
    return US_MAP.shortestPath(states.get(start), states.get(end));
  }

  /**
   * Check if the map is empty
   * 
   * @return true if it is empty, and false otherwise
   */
  public boolean isEmpty() {
    return states.size() == 0;
  }
  
  /**
   * Clears the map
   *
   */
  public void clear() {
    this.US_MAP = new CS400Graph<>();
  }
}


