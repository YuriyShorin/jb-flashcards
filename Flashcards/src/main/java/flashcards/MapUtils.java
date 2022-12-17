package flashcards;

import java.util.ArrayList;
import java.util.Map;

public class MapUtils {

    public static String getKey(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static int getMaxValue(Map<String, Integer> map) {
        int max = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            max = Math.max(entry.getValue(), max);
        }
        return max;
    }

    public static ArrayList<String> getKeysOfMaxValue(Map<String, Integer> map, int max) {
        ArrayList<String> keysOfMaxValue = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                keysOfMaxValue.add(entry.getKey());
            }
        }
        return keysOfMaxValue;
    }
}
