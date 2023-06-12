import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.example.StatisticFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TestValuesFunction {
    @Test
    void test_basic() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\": 1 },\n";
        jsonString += "{ \"a\": 2 },\n";
        jsonString += "{ \"a\": 3 }\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("1"));
        expValue.add(JsonParser.parseString("2"));
        expValue.add(JsonParser.parseString("3"));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_fromTaskExample() {
        // Prepare data logstash elastic splunk elastic
        String jsonString = "[\n";
        jsonString += "{ \"name\": \"logstash\" },\n";
        jsonString += "{ \"name\": \"elastic\" },\n";
        jsonString += "{ \"name\": \"splunk\" },\n";
        jsonString += "{ \"name\": \"elastic\" },\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("logstash"));
        expValue.add(JsonParser.parseString("elastic"));
        expValue.add(JsonParser.parseString("splunk"));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "name"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "name"));
    }

    @Test
    void test_allUniqueValues() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\": 10 },\n";
        jsonString += "{ \"a\": 20 },\n";
        jsonString += "{ \"a\": \"super\" },\n";
        jsonString += "{ \"a\": {} },\n";
        jsonString += "{ \"a\": \"hello\" },\n";
        jsonString += "{ \"a\": {\"b\": 1} },\n";
        jsonString += "{ \"a\": 70 }\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("10"));
        expValue.add(JsonParser.parseString("20"));
        expValue.add(JsonParser.parseString("\"super\""));
        expValue.add(JsonParser.parseString("{}"));
        expValue.add(JsonParser.parseString("\"hello\""));
        expValue.add(JsonParser.parseString("{\"b\": 1}"));
        expValue.add(JsonParser.parseString("70"));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_someNonUniqueValues() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\": 70 },\n";
        jsonString += "{ \"a\": {\"b\": {\"c\": \"hello\", \"d\": {}}} },\n";
        jsonString += "{ \"a\": {} },\n";
        jsonString += "{ \"a\": {\"b\": 1} },\n";
        jsonString += "{ \"a\": \"super\" },\n";
        jsonString += "{ \"a\": {\"b\": {\"c\": \"hello\", \"d\": {}}} },\n";
        jsonString += "{ \"a\": {} },\n";
        jsonString += "{ \"a\": {\"b\": {\"c\": \"hello\", \"d\": {}}} },\n";
        jsonString += "{ \"a\": \"super\" },\n";
        jsonString += "{ \"a\": {\"b\": 1} },\n";
        jsonString += "{ \"a\": {} },\n";
        jsonString += "{ \"a\": 70 }\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("\"super\""));
        expValue.add(JsonParser.parseString("{}"));
        expValue.add(JsonParser.parseString("{\"b\": 1}"));
        expValue.add(JsonParser.parseString("{\"b\": {\"c\": \"hello\", \"d\": {}}}"));
        expValue.add(JsonParser.parseString("70"));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_empty() {
        // Prepare data
        String jsonString = "[]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_manyFields() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\": \"hello\", \"b\": 100, \"c\": 10, \"d\": 123 },\n";
        jsonString += "{ \"a\": \"hello\", \"b\":  50, \"c\":  3, \"d\": 123 },\n";
        jsonString += "{ \"a\": \"super\", \"b\":   2, \"c\": 40, \"d\": 123 }\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("\"hello\""));
        expValue.add(JsonParser.parseString("\"super\""));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_manyFieldsDifferentCount() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\": \"hello\", \"b\": 100, \"d\": 123 },\n";
        jsonString += "{ \"a\": {}, \"b\":  50, \"c\":   3 },\n";
        jsonString += "{ \"a\": \"super\", \"d\": 123 }\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("{}"));
        expValue.add(JsonParser.parseString("\"hello\""));
        expValue.add(JsonParser.parseString("\"super\""));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_absentValues() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\": 10 },\n";
        jsonString += "{ },\n";
        jsonString += "{ },\n";
        jsonString += "{ \"a\": 40 },\n";
        jsonString += "{ \"a\": 50 },\n";
        jsonString += "{ },\n";
        jsonString += "{ \"a\": 70 }\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("10"));
        expValue.add(JsonParser.parseString("40"));
        expValue.add(JsonParser.parseString("50"));
        expValue.add(JsonParser.parseString("70"));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_absentValuesEmpty() {
        // Prepare data
        String jsonString = "[{}, {}, {}, {}, {}, {}, {}]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFields() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\":  10, \"b\": 20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"a\":  40, \"b\": 20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"a\":  70, \"b\": 20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"b\":  25, \"c\": 64, \"d\": 48 },\n";
        jsonString += "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n";
        jsonString += "{ \"a\":  50, \"b\": 20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("10"));
        expValue.add(JsonParser.parseString("40"));
        expValue.add(JsonParser.parseString("50"));
        expValue.add(JsonParser.parseString("70"));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsEmpty() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"b\":  25, \"c\": 64, \"d\": 48 },\n";
        jsonString += "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n";
        jsonString += "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsDifferentCount() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"a\":  10, \"b\": 20, \"d\": 40 },\n";
        jsonString += "{ \"b\":  20, \"c\": 30 },\n";
        jsonString += "{ \"b\":  25, \"d\": 48 },\n";
        jsonString += "{ \"a\":  70, \"c\": 30, \"d\": 40 },\n";
        jsonString += "{ \"a\":  50, \"d\": 40 },\n";
        jsonString += "{ \"a\":  40, \"b\": 20 },\n";
        jsonString += "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        Set<JsonElement> expValue = new HashSet<>();
        expValue.add(JsonParser.parseString("10"));
        expValue.add(JsonParser.parseString("40"));
        expValue.add(JsonParser.parseString("50"));
        expValue.add(JsonParser.parseString("70"));

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsDifferentCountEmpty() {
        // Prepare data
        String jsonString = "[\n";
        jsonString += "{ \"b\": 20, \"d\": 40 },\n";
        jsonString += "{ \"b\": 20, \"c\": 30 },\n";
        jsonString += "{ \"b\": 25, \"d\": 48 },\n";
        jsonString += "{ \"b\": 20 },\n";
        jsonString += "{ \"d\": 40 },\n";
        jsonString += "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n";
        jsonString += "{ \"c\": 30, \"d\": 40 },\n";
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(new HashSet<>(), StatisticFetcher.values(jsonString, "a"));
    }

    @Test
    void test_complex() {
        // Prepare data
        String jsonString = "[\n";
        double maxIndex = 1E4;
        Set<JsonElement> expValue = new HashSet<>();
        for (int i=0; i<maxIndex; i++) {
            String currStr = "";
            for (int j=0; j<i%10; j++) { currStr += (char)(j % 26 + 'a'); }
            expValue.add(JsonParser.parseString("\"" + currStr + "\""));

            jsonString += "{ \"a\": \"" + currStr + "\" }";
            if (i < maxIndex-1) { jsonString += ","; }
            jsonString += "\n";
        }
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(expValue, StatisticFetcher.values( jsonArray, "a"));
        Assertions.assertEquals(expValue, StatisticFetcher.values(jsonString, "a"));
    }
}