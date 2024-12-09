import java.util.*;

class Item 
{
    private int id;
    private int weight;
    private int value;

    public Item(int i, int w, int v) {
        id = i;
        weight = w;
        value = v;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public void print() {
        System.out.printf("Item %d, w = %d, v = %d \n", id, weight, value);
    }
}

public class D13_1_Knapsack 
{
    public static final int MAX = 16;		// maximum capacity
    public static final int COUNT = 6;		// no. of items

    public static void main(String[] args) {
        Item[] items = new Item[6];
        items[1] = new Item(1, 3, 2);
        items[2] = new Item(2, 5, 4);
        items[3] = new Item(3, 8, 5);
        items[4] = new Item(4, 4, 3);
        items[5] = new Item(5, 9, 6);

        int[][] V = new int[COUNT][MAX];		// all initial values = 0
        int[][] W = new int[COUNT][MAX];		// all initial values = 0

        // remember picked items (in each table cell)
        BitSet[][] B = new BitSet[COUNT][MAX];
        for (int i = 0; i < COUNT; i++) {
            for (int k = 0; k < MAX; k++) {
                B[i][k] = new BitSet(COUNT);            // all bits = false
            }
        }
        // Add first item
        for (int k = items[1].getWeight(); k < MAX; k++) { // Weight of i1 = 3; So k starts at 3
            V[1][k] = items[1].getValue();
            W[1][k] = items[1].getWeight();
            B[1][k].set(1);				// pick this item (bit = true)
        }

        // Add remaining items
        for (int i = 2; i < COUNT; i++) {
            for (int k = 1; k < MAX; k++) {
                V[i][k] = V[i - 1][k];
                W[i][k] = W[i - 1][k];
                B[i][k] = new BitSet(COUNT);
                // copy picked items from previous iteration of i
                B[i][k].or(B[i - 1][k]);

                int space = k - items[i].getWeight();
                if (space >= 0) { // Space = 0
                    int newV = items[i].getValue() + V[i - 1][space];
                    int newW = items[i].getWeight() + W[i - 1][space];

                    if ((newV > V[i][k])
                            || (newV == V[i][k] && newW < W[i][k])) {
                        V[i][k] = newV;
                        W[i][k] = newW;
                        B[i][k].clear();
                        B[i][k].or(B[i - 1][space]);	// previously picked items
                        B[i][k].set(i);			// current item
                    }
                }
            }
        }

        // get result from B[COUNT-1][MAX-1]
        BitSet pick = B[COUNT - 1][MAX - 1];
        for (int i = 0; i < pick.length(); i++) {
            if (pick.get(i)) {
                items[i].print();
            }
        }
    }
}
