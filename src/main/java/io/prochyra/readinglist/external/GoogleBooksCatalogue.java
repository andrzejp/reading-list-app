package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import jakarta.json.bind.JsonbConfig;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static jakarta.json.bind.JsonbBuilder.create;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.URI.create;
import static java.net.URLEncoder.encode;
import static java.net.http.HttpClient.Redirect.NORMAL;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.nio.charset.StandardCharsets.UTF_8;

public class GoogleBooksCatalogue implements Catalogue {

    private static final String PATH = "/books/v1/volumes";
    private static final String QUERY = "maxResults=5&printType=books&fields=items/volumeInfo(title,authors,publisher)&q=";
    private final String host;
    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(NORMAL)
            .build();
    private final JsonbConfig jsonbConfig;
    private final String scheme;
    private final int port;

    public GoogleBooksCatalogue(String scheme, String host, int port, GoogleBookAdapter bookAdapter) {
        this.host = host;
        jsonbConfig = new JsonbConfig().withAdapters(bookAdapter);
        this.scheme = scheme;
        this.port = port;
    }

    @Override
    public List<Book> find(String query) throws CatalogueException {
        if (query.isBlank())
            return List.of();

        HttpResponse<String> response = getResponse(query);
        checkStatusCode(response);
        return mapBooksFromJson(response.body());
    }

    private HttpResponse<String> getResponse(String query) throws CatalogueException {
        HttpRequest request = buildRequest(query);
        HttpResponse<String> response;

        try {
            response = client.send(request, ofString());
        } catch (IOException e) {
            throw new CatalogueException("There was a problem accessing the Google Books API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CatalogueException("There was a problem accessing the Google Books API", e);
        }
        return response;
    }

    private HttpRequest buildRequest(String searchTerms) {
        var uriString = format("%s://%s:%s%s?%s%s", scheme, host, port, PATH, QUERY, encode(searchTerms, UTF_8));
        return newBuilder(create(uriString))
                .GET()
                .build();
    }

    private void checkStatusCode(HttpResponse<String> response) throws CatalogueException {
        if (response.statusCode() != HTTP_OK) {
            throw new CatalogueException("There was a problem accessing the Google Books API. Status code was "
                                         + response.statusCode());
        }
    }

    private List<Book> mapBooksFromJson(String json) throws CatalogueException {
        try (var jsonb = create(jsonbConfig)) {
            return jsonb.fromJson(json, new ArrayList<Book>() {
            }.getClass().getGenericSuperclass());
        } catch (Exception e) {
            throw new CatalogueException("There was a problem deserializing the response from the Google Books API", e);
        }
    }
}
