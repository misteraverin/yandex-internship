package task5;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tester {
    @Test
    public void testPutOperation(){
        ATM atm = new ATM();
        assertTrue(atm.put(1, 3));
        assertTrue(atm.put(500, 5));
        assertTrue(atm.put(1000, 10));
        assertFalse(atm.put(-1, 3));
        assertFalse(atm.put(0, 3));
        assertFalse(atm.put(-1, 7));
        assertFalse("negative denomination", atm.put(-1, 9));
    }

    @Test
    public void testStateOperation(){
        ATM atm = new ATM();
        assertTrue(atm.put(1, 3));
        assertEquals(atm.state(), 3);
        assertTrue(atm.put(500, 5));
        assertEquals(atm.state(), 2503);
        assertTrue(atm.put(1000, 10));
        assertEquals(atm.state(), 12503);
    }
    @Test
    public void testDumpOperation(){
        ATM atm = new ATM();
        assertTrue(atm.put(1, 8));
        assertTrue(atm.put(50, 7));
        assertTrue(atm.put(5000, 10));
        Map<Integer, Integer> ans = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        final int[] AVAILABLE_DENOMINATIONS = new int[]{1, 3, 5, 10, 25, 50, 100, 500, 1000, 5000};
        for(Integer value : AVAILABLE_DENOMINATIONS)
            ans.put(value, 0);
        ans.put(1, ans.get(1) + 8);
        ans.put(50, ans.get(50) + 7);
        ans.put(5000, ans.get(5000) + 10);
        StringBuilder answer = new StringBuilder();
        for(Integer value : ans.keySet())
                answer.append(String.format("%d=%d\n", value, ans.get(value)));
        assertEquals(answer, atm.dump());
    }

}
