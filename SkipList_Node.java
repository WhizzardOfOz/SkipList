package SKPLIST_A4;

public class SkipList_Node {
  private double value;
  private int level;
  private SkipList_Node[] next;
  private SkipList_Node[] last;

	public SkipList_Node(double value, int height) {
		next = new SkipList_Node[height]; // IS THIS ACTUALLY THE SENTINEL?? // I THINK THIS POINTS TO WHAT IS NEXT IN THE HORIZONTAL LISTS
		last = new SkipList_Node[height];
		this.value = value;
		this.level = height; // root.getLevel() should, theoretically spearking, return the max height of the SkipList that was inputted

  	}

  	public void setNext(int height, SkipList_Node node) {
    		next[height] = node;
  	}
	public void setLast(int height, SkipList_Node node) {
		last[height] = node;
	}
  	public double getValue() { return value; }
  	public SkipList_Node[] getNext() { return next; }
  	public SkipList_Node getNext(int level) { return next[level]; }
	public SkipList_Node getLast() { return last; }
	public SkipList_Node getLast(int level) { return last[level]; }
  	public String toString() { return "" + value; }
  	public int getLevel() { return level; }

  // --------------------------------------------------------------------
  // you may add any other methods you want to get the job done
  // --------------------------------------------------------------------



}
