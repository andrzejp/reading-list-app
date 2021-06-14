package io.prochyra.readinglist.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.mockito.BDDMockito.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class ReadingListTest {

    @Mock
    private ReadingListPrinter printer;
    @Mock
    private BookRepository repository;

    @Test
    void should_start_out_empty() {
        ReadingList readingList = new ReadingList(printer, repository);

        readingList.view();

        then(printer).should().print(emptyList());
    }

    @Test
    void should_allow_adding_books() {
        var readingList = new ReadingList(printer, repository);
        var book = new Book("Title", emptyList(), "Publisher");

        readingList.save(book);

        then(repository).should().save(book);
    }
}