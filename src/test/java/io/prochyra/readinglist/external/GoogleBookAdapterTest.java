package io.prochyra.readinglist.external;

import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBookAdapterTest {

    @Test
    void should_adapt_empty_json_object_to_empty_list() {
        GoogleBookAdapter adapter = new GoogleBookAdapter();
        Jsonb jsonb = JsonbBuilder.create();
        String emptyJsonObject = "{}";
        JsonObject json = jsonb.fromJson(emptyJsonObject, JsonObject.class);

        then(adapter.adaptFromJson(json)).isEmpty();
    }
}