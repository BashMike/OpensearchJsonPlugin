package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticFetcher {
    static public double avg(@NotNull final Reader jsonReader, @NotNull final String fieldName) {
        return StatisticFetcher.avg(JsonParser.parseReader(jsonReader).getAsJsonArray(), fieldName);
    }

    static public double avg(@NotNull final String jsonArrayStr, @NotNull final String fieldName) {
        return StatisticFetcher.avg(JsonParser.parseString(jsonArrayStr).getAsJsonArray(), fieldName);
    }

    static public double avg(@NotNull final JsonArray array, @NotNull final String fieldName) {
        OptionalDouble result = array.asList().stream()
                // Get JSON elements that have field with given name and value of NUMBER type
                .filter(e -> e.isJsonObject() &&
                        e.getAsJsonObject().has(fieldName) &&
                        e.getAsJsonObject().get(fieldName).isJsonPrimitive() &&
                        e.getAsJsonObject().get(fieldName).getAsJsonPrimitive().isNumber())
                .mapToInt(e -> e.getAsJsonObject().get(fieldName).getAsInt())
                .average();

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Failed to get average of \"" + fieldName + "\" field values of JSON array elements " +
                    "because they don't contain that field or all values are invalid.");
        }

        return result.getAsDouble();
    }

    static public int max(@NotNull final Reader jsonReader, @NotNull final String fieldName) {
        return StatisticFetcher.max(JsonParser.parseReader(jsonReader).getAsJsonArray(), fieldName);
    }

    static public int max(@NotNull final String jsonArrayStr, @NotNull final String fieldName) {
        return StatisticFetcher.max(JsonParser.parseString(jsonArrayStr).getAsJsonArray(), fieldName);
    }

    static public int max(@NotNull final JsonArray array, @NotNull final String fieldName) {
        OptionalInt result = array.asList().stream()
                // Get JSON elements that have field with given name and value of NUMBER type
                .filter(e -> e.isJsonObject() &&
                        e.getAsJsonObject().has(fieldName) &&
                        e.getAsJsonObject().get(fieldName).isJsonPrimitive() &&
                        e.getAsJsonObject().get(fieldName).getAsJsonPrimitive().isNumber())
                .mapToInt(e -> e.getAsJsonObject().get(fieldName).getAsInt())
                .max();

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Failed to get maximum of \"" + fieldName + "\" field values of JSON array elements " +
                    "because they don't contain that field or all values are invalid.");
        }

        return result.getAsInt();
    }

    @NotNull
    static public Set<JsonElement> values(@NotNull final Reader jsonReader, @NotNull final String fieldName) {
        return StatisticFetcher.values(JsonParser.parseReader(jsonReader).getAsJsonArray(), fieldName);
    }

    @NotNull
    static public Set<JsonElement> values(@NotNull final String jsonArrayStr, @NotNull final String fieldName) {
        return StatisticFetcher.values(JsonParser.parseString(jsonArrayStr).getAsJsonArray(), fieldName);
    }

    @NotNull
    static public Set<JsonElement> values(@NotNull final JsonArray array, @NotNull final String fieldName) {
        return array.asList().stream()
                // Get JSON elements that have field with given name and value which is not a JSON null
                .filter(e -> e.isJsonObject() &&
                        e.getAsJsonObject().has(fieldName) &&
                       !e.getAsJsonObject().get(fieldName).isJsonNull())
                .map(e -> e.getAsJsonObject().get(fieldName))
                .collect(Collectors.toCollection(HashSet::new));
    }
}
