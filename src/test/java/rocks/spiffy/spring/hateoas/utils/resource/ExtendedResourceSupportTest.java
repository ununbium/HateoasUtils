package rocks.spiffy.spring.hateoas.utils.resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.hateoas.Link;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Andrew Hill
 */
public class ExtendedResourceSupportTest {

    @Test
    public void testNullableLinkPresent() {
        //given
        ExtendedResourceSupport e = new ExtendedResourceSupport();
        Link link = mock(Link.class);
        when(link.getRel()).thenReturn("spam");
        String mockUri = "http://spam.spam:9090";
        when(link.getHref()).thenReturn(mockUri);

        e.add(link);

        //when
        Optional<String> spam = e.ofNullableLink("spam");

        //then
        Assert.assertTrue(spam.isPresent());
        Assert.assertThat(spam.get(), is(mockUri));
    }

    @Test
    public void testNullableLinkNotPresent() {
        //given
        ExtendedResourceSupport e = new ExtendedResourceSupport();
        Link link = mock(Link.class);
        when(link.getRel()).thenReturn("spam");
        String mockUri = "http://spam.spam:9090";
        when(link.getHref()).thenReturn(mockUri);

        e.add(link);

        //when
        Optional<String> spam = e.ofNullableLink("spam2");

        //then
        Assert.assertFalse(spam.isPresent());
    }


}