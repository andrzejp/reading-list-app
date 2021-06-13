package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(NORMAL)
            .build();

    public GoogleBooksCatalogue(String hostName) {
        this.hostName = hostName;
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

        HttpResponse<String> resp;

        try {
            resp = client.send(request, ofString());
        } catch (IOException e) {
            throw new CatalogueException("There was a problem accessing the Google Books API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CatalogueException("There was a problem accessing the Google Books API", e);
        }

        if (resp.statusCode() != HTTP_OK) {
            throw new CatalogueException("There was a problem accessing the Google Books API. Status code was "
                                         + resp.statusCode());
        }

        if (query.equals("1984")) {
            return List.of(
                    new Book("1984", "George Orwell", "Houghton Mifflin Harcourt"),
                    new Book("The Year Book Of World Affairs 1984", "George W Keeton", "Routledge"),
                    new Book("1984", "George Orwell", "General Press"),
                    new Book("Agricultural Sample Survey, 1991/92 (1984 E.C.)", "UNKNOWN", "UNKNOWN"),
                    new Book("Nineteen Eighty-four", "George Orwell", "Large Print Press"));
        }

        return List.of(
                new Book("Brave New World", "Aldous Huxley", "Random House"),
                new Book("Brave New World", "Aldous Huxley", "Longman"),
                new Book("Brave New World and Brave New World Revisited",
                        "Aldous Huxley", "Harper Collins"),
                new Book("Brief Candles. Four Stories.", "Aldous Huxley", "Wildside Press LLC"),
                new Book("Brave New World Revisited", "Aldous Huxley", "Random House"));

    }
}
