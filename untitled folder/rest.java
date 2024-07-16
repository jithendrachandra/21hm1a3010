package com.example.averagecalculator;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/numbers")
public class AverageCalculatorController {

    private final int WINDOW_SIZE = 10;
    private Map<String, List<Integer>> numberStore = new HashMap<>();

    @GetMapping("/{numberId}")
    public Map<String, Object> getNumbers(@PathVariable String numberId) {
        List<Integer> numbers = fetchNumbers(numberId);
        List<Integer> storedNumbers = numberStore.getOrDefault(numberId, new LinkedList<>());
        
        if (storedNumbers.size() >= WINDOW_SIZE) {
            storedNumbers.remove(0);
        }
        storedNumbers.addAll(numbers);
        storedNumbers = storedNumbers.stream().distinct().toList();
        
        numberStore.put(numberId, storedNumbers);
        
        double average = storedNumbers.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        Map<String, Object> response = new HashMap<>();
        response.put("numbers", storedNumbers);
        response.put("average", average);
        return response;
    }

    private List<Integer> fetchNumbers(String numberId) {
        // Implement logic to fetch numbers from third-party API
        // For now, returning dummy data
        return List.of(1, 2, 3, 4, 5);
    }
}