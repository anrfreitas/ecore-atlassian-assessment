package com.store.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.HelloWorldDTO;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    Flyway flyway;

    @Value("${spring.devtools.remote.secret}")
    private String springDevtoolsRemoteSecret;

    @GetMapping("/world")
    public String getTest() {
        return "It works!";
    }

    @PostMapping("/world")
    public HelloWorldDTO postTest(@Valid @RequestBody(required = true) HelloWorldDTO payload) {
        // Validating @RequestBody
        return payload;
    }

    @PutMapping("/world")
    public Map<String, Object> putTest(@RequestBody Map<String, Object> payload) {
        // Not validating @RequestBody
        return payload;
    }

    @GetMapping("/reset")
    public String doReset() {
        this.resetDatabase();
        return "Database was resetted!";
    }

    @GetMapping("/ops")
    public JSONObject executeOps() {
        String base = "Andre Nivaldo Ribeiro de Freitas";
        String baseWS = "Andre Nivaldo          Ribeiro de        Freitas";
        JSONObject response = new JSONObject();
        String numberRegex = "^[-+]?\\d*[.]?\\d+|^[-+]?\\d+[.]?\\d*";
        String number = "1000000.00";

        // String Ops
        JSONObject strOps = new JSONObject();
        strOps.put("substring(0, 5)", base.substring(0, 5));
        strOps.put("toLowerCase", base.toLowerCase());
        strOps.put("toUpperCase", base.toUpperCase());
        strOps.put("toCharArray", base.toCharArray());
        strOps.put("replaceAll", base.replaceAll("Andre", "Antonio"));
        strOps.put("trim-like", baseWS.replaceAll("\\s+", " "));
        strOps.put("String.join(sep, str)", String.join(";", base.split(" ")));
        strOps.put("split", base.split(" "));
        strOps.put("Pattern.matches(regex, value)", Pattern.matches(numberRegex, number));
        strOps.put("envVariable (devtools-secret)", springDevtoolsRemoteSecret);

        // Array Ops
        JSONObject arrOps = new JSONObject();
        arrOps.put("arr.length", base.toCharArray().length);

        // Response
        response.put("strOps", strOps);
        response.put("arrOps", arrOps);

        System.out.println("");
        System.out.println("## Collection Ops");
        List<String> lista = Arrays.asList(base.split(" ")); // Converting array to List (Collection)
        Collections.sort(lista, Collections.reverseOrder()); // Sorting reverse (Collection)
        // lista.sort(null); // Sorting onwards (Collection)
        lista.stream().filter(item -> !item.equals("Andre")).forEach(System.out::println); // Filtering and printing

        System.out.println("");
        System.out.println("## Array Ops");
        Integer[] numbers = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Arrays.sort(numbers, Collections.reverseOrder()); // Sorting reverse (Array)
        // Arrays.sort(numbers); // Sorting onwards (Array)
        List<Integer> listaNum = Arrays.asList(numbers); // Converting array to List (Collection)
        listaNum.stream().filter(item -> item > 3).forEach(System.out::println); // Filtering and printing

        System.out.println("");
        System.out.println("## Concating two collections");
        List<Integer> lm = new ArrayList<Integer>();
        Integer[] numbers2 = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> listaNum2 = Arrays.asList(numbers2); // Converting array to List (Collection)
        lm = Stream.concat(listaNum2.stream(), lm.stream()).collect(Collectors.toList());
        lm.add(10);                                 // Adding new element
        lm.add(11);                                 // Adding new element
        lm.add(12);                                 // Adding new element
        lm.add(1, 99);                              // Adding new element on index 1
        lm.remove(lm.size() - 1);                   // Removing the last element
        lm.remove(0);                               // Removing first element
        lm.stream().forEach(System.out::println);   // Filtering and printing

        System.out.println("First element = " + lm.get(0));
        System.out.println("Last element = " + (lm.size() - 1));

        // lista.forEach(item -> System.out.println(item));
        // lista.forEach(System.out::println);

        // for (String item: lista) {
        //     System.out.println(item);
        // }

        return response;
    }

    private void resetDatabase() {
        // It would be useful to use before Each execution on an unit tests file!
        flyway.clean(); // Cleaning database
        flyway.migrate(); // Migrating stuff
    }
}
