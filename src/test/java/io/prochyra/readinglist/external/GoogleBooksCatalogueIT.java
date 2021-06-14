package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.CatalogueException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBooksCatalogueIT {

    @Test
    void should_find_five_books_for_a_common_search_query() throws CatalogueException {
        var catalogue = new GoogleBooksCatalogue(new GoogleBookAdapter());

        var foundBooks = catalogue.find("1984");

        then(foundBooks).hasSize(5);
    }
}
