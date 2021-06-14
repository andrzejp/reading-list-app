package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.bind.adapter.JsonbAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GoogleBookAdapter implements JsonbAdapter<ArrayList<Book>, JsonObject> {

    @Override
    public JsonObject adaptToJson(ArrayList<Book> books) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public ArrayList<Book> adaptFromJson(JsonObject jsonObject) {
        ArrayList<Book> books = new ArrayList<>();

        if (jsonObject.isEmpty())
            return books;

        var items = jsonObject.getJsonArray("items").getValuesAs(JsonObject.class);

        for (var item : items) {
            var volumeInfo = item.getJsonObject("volumeInfo");
            var title = volumeInfo.getString("title");
            var publisher = volumeInfo.getString("publisher", "UNKNOWN");
            var authors = new ArrayList<String>();
            if (volumeInfo.containsKey("authors")) {
                authors.addAll(volumeInfo.getJsonArray("authors").getValuesAs(JsonString.class).stream().map(JsonString::getString).collect(Collectors.toList()));
            }
            var book = new Book(title, authors, publisher);
            books.add(book);
        }

        return books;
    }
}
