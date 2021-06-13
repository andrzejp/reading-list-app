package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;

import java.util.List;

public class GoogleBooksCatalogue implements Catalogue {

    public GoogleBooksCatalogue(String hostName) {
    }

    @Override
    public List<Book> find(String query) {
        if (query.isBlank()) {
            return List.of();
        }
        return List.of(
                new Book("1984", "George Orwell", "Houghton Mifflin Harcourt"),
                new Book("1984", "George Orwell", "Univ. Press of Mississippi"),
                new Book("Older Americans Act Amendments of 1984", "United States. Congress. House. Committee on Education and Labor. Subcommittee on Human Resources", "UNKNOWN"),
                new Book("International Study Missions", "United States. Congress. House. Select Committee on Narcotics Abuse and Control", "UNKNOWN"),
                new Book("Proceedings of The Academy of Natural Sciences Special Publication 14, 1984", "UNKNOWN", "Academy of Natural Sciences"));
    }
}
