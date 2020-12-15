import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final int numberOfTrials;
    private final double[] x;   // fraction of open sites in grid when it percolates

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException();
        numberOfTrials = trials;
        x = new double[numberOfTrials];
        for (int i = 0; i < numberOfTrials; i++) {
            var percolation = new Percolation(n);
            while (!percolation.percolates()) {
                var row = StdRandom.uniform(1, n + 1);
                var col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col))
                    percolation.open(row, col);
            }
            x[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(numberOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(numberOfTrials));
    }



    public static void main(String[] args) {
        var gridSize = Integer.parseInt(args[0]);
        var numberOfTrials = Integer.parseInt(args[1]);
        var stats = new PercolationStats(gridSize, numberOfTrials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
