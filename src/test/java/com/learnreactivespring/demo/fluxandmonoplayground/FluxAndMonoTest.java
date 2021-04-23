package com.learnreactivespring.demo.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){
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

}
