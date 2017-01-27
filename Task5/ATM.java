package Task5;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by user on 27.01.2017.
 */
public class ATM {


    private static int availableSum = 0;
    private static int[] availableDenominations = new int[]{1, 3, 5, 10, 25, 50, 100, 500, 1000, 5000};
    private static int[] availableDenominationCount;


    public int state() {
        return availableSum;
    }

    public boolean put(int denomination, int count) {
        if (count <= 0)
            return false;
        int denominationIndex = Arrays.binarySearch(availableDenominations, denomination);
        if (denominationIndex < 0) {
            return false;
        }
        availableDenominationCount[denominationIndex] += count;
        availableSum += availableDenominations[denominationIndex] * count;
        return true;
    }

    public ArrayList<Pair<Integer, Integer>> dump() {
        ArrayList<Pair<Integer, Integer>> answer = new ArrayList<Pair<Integer, Integer>>();
        for (int i = availableDenominations.length - 1; i >= 0; i--) {
            if (availableDenominationCount[i] > 0)
                answer.add(new Pair(availableDenominations[i], availableDenominationCount[i]));
        }
        return answer;
    }

    public ArrayList<Pair<Integer, Integer>> get(int amount) {
        ArrayList<Pair<Integer, Integer>> answer = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Integer> notes = new ArrayList<Integer>();
        for (int i = 0; i < availableDenominationCount.length; i++) {
            for (int j = 0; j < availableDenominationCount[i]; j++)
                notes.add(availableDenominations[i]);
        }
        int cnt = notes.size();
        boolean[][] dp = new boolean[cnt + 1][amount + 1];
        Pair[][] pr = new Pair[cnt + 1][amount + 1];
        dp[0][0] = true;
        pr[0][0] = new Pair(0, 0);
        for (int i = 1; i <= cnt; i++) {
            for (int s = 0; s <= amount; s++) {
                dp[i][s] = dp[i - 1][s];
                if (s - notes.get(i - 1) >= 0 && dp[i - 1][s - notes.get(i - 1)]) {
                    dp[i][s] = true;
                    pr[i][s] = new Pair(i - 1, s - notes.get(i - 1));
                }
            }
        }
        int currentValue = 0;
        for (int s = amount; s >= 0; s--) {
            if (dp[cnt][s]) {
                currentValue = s;
                break;
            }
        }
        Pair<Integer, Integer> curState = new Pair(cnt, currentValue);
        ArrayList<Integer> takenNotes = new ArrayList<Integer>();
        while (curState.getFirst() != 0 && curState.getSecond() != 0) {
            int first = curState.getFirst();
            int second = curState.getSecond();
            curState = pr[first][second];
            int denomination = second - curState.getSecond();
            int denominationIndex = Arrays.binarySearch(availableDenominations, denomination);
            availableDenominationCount[denominationIndex]--;
            availableSum -= denomination;
            takenNotes.add(denomination);
        }
        int[] answerDenominationCount = new int[availableDenominations.length];
        for (int i = 0; i < availableDenominations.length; i++) {
            for (Integer note : takenNotes)
                if (availableDenominations[i] == note)
                    answerDenominationCount[i]++;
        }
        for (int i = 0; i < availableDenominations.length; i++)
            if (answerDenominationCount[i] > 0)
                answer.add(new Pair(availableDenominations[i], answerDenominationCount[i]));
        return answer;
    }

    public ATM() {
        availableDenominationCount = new int[availableDenominations.length];
    }
}
