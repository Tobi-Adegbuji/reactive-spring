package com.learnreactivespring.demo.fluxandmonoplayground;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                //.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After Error"))
                .log(); //Will log all the methods that occurred in the console

        //Imagine the flux above is an event stream coming from a database.
        //The only way to access the elements from the flux is to subscribe to it.
        //Once you subscribe that is when the flux will still start providing values to the subscriber one by one.
        //Wit=hin subscribe method you can choose what to do if error is retrieved from flux.

        stringFlux
                .subscribe(System.out::println, //What to do with each event retrieved
                        e -> System.err.println("Exception is: " + e), //What to do on error
                        () -> System.out.println("Completed")); //What to do on complete

    }

    @Test
    @DisplayName("Test flux elements for happy path")
    void fluxTestElementsWithoutError() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete(); //Verify Complete is equivalent to subscribe method.
    }

    @Test
    @DisplayName("Test flux elements for unhappy path")
    void fluxTestElementsWithError() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .expectError(RuntimeException.class) //Expecting an error
                //.expectErrorMessage("Exception Occurred") //Asserting error message
                .verify(); //Verify is equivalent to subscribe method.
    }

    @Test
    @DisplayName("Test flux elements for unhappy path Variation")
    void fluxTestElementsWithErrorVariation() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring","Spring Boot","Reactive Spring") //One liner assertion
                .expectError(RuntimeException.class) //Expecting an error
                .verify();
    }
}
