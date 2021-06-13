package io.prochyra.readinglist.domain;

import java.util.List;

public interface Catalogue {

    List<Book> find(String query);
}
