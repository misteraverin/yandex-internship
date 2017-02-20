package task5;

import javafx.util.Pair;

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
        try (BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter outWriter = new PrintWriter(System.out)) {
            String line = inReader.readLine();
            while (line != null) {
                StringTokenizer st = new StringTokenizer(line);
                String command = st.nextToken();
                switch (command) {
                    case "quit":
                        break;
                    case "state":
                        outWriter.println(atm.state());
                    case "dump":
                        outWriter.println(atm.dump());
                    case "put":
                        boolean result = atm.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                        if (result) outWriter.println(atm.state());
                        else outWriter.println("wrong arguments");
                    case "get":
                        int need = Integer.parseInt(st.nextToken());
                        List<Pair<Integer, Integer>> ans = atm.get(need);
                        int currentAmount = 0;
                        for (Pair<Integer, Integer> cur : ans) {
                            currentAmount += cur.getKey() * cur.getValue();
                            outWriter.println(cur.getKey().toString() + "=" + cur.getValue().toString());
                        }
                        outWriter.println("total: " + currentAmount);
                        if (need - currentAmount != 0)
                            outWriter.println("without " + (need - currentAmount));

                }
                outWriter.flush();
                line = inReader.readLine();
            }
        }
    }
}
