package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.URI.create;
import static java.net.URLEncoder.encode;
import static java.net.http.HttpClient.Redirect.NORMAL;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.nio.charset.StandardCharsets.UTF_8;

public class GoogleBooksCatalogue implements Catalogue {

    private final String hostName;
    private final JsonbAdapter<ArrayList<Book>, JsonObject> bookAdapter;
    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(NORMAL)
            .build();

    public GoogleBooksCatalogue(String hostName, JsonbAdapter<ArrayList<Book>, JsonObject> bookAdapter) {
        this.hostName = hostName;
        this.bookAdapter = bookAdapter;
    }

    @Override
    public List<Book> find(String query) throws CatalogueException {
        if (query.isBlank()) {
            return List.of();
        }

        HttpRequest request = newBuilder(
                create("http://" + hostName + "/books/v1/volumes?maxResults=5&printType=books&fields=items/volumeInfo(title,authors,publisher)&q="
                       + encode(query, UTF_8)))
                .GET()
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, ofString());
        } catch (IOException e) {
            throw new CatalogueException("There was a problem accessing the Google Books API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CatalogueException("There was a problem accessing the Google Books API", e);
        }

        if (response.statusCode() != HTTP_OK) {
            throw new CatalogueException("There was a problem accessing the Google Books API. Status code was "
                                         + response.statusCode());
        }

        JsonbConfig config = new JsonbConfig().withAdapters(bookAdapter);
        Jsonb jsonb = JsonbBuilder.create(config);

        return jsonb.fromJson(response.body(), new ArrayList<Book>(){}.getClass().getGenericSuperclass());
    }
}
