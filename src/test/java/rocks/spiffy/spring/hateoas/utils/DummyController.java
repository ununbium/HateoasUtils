package rocks.spiffy.spring.hateoas.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
            @PathVariable("identifier") String identifier) {
        return null;
    }

    @RequestMapping(value="/{identifier}/pet/{petName}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity findOnesPet(
            @PathVariable("identifier") String identifier,
            @PathVariable("petName") String pet) {
        return null;
    }


}
