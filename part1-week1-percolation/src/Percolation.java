import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int vTop;
    private final int vBottom;
    private int openSitesCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();
        gridSize = n;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);   // 0-th and (n * n + 1)th are virtual top and bottom
        vTop = 0;
        vBottom = n * n + 1;
        openSitesCount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSitesCount++;
        }
        // connecting to virtual top
        if (row == 1)
            uf.union(getUfIndex(row, col), vTop);
        // connecting to virtual bottom
        if (row == gridSize)
            uf.union(getUfIndex(row, col), vBottom);

        if (row > 1 && isOpen(row - 1, col))
            uf.union(getUfIndex(row, col), getUfIndex(row - 1, col));
        if (row < gridSize && isOpen(row + 1, col))
            uf.union(getUfIndex(row, col), getUfIndex(row + 1, col));
        if (col > 1 && isOpen(row, col - 1))
            uf.union(getUfIndex(row, col), getUfIndex(row, col - 1));
        if (col < gridSize && isOpen(row, col + 1))
            uf.union(getUfIndex(row, col), getUfIndex(row, col + 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.find(getUfIndex(row, col)) == uf.find(vTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(vTop) == uf.find(vBottom);
    }

    private int getUfIndex(int row, int col) {
        return gridSize * (row - 1) + col; // not (col + 1) because 0-th is virtual top
    }

    private void validate(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException();
    }
}