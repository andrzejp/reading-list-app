package io.prochyra.readinglist.acceptance;

import io.prochyra.readinglist.WireMockTest;
import io.prochyra.readinglist.application.*;
import io.prochyra.readinglist.external.catalogue.Catalogue;
import io.prochyra.readinglist.external.catalogue.CatalogueException;
import io.prochyra.readinglist.external.catalogue.GoogleBookAdapter;
import io.prochyra.readinglist.external.catalogue.GoogleBooksCatalogue;
import io.prochyra.readinglist.external.console.Console;
import io.prochyra.readinglist.external.repo.InMemoryBookRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.List.of;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReadingListIT extends WireMockTest {

    @Mock
    private Console console;

    @Test
    @SuppressWarnings("static-access")
    void should_return_a_list_of_five_books_matching_a_query() throws CatalogueException {
        Catalogue catalogue = new GoogleBooksCatalogue("http", "localhost", 8080, new GoogleBookAdapter());
        givenThat(get(anyUrl()).willReturn(ok().withBodyFile("1984_volumes.json")));

        List<Book> matchingBooks = catalogue.find("A Book Title");

        and.then(matchingBooks).hasSize(5);
    }

    @Test
    void should_save_books_to_reading_list_and_display_it() {
        ReadingListViewer viewer = new ReadingListViewer(console);
        ReadingList readingList = new ReadingList(viewer, new InMemoryBookRepository());

        Book book1 = new Book("Title", of("Author"), "Publisher");
        Book book2 = new Book("Title2", of("Author2"), "Publisher2");
        Book book3 = new Book("Title3", of("Author3"), "Publisher3");

        readingList.save(book1);
        readingList.save(book2);
        readingList.save(book3);

        readingList.view();

        then(console).should().printLine("1. 'Title' by Author - Publisher");
        then(console).should().printLine("2. 'Title2' by Author2 - Publisher2");
        then(console).should().printLine("3. 'Title3' by Author3 - Publisher3");
    }
}
