package Task5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Task5 {

    public static void main(String[] args) throws IOException {
        new Task5().solve();
    }

    public void solve() throws IOException {
        ATM atm = new ATM();
        BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outWriter = new PrintWriter(System.out);
        String line = inReader.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            String command = st.nextToken();
            if (command.equals("quit")) {
                break;
            } else if (command.equals("state")) {
                outWriter.println(atm.state());
            } else if (command.equals("dump")) {
                ArrayList<Pair<Integer, Integer>> ans = atm.dump();
                for (Pair<Integer, Integer> cur : ans) {
                    outWriter.println(cur.getFirst().toString() + "=" + cur.getSecond().toString());
                }
            } else if (command.equals("put")) {
                boolean result = atm.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                if (result) outWriter.println(atm.state());
                else outWriter.println("неправильные аргументы");
            } else if (command.equals("get")) {
                int need = Integer.parseInt(st.nextToken());
                ArrayList<Pair<Integer, Integer>> ans = atm.get(need);
                int currentAmount = 0;
                for (Pair<Integer, Integer> cur : ans) {
                    currentAmount += cur.getSecond() * cur.getFirst();
                    outWriter.println(cur.getFirst().toString() + "=" + cur.getSecond().toString());
                }
                if (need - currentAmount != 0)
                    outWriter.println("без " + (need - currentAmount));

            }
            outWriter.flush();
            line = inReader.readLine();

        }
        outWriter.close();
    }
}
