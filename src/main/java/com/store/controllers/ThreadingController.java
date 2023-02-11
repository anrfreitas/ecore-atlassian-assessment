package com.store.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.store.entities.Customer;
import com.store.services.ThreadingService;

@RestController
@RequestMapping("threading")
public class ThreadingController {

    @Autowired
    private Flyway flyway;

    @Autowired
    private ThreadingService threadingService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/customers")
    public void saveCustomers(@RequestParam(value = "files") MultipartFile[] files) {
        this.cleanDatabase();

        if (files == null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "File has to be Present");

        try {
            for(MultipartFile file: files) {
                threadingService.saveCustomer(file.getInputStream());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customers")
    public @ResponseBody ResponseEntity  getAllCustomers() throws Exception { // CompletableFuture<ResponseEntity>

        CompletableFuture<List<Customer>> c1 = threadingService.getAllCustomers();
        CompletableFuture<List<Customer>> c2 = threadingService.getAllCustomers();
        CompletableFuture<List<Customer>> c3 = threadingService.getAllCustomers();

        CompletableFuture.allOf(c1, c2, c3).join();

        return ResponseEntity.status(HttpStatus.OK).build();

        // return threadingService
        //     .getAllCustomers()
        //     .<ResponseEntity>thenApply(ResponseEntity::ok)
        //     .exceptionally(handleGetCarFailure);
    }

    private static Function<Throwable, ResponseEntity<? extends List<Customer>>> handleGetCarFailure = throwable -> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

    private void cleanDatabase() {
        flyway.clean();
        flyway.migrate();
    }
}
