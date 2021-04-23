package com.learnreactivespring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    //The browser acts as a subscriber asking for the data
    //By default the browser is a blocking client
    @GetMapping("flux" )
    public ResponseEntity<Flux<Integer>> getFlux(){

        Flux<Integer> flux = Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1)) //
                .log();

        return new ResponseEntity<>(flux, HttpStatus.OK);
    }

    //Informs the client, that what this enc point produces is a stream
    @GetMapping(value = "fluxStream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public ResponseEntity<Flux<Integer>> getFluxStream(){

        Flux<Integer> flux = Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1)) //
                .log();

        return new ResponseEntity<>(flux, HttpStatus.OK);
    }
}
