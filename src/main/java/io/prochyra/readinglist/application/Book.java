package io.prochyra.readinglist.application;

import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;

public record Book(String title, List<String> authors, String publisher) {

    @Override
    public String toString() {
        return format("'%s' by %s - %s", title, join(", ", authors), publisher);
    }
}
