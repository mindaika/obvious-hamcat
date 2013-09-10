public class Percolation
{
    // ...
    // your data members here
    private WeightedQuickUnionUF Unity;
    private int GridEdge;
    private boolean[][] Grid;
    // ...

    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        Unity = new WeightedQuickUnionUF((N*N)); // Which sites are connected
        GridEdge = N;                            // Grid Size
        Grid = new boolean[N][N];                // Which sites are open
    }


    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {

    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        //System.out.println(gridToIndex(i, j));
        return false;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        return false;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return false;
    }

    private int gridToIndex(int row, int col)
    {
        int index = col + ((row - 1) * GridEdge);
        return index;
    }

    private boolean indexValidator(int index)
    {
        boolean indexIsValid = false;
        //if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
    }
}
