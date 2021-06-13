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
import static java.net.http.HttpClient.Redirect.NORMAL;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;

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
                create("http://" + hostName + "/books/v1/volumes?maxResults=5&printType=books&q=1984"))
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

        return List.of(
                new Book("1984", "George Orwell", "Houghton Mifflin Harcourt"),
                new Book("1984", "George Orwell", "Univ. Press of Mississippi"),
                new Book("Older Americans Act Amendments of 1984", "United States. Congress. House. Committee on Education and Labor. Subcommittee on Human Resources", "UNKNOWN"),
                new Book("International Study Missions", "United States. Congress. House. Select Committee on Narcotics Abuse and Control", "UNKNOWN"),
                new Book("Proceedings of The Academy of Natural Sciences Special Publication 14, 1984", "UNKNOWN", "Academy of Natural Sciences"));
    }
}
