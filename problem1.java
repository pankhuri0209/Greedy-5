
class problem1 {

    // Time Complexity: O(m*n)
    // Space Complexity: O(m*n)
//    This code uses dynamic programming where dp[i][j] means the first i characters of s match the first j characters of pattern p.
//    It initializes matches for the empty string, especially handling * since it can represent an empty sequence.
//    While filling the table, * can either match nothing (dp[i][j-1]) or consume a character (dp[i-1][j]), and ?/exact letters match using dp[i-1][j-1].
//    Finally, it returns dp[m][n] to tell if the full string matches the full pattern.

    // Approch1 : tabulation
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 1; j <= n; i++) {
            char pChar = p.charAt(j - 1);
            if (pChar == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pChar = p.charAt(j - 1);
                if (pChar == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    if (pChar == s.charAt(i - 1) || pChar == '?') {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = false;
                    }
                }
            }
        }
        return dp[m][n];
    }

    // Approach 2: greedy
    public boolean isMatch1(String s, String p) {

        int i=0,j=0;
        int startIdx=-1;
        int match =0;

        while(i<s.length())
        {
            if (j<p.length() && (p.charAt(j) =='?' || s.charAt(i) == p.charAt(j)))
            {
                i++;
                j++;
            } else if (j< p.length() && p.charAt(j) == '*') {
                match=i;
                startIdx=j;
                j++;
            }
            else if (startIdx!=-1)
            {
                j= startIdx+1;
                match++;
                i= match;
            }
            else {
                return false;
            }
        }
        while (j<p.length() && p.charAt(j) == '*')
        {
            j++;
        }

        return j==p.length();
    }

    // Approach 3: Memoization

    private String s,p;
    private int m, n;
    private int[][] memo;

    public boolean isMatch2(String s, String p) {
        this.s=s;
        this.p=p;
        this.m= s.length();
        this.n= p.length();
        this.memo = new int[m+1][n+1];
        return dfs(0,0);
    }
    private boolean dfs(int i, int j) {
        if (memo[i][j] != null) return memo[i][j];

        // if pattern finished, string must also be finished
        if (j == pArr.length) {
            return memo[i][j] = (i == sArr.length);
        }

        // if string finished, remaining pattern must be all '*'
        if (i == sArr.length) {
            for (int k = j; k < pArr.length; k++) {
                if (pArr[k] != '*') return memo[i][j] = false;
            }
            return memo[i][j] = true;
        }

        boolean ans;
        if (pArr[j] == '*') {
            // 1) '*' matches empty  -> move pattern (j+1)
            // 2) '*' matches a char -> move string  (i+1)
            ans = dfs(i, j + 1) || dfs(i + 1, j);
        } else {
            // match single char: exact or '?'
            ans = (pArr[j] == '?' || pArr[j] == sArr[i]) && dfs(i + 1, j + 1);
        }

        return memo[i][j] = ans;
    }
}