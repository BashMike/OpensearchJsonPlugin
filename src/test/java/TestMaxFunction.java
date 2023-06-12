import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.example.StatisticFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMaxFunction {
    @Test
    void test_basic() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 1 },\n"
                + "{ \"a\": 2 },\n"
                + "{ \"a\": 3 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(3, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(3, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_fromTaskExample() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"number\":  6 },\n"
                + "{ \"number\": 10 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(10, StatisticFetcher.max( jsonArray, "number"));
        Assertions.assertEquals(10, StatisticFetcher.max(jsonString, "number"));
    }

    @Test
    void test_manyValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 10 },\n"
                + "{ \"a\": 20 },\n"
                + "{ \"a\": 30 },\n"
                + "{ \"a\": 40 },\n"
                + "{ \"a\": 50 },\n"
                + "{ \"a\": 60 },\n"
                + "{ \"a\": 70 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(70, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(70, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_empty() {
        // Prepare data
        String jsonString = "[]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyFields() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\":  1, \"b\": 100, \"c\": 10, \"d\": 123 },\n"
                + "{ \"a\": 20, \"b\":  50, \"c\":  3, \"d\": 123 },\n"
                + "{ \"a\": 30, \"b\":   2, \"c\": 40, \"d\": 123 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(30, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(30, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyFieldsDifferentCount() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\":  1, \"b\": 100, \"d\": 123 },\n"
                + "{ \"a\": 20, \"b\":  50, \"c\":   3 },\n"
                + "{ \"a\": 30, \"d\": 123 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(30, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(30, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 10 },\n"
                + "{ },\n"
                + "{ },\n"
                + "{ \"a\": 40 },\n"
                + "{ \"a\": 50 },\n"
                + "{ },\n"
                + "{ \"a\": 70 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(70, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(70, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesEmpty() {
        // Prepare data
        String jsonString = "[{}, {}, {}, {}, {}, {}, {}]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFields() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\":  10, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\":  25, \"c\": 64, \"d\": 48 },\n"
                + "{ \"a\":  70, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"a\":  40, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"a\":  50, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(70, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(70, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\":  25, \"c\": 64, \"d\": 48 },\n"
                + "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n"
                + "{ \"b\":  20, \"c\": 30, \"d\": 40 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsDifferentCount() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\":  10, \"b\": 20, \"d\": 40 },\n"
                + "{ \"b\":  20, \"c\": 30 },\n"
                + "{ \"b\":  25, \"d\": 48 },\n"
                + "{ \"a\":  70, \"c\": 30, \"d\": 40 },\n"
                + "{ \"a\":  40, \"b\": 20 },\n"
                + "{ \"a\":  50, \"d\": 40 },\n"
                + "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function
        double actualValue = StatisticFetcher.max(jsonArray, "a");

        // Run testing function and check result
        Assertions.assertEquals(70, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(70, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsDifferentCountEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"b\": 20, \"d\": 40 },\n"
                + "{ \"b\": 20, \"c\": 30 },\n"
                + "{ \"b\": 25, \"d\": 48 },\n"
                + "{ \"b\": 20 },\n"
                + "{ \"d\": 40 },\n"
                + "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n"
                + "{ \"c\": 30, \"d\": 40 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_invalidValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 3 },\n"
                + "{ \"a\": {\"e\": 2}},\n"
                + "{ \"a\": 1 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(3, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(3, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_invalidValuesEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": \"\" },\n"
                + "{ \"a\": {\"e\": 2}},\n"
                + "{ \"a\": \"hello\" }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyValuesInvalidValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 10 },\n"
                + "{ \"a\": {\"e\": 2} },\n"
                + "{ \"a\": 30 },\n"
                + "{ \"a\": 40 },\n"
                + "{ \"a\": {\"e\": {\"f\": 2}} },\n"
                + "{ \"a\": \"string\" },\n"
                + "{ \"a\": 70 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(70, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(70, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyValuesInvalidValuesEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{},\n"
                + "{ \"a\": {\"e\": 2} },\n"
                + "{ \"a\": \"hello\" },\n"
                + "{},\n"
                + "{ \"a\": {\"e\": {\"f\": 2}} },\n"
                + "{ \"a\": \"string\" },\n"
                + "{}\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyFieldsInvalidValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\":  1, \"b\": 100, \"c\": 10, \"d\": 123 },\n"
                + "{ \"a\": {\"e\": {\"f\": 2}}, \"b\":  50, \"c\":  3, \"d\": 123 },\n"
                + "{ \"a\": \"string\", \"b\":   2, \"c\": 40, \"d\": 123 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(1, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(1, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyFieldsInvalidValuesEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"b\": 100, \"c\": 10, \"d\": 123 },\n"
                + "{ \"a\": {\"e\": {\"f\": 2}}, \"b\":  50, \"c\":  3, \"d\": 123 },\n"
                + "{ \"a\": \"string\", \"b\":   2, \"c\": 40, \"d\": 123 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyFieldsDifferentCountInvalidValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\":  1, \"b\": 100, \"d\": 123 },\n"
                + "{ \"a\": \"string\", \"b\":  50, \"c\":  3 },\n"
                + "{ \"a\": 30, \"d\": 123 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(30, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(30, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_manyFieldsDifferentCountInvalidValuesEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"b\": 100, \"d\": 123 },\n"
                + "{ \"a\": \"string\", \"b\":  50, \"c\":  3 },\n"
                + "{ \"a\": {}, \"d\": 123 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesInvalidValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 10 },\n"
                + "{ },\n"
                + "{ },\n"
                + "{ \"a\": {\"e\": 2} },\n"
                + "{ \"a\": \"string\" },\n"
                + "{ },\n"
                + "{ \"a\": 70 }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(70, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(70, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesInvalidValuesEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": \"10\" },\n"
                + "{ },\n"
                + "{ },\n"
                + "{ \"a\": {\"e\": 2} },\n"
                + "{ \"a\": \"string\" },\n"
                + "{ },\n"
                + "{ \"a\": {} }\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsInvalidValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 10, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": {\"e\": 2}, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": \"string\", \"c\": 64, \"d\": 48 },\n"
                + "{ \"a\": 40, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"a\": {\"e\": 2}, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n"
                + "{ \"a\": \"string\", \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(40, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(40, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsInvalidValuesEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": \"10\", \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": {\"e\": 2}, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": \"string\", \"c\": 64, \"d\": 48 },\n"
                + "{ \"a\": {}, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"a\": {\"e\": 2}, \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "{ \"b\": 3.14, \"c\": 82, \"d\": 67 },\n"
                + "{ \"a\": \"string\", \"b\": 20, \"c\": 30, \"d\": 40 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsDifferentCountInvalidValues() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": 10, \"b\": 20, \"d\": 40 },\n"
                + "{ \"b\": {\"e\": 2}, \"c\": 30 },\n"
                + "{ \"b\": \"string\", \"d\": 48 },\n"
                + "{ \"a\": 40, \"b\": 20 },\n"
                + "{ \"a\": {\"e\": 2}, \"d\": 40 },\n"
                + "{ \"b\": 100, \"c\": 82, \"d\": 67 },\n"
                + "{ \"a\": \"string\", \"c\": 30, \"d\": 40 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(40, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(40, StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_absentValuesManyFieldsDifferentCountInvalidValuesEmpty() {
        // Prepare data
        String jsonString = "[\n"
                + "{ \"a\": \"10\", \"b\": 20, \"d\": 40 },\n"
                + "{ \"b\": {\"e\": 2}, \"c\": 30 },\n"
                + "{ \"b\": \"string\", \"d\": 48 },\n"
                + "{ \"a\": {}, \"b\": 20 },\n"
                + "{ \"a\": {\"e\": 2}, \"d\": 40 },\n"
                + "{ \"b\": 100.1, \"c\": 82, \"d\": 67 },\n"
                + "{ \"a\": \"string\", \"c\": 30, \"d\": 40 },\n"
                + "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StatisticFetcher.max(jsonString, "a"));
    }

    @Test
    void test_complex() {
        // Prepare data
        String jsonString = "[\n";
        double maxIndex = 1E4;
        for (int i=0; i<maxIndex; i++) {
            jsonString += "{ \"a\": " + i + " }";
            if (i < maxIndex-1) { jsonString += ","; }
            jsonString += "\n";
        }
        jsonString += "]";
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Run testing function and check result
        Assertions.assertEquals(maxIndex-1, StatisticFetcher.max( jsonArray, "a"));
        Assertions.assertEquals(maxIndex-1, StatisticFetcher.max(jsonString, "a"));
    }
}
