public class Percolation
{
    // ...
    // your data members here
    private WeightedQuickUnionUF Unity;
    private int GridEdge;
    private boolean[][] Grid;
    private int TopIndex, BottomIndex;
    // ...

    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        // Which sites are connected;
        // Index N^2 is the top virtual site
        // Index N^2+1 is the bottom virtual site
        Unity = new WeightedQuickUnionUF((N*N)+2);
        TopIndex = (N*N);
        BottomIndex = (N*N+1);

        // Grid Edge size. Total grid size is N^2
        GridEdge = N;

        // Grid to store which sites are open
        Grid = new boolean[N][N];

        // Join top virtual site to top row
        for (int i = 0; i < N; i++)
            Unity.union(TopIndex, gridToIndex(0,i));

        // Join virtual bottom to bottom row
        for (int i = 0; i < N; i++)
            Unity.union(gridToIndex(N-1, i),BottomIndex);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        if (!Grid[i-1][j-1])
        {
            int siteIndex = gridToIndex(i-1, j-1);
            indexValidator(siteIndex);

            // Open site
            Grid[i-1][j-1] = true;

            // Join site to left
            if (j != 1 && Grid[i-1][j-1])
                Unity.union(siteIndex, siteIndex-1);

            // Join site to right
            if (j != GridEdge && Grid[i-1][j])
                Unity.union(siteIndex, siteIndex+1);

            // Join site below, unless bottom row
            if (i != GridEdge && Grid[i][j-1])
                Unity.union(siteIndex, siteIndex+GridEdge);

            // Join site above, unless top row
            if (i != 1 && Grid[i-2][j-1])
                Unity.union(siteIndex, siteIndex-GridEdge);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        return Grid[i-1][j-1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        return (Unity.connected(gridToIndex(i-1, j-1),TopIndex) &&
                Grid[i-1][j-1]);
    }

    // does the system percolate?
    public boolean percolates()
    {
        return Unity.connected(TopIndex,BottomIndex);
    }

    // Convert grid location to index in boolean array
    private int gridToIndex(int row, int col)
    {
        int index = col + (row * GridEdge);
        return index;
    }

    // Validates some give index, but I don't think I actually used it anywhere
    private boolean indexValidator(int index)
    {
        boolean indexIsValid = true;
        if (index < 0 || index > GridEdge*GridEdge+2) throw new IndexOutOfBoundsException("row index i out of bounds");
        return indexIsValid;
    }
}
