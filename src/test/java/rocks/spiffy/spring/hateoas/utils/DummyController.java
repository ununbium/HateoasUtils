package rocks.spiffy.spring.hateoas.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * A dummy controller stub for testing purposes
 *
 * @author Andrew Hill
 */
@RequestMapping("dummy")
@Controller
public class DummyController {

    @RequestMapping(value="/{identifier}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity findOne(
            @RequestParam("identifier") String identifier) {
        return null;
    }

    @RequestMapping(value="/{identifier}/pet/{petName}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity findOnesPet(
            @RequestParam("identifier") String identifier,
            @RequestParam("petName") String pet) {
        return null;
    }


}
