class problem2{
// Using a greedy “distance buckets” idea. It computes the Manhattan distance for every worker–bike pair and stores pairs in a map keyed by distance.
// Then it scans distances from smallest to largest and assigns a bike to a worker whenever both are still unassigned.
// The first valid match at the smallest distance is kept, and the process continues until all workers get bikes.

    public int[] assignBikes(int[][] workers, int[][] bikes)
    {
        int m= workers.length;
        int n= bikes.length;

        HashMap<Integer, List<int[]>> map= new HashMap<>();
        int min= Integer.MAX_VALUE;
        int max= Integer.MIN_VALUE;

        for (int i = 0; i < m; i++) //worker
        {
            for (int j = 0; j < n; j++) //biker
            {
                int dist= calculateDist(workers[i], bikes[j]);
                min = Math.min(min, dist);
                max = Math.max(max, dist);

                map.putIfAbsent(dist, new ArrayList<>());
                map.get(dist).add(new int[]{i,j}); // {wi,bj}
            }
        }
        int result= new int[m];
        boolean[] workerVisited= new boolean[m];
        boolean[] bikesVisited= new boolean[n];

        for (int i=min;i<max;i++) // O(m.n + R)
        {
            if (!map.containsKey(i)){continue;}
            List<int[]> list= map.get(i); //pairs

            for(int[] wb: list) // iterating thru all the pairs : bucket sort , costs O(m.n)
            {
                int workerId= wb[0];
                int bikeId= wb[1];

                if (!workerVisited[workerId] && !bikesVisited[bikeId]){
                    result[workerId]= bikeId;
                    workerVisited[workerId]= true;
                    bikesVisited[bikeId]= true;
                }

            }
        }
        return result;

    }
    private int calculateDist(int[] workers, int[] bikes)
    {
        return Math.abs(workers[0]-bikes[0])+Math.abs(workers[1]-bikes[1]);
    }
}
