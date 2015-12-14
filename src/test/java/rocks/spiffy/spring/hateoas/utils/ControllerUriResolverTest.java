package rocks.spiffy.spring.hateoas.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * ControllerUriResolver
 *
 * @author Andrew Hill
 */
public class ControllerUriResolverTest
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void testFactoryMethodRejectString() {
        ControllerUriResolver.on("hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactoryMethodRejectUnproxiedController() {
        //given
        DummyController controller = new DummyController();

        //when
        ControllerUriResolver.on(controller);
    }

    @Test
    public void testFactoryMethodAcceptProxiedController() {
        //given

        //when
        ControllerUriResolver on = ControllerUriResolver.on(
                methodOn(DummyController.class).findOne(null));

        //then
        assertNotNull(on);
    }

    @Test
    public void testSingleParameterFound() {
        //given
        ControllerUriResolver on = ControllerUriResolver.on(
                methodOn(DummyController.class).findOne(null));

        //when
        Map<String, String> params = on.resolve("http://spam.com/dummy/5");

        //then
        assertThat(params.size(), is(1));
        assertThat(params.get("identifier"), is("5"));
    }

    @Test
    public void testSingleParameterFoundDirect() {
        //given
        ControllerUriResolver on = ControllerUriResolver.on(
                methodOn(DummyController.class).findOne(null));

        //when
        String identifier = on.resolve("http://spam.com/dummy/5", "identifier");

        //then
        assertThat(identifier, is("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSingleParameterNotFoundDirect() {
        //given
        ControllerUriResolver on = ControllerUriResolver.on(
                methodOn(DummyController.class).findOne(null));

        //when
        on.resolve("http://spam.com/dummy/5", "potato");
    }

    @Test
    public void testMultipleParametersFound() {
        //given
        ControllerUriResolver on = ControllerUriResolver.on(
                methodOn(DummyController.class).findOnesPet(null, null));

        //when
        Map<String, String> params = on.resolve("http://spam.com/dummy/5/pet/bob");

        //then
        assertThat(params.size(), is(2));
        assertThat(params.get("identifier"), is("5"));
        assertThat(params.get("petName"), is("bob"));
    }
}
