

class D13_2_OptimalBST 
{
    public static void main(String[] args) {
        int n = 9;
        double[] Pr = {0, 0.22, 0.18, 0.20, 0.05, 0.25, 0.02, 0.08, 0};

        double[][] CP = new double[n][n];	// all members = 0
        double[][] Cost = new double[n][n];	// by default
        int[][] R = new int[n][n];

        // initialize all tables
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    CP[i][i] = Cost[i][i] = Pr[i];	// diagonal line
                }
                if (j >= i) {
                    R[i][j] = i;                        // upper half
                }
            }
        }

        // cumulative prob.
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                CP[i][j] = CP[i][j - 1] + Pr[j];
            }
        }

        System.out.println("\n=== Cumulative Probability ===");
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                System.out.printf("%.2f  ", CP[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        double newCost;
        for (int size = 1; size < n; size++) {              // tree size
            for (int i = 1; i < n - size - 1; i++) {        // no. trees to consider
                int j = size + i;

                for (int root = i; root <= j; root++) {     // best root for each tree
                    newCost = Cost[i][root - 1] + Cost[root + 1][j] + CP[i][j];
                    if (Cost[i][j] > 0) {
                        if (newCost < Cost[i][j]) {
                            Cost[i][j] = newCost;
                            R[i][j] = root;
                        }
                    } else {
                        Cost[i][j] = newCost;
                    }
                }  // end for root
            }  // end for i
        }  // end for count

        System.out.println("\n=== Minimum Cost ===");
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (Cost[i][j] > 0) {
                    System.out.printf("%.2f  ", Cost[i][j]);
                } else {
                    System.out.printf("----  ");
                }
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("\n=== Best Root ===");
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                System.out.printf("%d  ", R[i][j]);
            }
            System.out.println();
        }
    }
}
