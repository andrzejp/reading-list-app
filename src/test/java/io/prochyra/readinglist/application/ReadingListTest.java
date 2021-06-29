package io.prochyra.readinglist.application;

import io.prochyra.readinglist.application.Book;
import io.prochyra.readinglist.external.repo.BookRepository;
import io.prochyra.readinglist.application.ReadingList;
import io.prochyra.readinglist.application.ReadingListViewer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.List.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class ReadingListTest {

    @Mock
    private ReadingListViewer viewer;
    @Mock
    private BookRepository repository;
    @InjectMocks
    private ReadingList readingList;

    @Test
    void should_start_out_empty() {
        readingList.view();

        then(viewer).should().show(emptyList());
    }

    @Test
    void should_allow_adding_books() {
        var book = new Book("Title", emptyList(), "Publisher");

        readingList.save(book);

        then(repository).should().save(book);
    }

    @Test
    void should_show_current_reading_list() {
        List<Book> books = of(
                new Book("Title", of("Author"), "Publisher"),
                new Book("Title2", of("Author2"), "Publisher2"),
                new Book("Title3", of("Author3"), "Publisher3")
        );

        given(repository.getAll()).willReturn(books);

        readingList.view();

        then(viewer).should().show(books);
    }
}