public class Percolation
{
    // ...
    // your data members here
    private WeightedQuickUnionUF unity, workAbout;
    private boolean[] siteIsFull;
    //private boolean[][] siteIsFull2;
    private boolean[][] grid;
    private int topIndex, bottomIndex, gridEdge;
    // ...

    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        // Which sites are connected;
        // Index N^2 is the top virtual site
        // Index N^2+1 is the bottom virtual site
        unity = new WeightedQuickUnionUF((N*N)+2);
        workAbout = new WeightedQuickUnionUF(N*N+1);
        topIndex = (N*N);
        bottomIndex = (N*N+1);

        // grid Edge size. Total grid size is N^2
        gridEdge = N;

        // grid to store which sites are open, full
        grid = new boolean[N][N];
        siteIsFull = new boolean[N*N];
        //siteIsFull2 = new boolean[N][N];

        // Join top virtual site to top row
        for (int i = 0; i < N; i++) {
            unity.union(topIndex, gridToIndex(0, i));
            workAbout.union(topIndex, gridToIndex(0, i));
        }

        // Join virtual bottom to bottom row
        for (int i = 0; i < N; i++)
            unity.union(gridToIndex(N-1, i), bottomIndex);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        int siteIndex = gridToIndex(i-1, j-1);
        indexValidator(siteIndex);
        if (!grid[i-1][j-1])
        {
            // Open site
            grid[i-1][j-1] = true;
/*
            // Open Top-row sites are always full
            if (i == 1 && grid[i-1][j-1])
                siteIsFull[siteIndex] = true;
*/
            // Join site to left
            if (j != 1 && grid[i-1][j-2]) {
                unity.union(siteIndex, siteIndex - 1);
                workAbout.union(siteIndex, siteIndex - 1);
            }

            // Join site to right
            if (j != gridEdge && grid[i-1][j]) {
                unity.union(siteIndex, siteIndex + 1);
                workAbout.union(siteIndex, siteIndex + 1);
            }

            // Join site below, unless bottom row
            if (i != gridEdge && grid[i][j-1]) {
                unity.union(siteIndex, siteIndex + gridEdge);
                workAbout.union(siteIndex, siteIndex + gridEdge);
            }

            // Join site above, unless top row
            if (i != 1 && grid[i-2][j-1]) {
                unity.union(siteIndex, siteIndex - gridEdge);
                workAbout.union(siteIndex, siteIndex - gridEdge);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        return grid[i-1][j-1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        int siteIndex = gridToIndex(i-1, j-1);
        indexValidator(siteIndex);

        // A site is full if connected to the topIndex and isOpen
        if (workAbout.connected(gridToIndex(i-1, j-1), topIndex)
                && grid[i-1][j-1])
                //&& !unity.connected(gridToIndex(i-1, j-1), bottomIndex))
            siteIsFull[siteIndex] = true;

        // Check site to left
        if (j != 1 && workAbout.connected(gridToIndex(i-1, j-2), topIndex) && grid[i-1][j-1])
        //if (j != 1 && siteIsFull[i-1][j-2] && grid[i-1][j-1])
            siteIsFull[siteIndex] = true;


        // Check site to right
        if (j != gridEdge && workAbout.connected(gridToIndex(i-1, j), topIndex) && grid[i-1][j-1])
        //if (j != gridEdge && siteIsFull[i-1][j] && grid[i-1][j-1])
            siteIsFull[siteIndex] = true;

        // Check site below, unless bottom row
        //if (i != gridEdge && unity.connected(gridToIndex(i, j-1), topIndex) && grid[i-1][j-1])
        if (i != gridEdge && siteIsFull[siteIndex] && grid[i-1][j-1])
            siteIsFull[siteIndex] = true;

        // Check site above, unless top row
        if (i != 1 && siteIsFull[siteIndex] && grid[i-1][j-1])
        //if (i != 1 && workAbout.connected(gridToIndex(i-2, j-1), topIndex) && grid[i-1][j-1])
            siteIsFull[siteIndex] = true;

        return (siteIsFull[siteIndex]);
    }

    // does the system percolate?
    public boolean percolates()
    {
        boolean perky = false;
        if (gridEdge == 1 && !grid[0][0])
            perky = false;
        else
            perky = unity.connected(topIndex, bottomIndex);

        return perky;
    }

    // Convert grid location to index in boolean array
    private int gridToIndex(int row, int col)
    {
        int index = col + (row * gridEdge);
        return index;
    }

    // Validates some give index, but I don't think I actually used it anywhere
    private boolean indexValidator(int index)
    {
        if (index < 0 || index > gridEdge * gridEdge+1) throw new IndexOutOfBoundsException("row index i out of bounds");
        return true;
    }
}
