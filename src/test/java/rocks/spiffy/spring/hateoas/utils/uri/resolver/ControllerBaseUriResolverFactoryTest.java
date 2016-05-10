package rocks.spiffy.spring.hateoas.utils.uri.resolver;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rocks.spiffy.spring.hateoas.utils.DummyController;

import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
* @author Andrew Hill
*/
public class ControllerBaseUriResolverFactoryTest {

    @Before
    public void before() {
        //mock the http request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testControllerUriFactory_OnePathVariable() {
        //arrange
        String petName = "Bob";
        ControllerBaseUriResolverFactory factory =
                new ControllerBaseUriResolverFactory( methodOn(DummyController.class).findOne(petName) );

        //act
        ControllerUriResolver build = factory.build();

        //assert
        assertThat(build.getUriTemplate().toString(), is("dummy/{identifier}"));
        List<PathVariable> pathVariables = build.getPathVariables();
        assertThat(pathVariables.size(), is(1));
        assertThat(pathVariables.get(0).value(), is("identifier"));
    }

    @Test
    public void testControllerUriFactory_TwoPathVariables() {
        //arrange
        ControllerBaseUriResolverFactory factory =
                new ControllerBaseUriResolverFactory( methodOn(DummyController.class).findOnesPet("Harold", "Bob") );

        //act
        ControllerUriResolver build = factory.build();

        //assert
        assertThat(build.getUriTemplate().toString(), is("dummy/{identifier}/pet/{petName}"));
        List<PathVariable> pathVariables = build.getPathVariables();
        assertThat(pathVariables.size(), is(2));
        assertThat(pathVariables.get(0).value(), is("identifier"));
        assertThat(pathVariables.get(1).value(), is("petName"));
    }

    @Test
    public void testControllerUriFactory_OneRequestParam() {
        //arrange
        ControllerBaseUriResolverFactory factory =
                new ControllerBaseUriResolverFactory( methodOn(DummyController.class).searchByName("Harold") );

        //act
        ControllerUriResolver build = factory.build();

        //assert
        assertThat(build.getUriTemplate().toString(), is("dummy/search/byName"));
        List<RequestParam> requestParameters = build.getRequestParameters();
        assertThat(requestParameters.size(), is(1));
        assertThat(requestParameters.get(0).value(), is("name"));
    }



}