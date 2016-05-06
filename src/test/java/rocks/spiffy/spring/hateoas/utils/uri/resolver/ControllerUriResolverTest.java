package rocks.spiffy.spring.hateoas.utils.uri.resolver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rocks.spiffy.spring.hateoas.utils.DummyController;
import rocks.spiffy.spring.hateoas.utils.uri.resolver.ControllerUriResolver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Andrew Hill
 */
public class ControllerUriResolverTest
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        //mock the http request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

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
        Map<String, String> params = on.resolve("http://localhost/dummy/5");

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
        Optional<String> identifier = on.resolve("http://spam.com/dummy/5", "identifier");

        //then
        assertTrue(identifier.isPresent());
        assertThat(identifier.get(), is("5"));
    }

    @Test
    public void testSingleParameterNotFoundDirect() {
        //given
        ControllerUriResolver on = ControllerUriResolver.on(
                methodOn(DummyController.class).findOne(null));

        //when
        Optional<String> identifier = on.resolve("http://spam.com/dummy/5", "identifier Not There!");

        //then
        assertFalse(identifier.isPresent());
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

    @Test
    public void testGetParameters_MultipleParametersFound() {
        //given
        ControllerUriResolver on = ControllerUriResolver.on(
                methodOn(DummyController.class).findOnesPet(null, null));

        //when
        List<PathVariable> pathVariables = on.getPathVariables();

        //then
        assertThat(pathVariables.size(), is(2));

        assertThat(pathVariables.get(0).value(), is("identifier"));
        assertThat(pathVariables.get(1).value(), is("petName"));
    }


}
