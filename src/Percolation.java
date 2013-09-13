public class Percolation
{
    // ...
    // your data members here
    private WeightedQuickUnionUF percChain, fullState;
    private boolean[] siteIsOpen;
    private int topIndex, bottomIndex, gridEdge;
    // ...

    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        // Which sites are connected;
        // Index N^2 is the top virtual site
        // Index N^2+1 is the bottom virtual site
        percChain = new WeightedQuickUnionUF((N*N)+2);
        fullState = new WeightedQuickUnionUF((N*N)+1);
        topIndex = (N*N);
        bottomIndex = (N*N+1);

        // grid Edge size. Total grid size is N^2
        gridEdge = N;

        // grid to store which sites are open, full
        siteIsOpen = new boolean[N*N];

        // Join top virtual site to top row
        for (int i = 0; i < N; i++) {
            percChain.union(topIndex, gridToIndex(0, i));
            fullState.union(topIndex, gridToIndex(0, i));
        }

        // Join virtual bottom to bottom row
        for (int i = 0; i < N; i++)
            percChain.union(gridToIndex(N-1, i), bottomIndex);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        int siteIndex = gridToIndex(i-1, j-1);
        if (!siteIsOpen[siteIndex])
        {
            // Open site
            siteIsOpen[siteIndex] = true;

            // Join site to left
            if (j != 1 && siteIsOpen[siteIndex - 1]) {
                percChain.union(siteIndex, siteIndex - 1);
                fullState.union(siteIndex, siteIndex - 1);
            }

            // Join site to right
            if (j != gridEdge && siteIsOpen[siteIndex + 1]) {
                percChain.union(siteIndex, siteIndex + 1);
                fullState.union(siteIndex, siteIndex + 1);
            }

            // Join site below, unless bottom row
            if (i != gridEdge && siteIsOpen[siteIndex + gridEdge]) {
                percChain.union(siteIndex, siteIndex + gridEdge);
                fullState.union(siteIndex, siteIndex + gridEdge);
            }

            // Join site above, unless top row
            if (i != 1 && siteIsOpen[siteIndex - gridEdge]) {
                percChain.union(siteIndex, siteIndex - gridEdge);
                fullState.union(siteIndex, siteIndex - gridEdge);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        return siteIsOpen[gridToIndex(i-1, j-1)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        // Set siteIndex, for great justice
        int siteIndex = gridToIndex(i-1, j-1);
        return (fullState.connected(siteIndex, topIndex) && siteIsOpen[siteIndex]);
    }

    // does the system percolate?
    public boolean percolates()
    {
        return (!(gridEdge == 1 && !siteIsOpen[gridToIndex(0, 0)]) && percChain.connected(topIndex, bottomIndex));
    }

    // Convert grid location to index in boolean array
    private int gridToIndex(int row, int col)
    {
        indexValidator(row);
        indexValidator(col);
        return (col + (row * gridEdge));
    }

    // Validates some given index
    private boolean indexValidator(int index)
    {
        if (index < 0 || index >= gridEdge) throw new IndexOutOfBoundsException("row index is out of bounds");
        return true;
    }
}
