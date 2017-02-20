package task5;

import java.util.*;

import javafx.util.Pair;

public class ATM {


    private int availableSum = 0;
    private final static int[] AVAILABLE_DENOMINATIONS = new int[]{1, 3, 5, 10, 25, 50, 100, 500, 1000, 5000};
    private Map<Integer, Integer> amounts = new HashMap<>();

    public ATM() {
        for (Integer value : AVAILABLE_DENOMINATIONS)
            amounts.put(value, 0);
    }

    public int state() {
        return availableSum;
    }

    public boolean put(int denomination, int count) {
        if (count <= 0)
            return false;
        Integer result = amounts.get(denomination);
        if (result != null) {
            amounts.put(denomination, result + count);
            availableSum += denomination * count;
            return true;
        } else
            return false;
    }

    public StringBuilder dump() {
        StringBuilder answer = new StringBuilder();
        for (Integer value : amounts.keySet()) {
            answer.append(String.format("%d=%d\n", value, amounts.get(value)));
        }
        return answer;
    }

    /*
        @param  amount money, you need to get
        @return        list, consisting of denominations and their counts
     */
    public List<Pair<Integer, Integer>> get(int amount) {
        HashMap<Integer, Integer> best = new HashMap<>();
        ArrayList<Integer> notes = new ArrayList<Integer>();
        List<Pair<Integer, Integer>> answer = new ArrayList<>();

        for (Integer value : amounts.keySet())
            for (int j = 0; j < amounts.get(value); j++)
                notes.add(value);

        int cnt = notes.size();
        boolean[][] canGet = new boolean[cnt + 1][amount + 1]; // can we get amount with cnt notes
        Pair[][] prevState = new Pair[cnt + 1][amount + 1]; // from which state we get to amount with cnt notes
        canGet[0][0] = true;
        prevState[0][0] = new Pair(0, 0);
        for (int i = 1; i <= cnt; i++) {
            for (int s = 0; s <= amount; s++) {
                canGet[i][s] = canGet[i - 1][s];
                if (s - notes.get(i - 1) >= 0 && canGet[i - 1][s - notes.get(i - 1)]) {
                    canGet[i][s] = true;
                    prevState[i][s] = new Pair(i - 1, s - notes.get(i - 1));
                }
            }
        }
        int currentValue = 0;
        for (int s = amount; s >= 0; s--) {
            if (canGet[cnt][s]) {
                currentValue = s;
                break;
            }
        }
        Pair<Integer, Integer> curState = new Pair(cnt, currentValue);

        while (curState.getKey() != 0 && curState.getValue() != 0) {
            int count = curState.getKey();
            int sum = curState.getValue();
            curState = prevState[count][sum];
            int denomination = sum - curState.getValue();
            amounts.put(denomination, amounts.get(denomination) - 1);
            availableSum -= denomination;
            best.put(denomination, best.get(denomination) + 1);
        }

        for(Integer value : best.keySet()){
            answer.add(new Pair(value, best.get(value)));
        }
        return answer;
    }
}
