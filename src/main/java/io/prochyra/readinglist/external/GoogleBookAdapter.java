package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

import java.util.ArrayList;

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

        var items = jsonObject.getJsonArray("items");

        var firstItem = items.getJsonObject(0);
        var volumeInfo = firstItem.getJsonObject("volumeInfo");
        var title = volumeInfo.getString("title");
        var book = new Book(title, "UNKNOWN", "UNKNOWN");

        books.add(book);

        return books;
    }
}
