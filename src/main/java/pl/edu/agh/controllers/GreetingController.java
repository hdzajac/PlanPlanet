package pl.edu.agh.controllers;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/nowy")
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Greeting sayHello(
            @RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
