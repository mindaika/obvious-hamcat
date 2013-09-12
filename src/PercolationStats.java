public class PercolationStats
{
    // ...
    // your data members here
    private int gridSize, trials, totalSites;
    private double[] thatsJustMean;
    // ...

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Error, Will Robinson...");
        gridSize = N;
        trials = T;
        totalSites = N * N;
        thatsJustMean = new double[T];

        for (int index = 0; index < trials; index++) {
            Percolation perc = new Percolation(gridSize);
            double timeToPerk = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(gridSize) + 1;
                int j = StdRandom.uniform(gridSize) + 1;
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    timeToPerk++;
                }
            }
            thatsJustMean[index] = (timeToPerk / totalSites);
        }
    }

    public static void main(String[] args)
    {
        Stopwatch timer = new Stopwatch();
        PercolationStats perkStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.println("mean                    = " + perkStats.mean());
        StdOut.println("stddev                  = " + perkStats.stddev());
        StdOut.println("95% confidence interval = " + perkStats.confidenceLo() + ", " + perkStats.confidenceHi());
        StdOut.println("It took: " + timer.elapsedTime() + " seconds");
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(thatsJustMean);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(thatsJustMean);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        return (StdStats.mean(thatsJustMean) - StdStats.stddev(thatsJustMean));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        return (StdStats.mean(thatsJustMean) + StdStats.stddev(thatsJustMean));
    }
}
