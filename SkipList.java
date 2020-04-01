// WHAT IS THE POINT OF HAVING THE CONSTANT MAX HEIGHT, IF IT IS NOT USED IN THE SKIPLIST CONSTUCTOR
// I may have to change all the forloops and everything that works with levels if the levels actually start at 0.
// I can track the maximum value in the SkipList on insertion and deletion, if I choose to do so. Which is most efficient?

package SKPLIST_A4;

import java.util.Arrays;
import java.util.Random;

public class SkipList implements SkipList_Interface {
	private SkipList_Node root;
	private final Random rand;
	private double probability;
	private const int MAXHEIGHT = 30; // the most links that a data cell may contain
	private int size;


	public SkipList(int maxHeight) {
		root = new SkipList_Node(Double.NaN, maxHeight); // must be the sentinel // TODO: IS THIS REALLY THE SENTINEL??
		// root.getLevel() should, theoretically spearking, return the max height of the SkipList that was inputted
		rand = new Random();
		probability = 0.5;
		size = 0;
	}

	@Override
	public void setSeed(long seed) { rand.setSeed(seed); } /// WHAT DOES THIS ACTUALLY DO?????

	@Override
	public void setProbability(double probability) {
     	this.probability = probability;
	}

	private boolean flip() {
		// use this where you "roll the dice"
		// call it repeatedly until you determine the level
		// for a new node
		return rand.nextDouble() < probability;
	}

	@Override
	public SkipList_Node getRoot() { return root; }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int levels;

		for(levels = 0; levels < root.getNext().length && root.getNext(levels) != null; levels ++);

			StringBuilder[] sbs = new StringBuilder[levels];

			for(int i = 0; i < sbs.length; i ++) {
				sbs[i] = new StringBuilder();
				sbs[i].append("level ").append(i).append(":");
			}

			SkipList_Node cur = root;

			while (cur.getNext(0) != null) {
				cur = cur.getNext(0);
				for(int i = levels - 1; i >= cur.getNext().length; i --) {
					sbs[i].append("\t");
				}

			for(int i = cur.getNext().length - 1; i >= 0; i --) {
				if (cur.getNext(i) == null) {
					levels --;
				}

				sbs[i].append("\t").append(cur.getValue());
			}
		}

		for(int i = sbs.length - 1; i >= 0; i --) {
			sb.append(sbs[i]).append("\n");
		}

		return sb.toString();
	}

	//---------------------------------------------------------
	// student code follows
	// implement the methods of the interface
	//---------------------------------------------------------


	public boolean insert(double value);
	/*
	insert:
    in: a double (the element to be stored into the skiplist)
    return: boolean, return true if insert is successful, false otherwise
    effect: if the double is already in the skiplist, then there is no change to
            the skiplist state, and return false
            if the double is not already in the skiplist, then a new skiplist node
              is created, the double put into it as data, the new node is linked
              into the skiplist at the proper place; size is incremented by 1,
              and return a true
  	*/

	public boolean remove(double value);
  	/*
  	remove:
    in: a double (the element to be taken out of the skiplist)
    return: boolean, return true if the remove is successful, false otherwise
            this means return false if the skiplist size is 0
    effect: if the element being looked for is in the skiplist, unlink the node containing
            it and return true (success); size decreases by one
            if the element being looked for is not in the skiplist, return false and
            make no change to the skiplist state
  	*/

	//DONE
	public boolean contains(double value) {
		if (size() == 0) {
			return false;
		} else {
			// root's 'next' is the sentinel, I THINK
			SkipList_Node currentPlace = getRoot();
			SkipList_Node lastPlace = null;
			for (int level = level(); level >= 1; ) {
				if (currentPlace.getNext(level).getValue() == value) {
					return true;
				} else if (value < currentPlace.getNext(level).getValue() && level > 1) {
					level--;
				} else if (value > currentPlace.getNext(level).getValue() && currentPlace.getNext(level).getNext(level) != null) {
					if (value > currentPlace.getNext(level).getNext(level).getValue()) {
						lastPlace = currentPlace;
						currentPlace = currentPlace.getNext(level).getNext(level);
					} else {
						lastPlace = currentPlace;
						currentPlace = currentPlace.getNext(--level); //TODO: Does this actually change the value of level before the next loop iteration??
					}
				} else {
					System.out.println('Element not found in SkipList');
					return false;
				}
			}

		System.out.println('There is an error in contains(double value) method');
		return false;
	}
  	/*
  	contains:
    in: a double (the element to be searched for)
    return: boolean, return true if the double being looked for is in the skiplist;
            return false otherwise
            this also means return false if skiplist size is 0
    effect: no change to the state of the skiplist
  	*/

	//DONE
	public double findMin() {
		if (size() == 0) {
			return Double.NaN;
		} else {
			return root.getNext(1).getValue();
		}

		System.out.println('error in findMin() method');
		return Double.NaN;
	}

  	/*
  	findMin:
    in: none
    return: double, the element value from the skiplist that is smallest
    effect: no skiplist state change
    error: is skiplist size is 0, return Double.NaN

	*/

	//DONE
	public double findMax() {
		if (size() == 0) {
			return Double.NaN;
		} else {
			currentNode = getRoot();
			for (int nodeIndex = 0; nodeIndex <= size(); nodeIndex++) {
				if (currentNode.getNext(1) == null) {
					return currentNode.getValue();
				} else {
					currentNode = currentNode.getNext(1);
				}
			}
		}

		System.out.println('error in findMax() method');
		return Double.NaN;
	}

	/*
	findMax:
	in: none
	return: double, the element value from the skiplist that is largest
	effect: no skiplist state change
	error: is skiplist size is 0, return Double.NaN
	*/

	public boolean empty() {
		if (size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	/*empty:
    in: nothing
    return: boolean, true if the skiplist has no elements in it, true if size is 0
            return false otherwise
    effect: no change to skiplist state
    */
	public void clear();
	/*
	clear:
	in: none
	return: void
	effect: all elements in the skip list are unhooked so that no elements are in the list
            size is set to 0
            sentinel node remains
            the effect is to create a skip list state that exists when it is first
            produced by the constructor
            */

	public int size() {
		return size;
	}
  	/*
  	size:
    	in: nothing
    	return: number of elements stored in the skiplist
    	effect: no change to skiplist state
  	*/

	// level() currently is not as efficient as it could be
	public int level() {

		// returns the highest node in SL
		if (size() == 0) {
			return -1;
		} else {
			int biggestLevel = 0;
			for (int inc = 0; inc <= size() ; inc++) {
				if (node.getLevel() > biggestLevel) {
					biggestLevel = node.level;
				}
			}

			return biggestLevel;
		}
	}
	/*level:
	in: none
	return: integer, the level of the highest node in the skiplist
	effect: no change in skiplist state
	error: return -1 if skiplist is empty (size is 0, only sentinel node is there)
	*/

	public int max() {
		return MAXHEIGHT;
	}
     /*max:
    in: none
    return: integer, the value of MAXHEIGHT that is set in the list constructor
    effect: no change in skip list state
    */

	public int randomLevel(int startingLevel) {

		if (startingLevel < 0) {

		}

		if (startingLevel > max()) {
			return max();
		}

		if (flip() == true) {
			return startingLevel;
		} else {
			return randomLevel(++startingLevel);
		}
	}



// I DO NOT GET WHY MAX IS TO BE SET TO MAXHEIGHT CONSTANT AND NOT A DYNAMIC HEIGHT VARIABLE
}
