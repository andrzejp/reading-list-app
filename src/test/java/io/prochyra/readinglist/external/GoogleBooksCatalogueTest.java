package io.prochyra.readinglist.external;

import io.prochyra.readinglist.WireMockTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBooksCatalogueTest extends WireMockTest {

    @Test
    void should_return_an_empty_list_for_an_empty_query() {
        GoogleBooksCatalogue catalogue = new GoogleBooksCatalogue("");
        then(catalogue.find("")).isEmpty();
    }
}