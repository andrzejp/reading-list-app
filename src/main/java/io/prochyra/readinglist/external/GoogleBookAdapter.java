package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.bind.adapter.JsonbAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class GoogleBookAdapter implements JsonbAdapter<ArrayList<Book>, JsonObject> {

    @Override
    public JsonObject adaptToJson(ArrayList<Book> books) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public ArrayList<Book> adaptFromJson(JsonObject jsonObject) {
        if (jsonObject.isEmpty())
            return new ArrayList<>();

        return getItems(jsonObject)
                .stream()
                .map(this::getBook)
                .collect(toCollection(ArrayList::new));
    }

    private List<JsonObject> getItems(JsonObject jsonObject) {
        return jsonObject.getJsonArray("items")
                .getValuesAs(JsonObject.class);
    }

    private Book getBook(JsonObject item) {
        var volumeInfo = item.getJsonObject("volumeInfo");
        var title = volumeInfo.getString("title");
        var publisher = volumeInfo.getString("publisher", "UNKNOWN");
        var authors = new ArrayList<String>();
        if (volumeInfo.containsKey("authors"))
            authors.addAll(volumeInfo.getJsonArray("authors").getValuesAs(JsonString.class).stream().map(JsonString::getString).collect(Collectors.toList()));

        return new Book(title, authors, publisher);
    }
}
