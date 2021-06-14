package io.prochyra.readinglist.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ReadingListTest {

    @Mock
    private ReadingListPrinter printer;

    @Test
    void should_start_out_empty() {
        ReadingList readingList = new ReadingList(printer);

        readingList.view();

        then(printer).should().print(emptyList());
    }
}