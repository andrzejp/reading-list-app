# reading-list-app

![Build](https://github.com/andrzejp/reading-list-app/actions/workflows/build.yml/badge.svg)
![Integration and Acceptance](https://github.com/andrzejp/reading-list-app/actions/workflows/integration.yml/badge.svg)

A command line application for using the Google Books API.

To run with Apache Maven and Java 16:

Create an executable uber JAR with:

`$ mvn package`

and then run with

`$ java -jar target/reading-list-app-1.0-SNAPSHOT.jar`

## Requirements
The application should allow the user to:
- Type in a query and display a list of 5 books matching that query.
- Each item in the list should include the book's author, title, and publishing company
- A user should be able to select a book from the five displayed to save to a "Reading List"
- View a "Reading List" with all the books the user has selected from their queries -- this is a local reading list and not tied to Google Book's account features.

## Analysis
I choose to use Java, since I have most confidence with Java and   a little practice doing outside-in TDD. For the testing frameworks I choose JUnit 5, Mockito (in order to do outside-in TDD), WireMock (to stub the external API for testing) and AssertJ for fluent, readable assertions. I choose Apache Maven for the build tool because of my familiarity with it.

To access the Google Books API I study the documentation and examine Google's Java API client. I see that it is very general and quite complex. I believe that it would be better to simply use the Java HttpClient, at least to begin with. A properly decoupled architecture would allow me to write an alternative adapter class to use the Google client instead.

To process the API'S JSON representations I choose the Jakarta JSON Binding API with the Yasson implementation. This is now a standard that I would like to learn how to use and it is straightforward in the basic case.

I choose IntelliJ IDEA due to my familiarity with it.

## Process
1. Start a new Maven project using [my Maven archetype](https://github.com/andrzejp/tdd-starter/) that I keep updated with the latest dependency versions and a minimal POM.
2. I see that the requirements essentially specify two features:
    - Querying a book catalogue (Google Books) for a book
    - Maintaining a "Reading List"
3. I choose the first feature and write a high-level acceptance test. The requirement is for a command line interface but I decide to write the acceptance tests against against the UI's collaborators, due to the fragility of UI tests and the high-level nature of the given specifications. I use WireMock to supply a representative JSON payload.
4. I write the test describing the API that I would like to have  and use the IDE to generate the required classes, interfaces, and methods. I autogenerate an 'UnsupportedOperationException' throw for every method created in this way to drive the development process in the inner TDD loop.
5. I run the acceptance test and find that an 'UnsupportedOperationException' has been thrown by the 'find()' method of the book catalogue object. I now "park" the acceptance test and generate a test class for the Catalogue class.
6. I can now create a git repo and make my first commit. I take the opportunity to also start a very basic Continuous Delivery setup using GitHub Actions to run the unit acceptance and integration tests.
6. I test-drive an implementation of the Catalogue class, considering its desirable low-level behaviour and injecting mocks where a collaborator is required. I inject a corresponding instantiation of the corresponding actual class in the constructor used in the acceptance test.
7. I continue in this way until the first acceptance test passes.
8. I next write an acceptance test for the second feature and proceed as above.
9. Both acceptance tests pass and now I begin on the command-line interface. I test-drive the implementation, using the appropriate test-doubles.
10. I put the 'main' method in the `App` class, to assemble the system and start the CLI. I run it and perform a manual test. This guides my development of the UI. I complete a simple implementation.

## Future Work
The implementation that I achieved in the time is rather basic and there are a number of enhancements that I would like to work on next:

- Persist reading lists between runs - I would like to write a `FileBookRepository` to serialise to a file, for example.
- Searching by title/author specifically
- Generating native executable
- Disallowing adding a book to the reading list twice
- Considering the case when a book's author is unknown
- A friendlier way of dealing with API connection issues, i.e. we currently block for the default `HttpClient` timeout time. It would be better to give an indication that something is happening and reduce the timeout time or retry
- Check book choice input is in the right range
- Factor out menu functionality - displaying menus, accepting input could be a separate concern and actions performed by menu choices could be encapsulated, using the Command Pattern.
