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
        return new ArrayList<>();
    }
}
