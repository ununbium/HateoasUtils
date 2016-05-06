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
            @PathVariable("identifier") String identifier) {
        return ResponseEntity.ok("asd");
    }

    @RequestMapping(value="/{identifier}/pet/{petName}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity findOnesPet(
            @PathVariable("identifier") String identifier,
            @PathVariable("petName") String pet) {
        return ResponseEntity.ok("asd");
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity baseResource(){
        return ResponseEntity.ok("asd");
    }

    @RequestMapping(value="/search/byName", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity searchByName(
            @RequestParam("name") String name) {
        return ResponseEntity.ok("asd");
    }

    @RequestMapping(value="/search/byNameAndOwner", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity searchByNameAndOwner(
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value="owner", required = false) String owner) {
        return ResponseEntity.ok("asd");
    }

    @RequestMapping(value="/{userId}/search/byNameAndOwner", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity searchUserByNameAndOwner(
            @PathVariable("userId") String userId,
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value="owner", required = false) String owner) {
        return ResponseEntity.ok("asd");
    }

    @RequestMapping(value="/{userId}/search/byNameAndOwner", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity searchUserByNameAndOwnerStrict(
            @PathVariable("userId") String userId,
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="owner", required = true) String owner) {
        return ResponseEntity.ok("asd");
    }


}
