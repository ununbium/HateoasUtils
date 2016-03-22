package rocks.spiffy.spring.hateoas.utils.uri;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriTemplate;
import rocks.spiffy.spring.hateoas.utils.DummyController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
        ControllerUriBuilder.controllerProvider = (value) -> controllerUriResolver;
    }

    @Test
    public void testLinkOnValidReference() {
        //given
        UriTemplate mockUriTemplate = mock(UriTemplate.class);
        when(mockUriTemplate.toString()).thenReturn("http://some_url/place:9090");
        when(controllerUriResolver.getUriTemplate()).thenReturn(mockUriTemplate);

        List<RequestParam> parameters = new ArrayList<>();

        RequestParam mockParameter1 = mock(RequestParam.class);
        when(mockParameter1.value()).thenReturn("abc");
        parameters.add(mockParameter1);

        RequestParam mockParameter2 = mock(RequestParam.class);
        when(mockParameter2.value()).thenReturn("def");
        parameters.add(mockParameter2);

        when(controllerUriResolver.getRequestParameters()).thenReturn(parameters);

        //when
        Link bob = ControllerUriBuilder.linkTo(methodOn(DummyController.class).findOne("bob"));

        //then
        assertThat(bob.getHref(), is("http://some_url/place:9090?abc={abc}&def={def}"));
        assertThat("self", is(bob.getRel()));
    }
}
