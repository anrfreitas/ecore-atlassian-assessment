package com.store.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.configs.profiles.EnvironmentInterface;
import com.store.dto.HelloWorldDTO;
import com.store.services.implementations.HelloWorldService;
import com.store.services.scopes.HelloWorldPrototype;
import com.store.services.scopes.HelloWorldSingleton;

import net.minidev.json.JSONObject;

@Scope("prototype") // it won't be a singleton, we'll create a new instance for each call now
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/hello")
@PropertySource("classpath:application.properties") // just demonstrating how to load a different properties file :)
public class HelloController {

    @Autowired
    Flyway flyway;

    @Value("${spring.devtools.remote.secret}")
    private String springDevtoolsRemoteSecret;

    @Autowired
    @Qualifier("helloWorldServiceV1")
    private HelloWorldService hwServiceV1;

    @Autowired
    @Qualifier("helloWorldServiceV2")
    private HelloWorldService hwServiceV2;

    @Autowired
    private HelloWorldService hwServicePrimary;

    @Autowired
    private HelloWorldSingleton hwSingleton;

    @Autowired
    private HelloWorldPrototype hwPrototype;

    // Since there will be only one instance due @Profile settings, we don't need to apply @Qualifiers
    @Autowired
    private EnvironmentInterface environmentInterface;

    @GetMapping("/world")
    public String getTest() {
        System.out.println(environmentInterface.getHelloWorldMessage());
        return environmentInterface.getItWorksMessage();
    }

    @GetMapping("/world/v1")
    public String getTestV1() {
        return hwServiceV1.getMessage();
    }

    @GetMapping("/world/v2")
    public String getTestV2() {
        return hwServiceV2.getMessage();
    }

    @GetMapping("/world/primary")
    public String getTestPrimary() {
        return hwServicePrimary.getMessage();
    }

    @GetMapping("/world/scopes")
    public JSONObject getTestScopes() {
        JSONObject obj = new JSONObject();
        obj.put("Singleton", hwSingleton.getScopedValue());
        obj.put("Prototype", hwPrototype.getScopedValue());
        return obj;
    }

    @GetMapping("/world/profile")
    public String getTestProfile() {
        return environmentInterface.getEnvironment();
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

    @GetMapping("/world/challenge")
    public HashSet<Integer> doChallenge() {

        /*
            Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
            You may assume that each input would have exactly one solution, and you may not use the same element twice.
            You can return the answer in any order.

            Example 1
            Input: nums = [2,7,11,15], target = 9
            Output: [0,1]

            Example 2
            Input: nums = [3,2,4], target = 6
            Output: [1,2]
        */

        // int[] col = new int[]{2,7,11,15};
        // int target = 9;

        // List<Integer> set = new ArrayList<Integer>();
        // HashSet<Integer> hSet = new HashSet<Integer>();
        // HashSet<Integer> indexes = new HashSet<Integer>();

        // int i = 0;
        // for (i = 0; i < col.length; i++) {
        //     hSet.add(col[i]);
        //     int diff = target - col[i];
        //     if (diff > 0) set.add(diff);
        // }

        // HashSet<Integer> values = new HashSet<Integer>();
        // for (i = 0; i < set.size(); i++) {
        //     int cur = set.get(i);
        //     int diff = target - cur;
        //     if(hSet.contains(diff) && cur != diff) {
        //         values.add(cur);
        //         values.add(diff);
        //     }
        // }

        // for (i = 0; i < col.length; i++) {
        //     if (values.contains(col[i])) indexes.add(i);
        // }

        // return indexes;

        // #####################################################################################
        // Finding duplicate elements in the array

        // Integer[] list = new Integer[]{0,0,0,0,0,0,0,1,1,1,1,1,2,2,2,4,4,5,7,7,7,8,8,8};

        // # Solution 1 - Big O(n^2)
            // List<Integer> nonDuplicated = new ArrayList<Integer>();

            // for (Integer item: list) {
            //     if (!nonDuplicated.contains(item)) nonDuplicated.add(item);
            // }

        // # Solution 2 - Big O(n)
            // HashSet<Integer> nonDuplicated = new HashSet<Integer>();
            // for (Integer item: list) {
            //     nonDuplicated.add(item);
            // }

            // return nonDuplicated;

        // #####################################################################################
        // Finding elements that appears only once in the array - Big O(n log(n))

        // int arr[] = { 1,1,1,5,6,1,1,1 };
        // Arrays.sort(arr);
        // int len = arr.length;
        // int lastIndex = len - 1;
        // HashSet<Integer> uniqueElements = new HashSet<>();

        // // Ignoring the first and the last element, we'll check 'em later
        // for (int i = 1; i < lastIndex; i++)
        // {
        //     if (arr[i] != arr[i-1] && arr[i] != arr[i+1])
        //         uniqueElements.add(arr[i]);
        // }

        // // Checking the first element
        // if (arr[0] != arr[1]) uniqueElements.add(arr[0]);

        // // Checking the last element
        // if (arr[lastIndex] != arr[len - 2]) uniqueElements.add(arr[lastIndex]);

        // return uniqueElements;

        // #####################################################################################
        // Find first non-repeating element in a given Array - Big O(n)

        // int arr[] = { 9,9,2,3,4,2,5 };
        // int n = arr.length;

        // Map<Integer, Integer> m = new HashMap<>();
        // for (int i = 0; i < n; i++) {
        //     if (m.containsKey(arr[i])) {
        //         m.put(arr[i], m.get(arr[i]) + 1);
        //     }
        //     else {
        //         m.put(arr[i], 1);
        //     }
        // }

        // for (int i = 0; i < n; i++)
        //     if (m.get(arr[i]) == 1) {
        //         System.out.println(arr[i]);
        //         break;
        //     }
        // System.out.println(-1);
        // return null;

        // #####################################################################################
        // Find element in a given sorted Array - Big O(log n)

        int arr[] = { 1,2,3,4,5,6,7,8,9,10 };
        int input = 8;

        int mid = (int) Math.floor(arr.length / 2);
        int initPoint = 0;
        int maxPoint = arr.length - 1;

        if (arr[mid] == input) {
            System.out.println(input);
            return null;
        }
        else if (arr[mid] > input) {
            maxPoint = mid;
        }
        else {
            initPoint = mid + 1;
        }

        int steps = 0;
        for (int i = initPoint; i < maxPoint; i++) {
            steps++;
            if (arr[i] == input) {
                System.out.println("steps: " + steps);
                System.out.println(arr[i]);
                return null;
            }
        }

        return null;
    }
}
