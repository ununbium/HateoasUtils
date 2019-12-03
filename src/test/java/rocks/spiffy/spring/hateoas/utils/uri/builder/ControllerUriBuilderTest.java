package rocks.spiffy.spring.hateoas.utils.uri.builder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rocks.spiffy.spring.hateoas.utils.DummyController;
import rocks.spiffy.spring.hateoas.utils.uri.resolver.ControllerUriResolver;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Andrew Hill
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerUriBuilderTest
{
    @Mock
    ControllerUriResolver controllerUriResolver;

    @Before
    public void setupTests() {
        //ControllerUriBuilder.controllerProvider = (value) -> controllerUriResolver;

        //mock the http request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testLinkTo_NoRequestParam_NoPathParam() {

        //when
        Link link = ControllerUriBuilder.linkTo(methodOn(DummyController.class).baseResource());

        //then
        assertThat(link.getHref(), is("http://localhost/dummy"));
    }

    @Test
    public void testLinkTo_NoRequestParam_OnePathParam() {

        //when
        Link link = ControllerUriBuilder.linkTo(methodOn(DummyController.class).findOne("bob"));

        //then
        assertThat(link.getHref(), is("http://localhost/dummy/bob"));
    }

    @Test
    public void testLinkTo_TwoRequestParam_NoPathParam() {

        //when
        Link link = ControllerUriBuilder.linkTo(methodOn(DummyController.class).searchByNameAndOwner("bob", "bobby"));

        //then
        assertThat(link.getHref(), is("http://localhost/dummy/search/byNameAndOwner{?name,owner}"));
    }

    @Test
    public void testLinkTo_TwoOptionalRequestParam_NoPathParam() {

        //when
        Link link = ControllerUriBuilder.linkTo(methodOn(DummyController.class).searchByNameAndOwner(null, null));

        //then
        assertThat(link.getHref(), is("http://localhost/dummy/search/byNameAndOwner{?name,owner}"));
    }

    @Test
    public void testLinkTo_TwoOptionalRequestParam_OnePathParam() {

        //when
        Link link = ControllerUriBuilder.linkTo(methodOn(DummyController.class).searchUserByNameAndOwner("asd", null, null));

        //then
        assertThat(link.getHref(), is("http://localhost/dummy/asd/search/byNameAndOwner{?name,owner}"));
    }

    @Test
    public void testLinkTo_TwoRequiredRequestParam_OnePathParam() {

        //when
        Link link = ControllerUriBuilder.linkTo(methodOn(DummyController.class).searchUserByNameAndOwnerStrict("asd", null, null));

        //then
        //TODO this is not RFC6570 compliant! should represent required arguments differently
        assertThat(link.getHref(), is("http://localhost/dummy/asd/search/byNameAndOwner{?name,owner}"));
    }
}
