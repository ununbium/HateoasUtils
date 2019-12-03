package rocks.spiffy.spring.hateoas.utils.resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;

import java.util.Map;
import java.util.Optional;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Andrew Hill
 */
public class ExtendedResourceSupportTest {

    @Test
    public void testNullableLinkPresent() {
        //given
        ExtendedRepresentationModel e = new ExtendedRepresentationModel();
        String mockUri = "http://spam.spam:9090";

        Link link = new Link(mockUri, "spam");
        
        e.add(link);

        //when
        Optional<String> spam = e.ofNullableLink("spam");

        //then
        assertTrue(spam.isPresent());
        assertThat(spam.get(), is(mockUri));
    }

    @Test
    public void testNullableLinkNotPresent() {
        //given
        ExtendedRepresentationModel e = new ExtendedRepresentationModel();
        Link link = mock(Link.class);
        when(link.getRel()).thenReturn(LinkRelation.of("spam"));
        String mockUri = "http://spam.spam:9090";
        when(link.getHref()).thenReturn(mockUri);

        e.add(link);

        //when
        Optional<String> spam = e.ofNullableLink("spam2");

        //then
        Assert.assertFalse(spam.isPresent());
    }

    @Test
    public void test_EmbeddedIsInitialisedEmpty() {
        //given
        ExtendedRepresentationModel e = new ExtendedRepresentationModel();

        //when
        Map<String, Object> embedded = e.get_embedded();

        //then
        assertTrue(embedded.isEmpty());
    }

    @Test
    public void test_EmbeddedIsAdded() {
        //given
        ExtendedRepresentationModel e = new ExtendedRepresentationModel();
        String embeddedKey = "testEntry";
        Object embeddedValue = mock(Object.class);
        e.addEmbedded(embeddedKey, embeddedValue);

        //when
        Map<String, Object> embedded = e.get_embedded();

        //then
        assertThat(embedded.size(), is(1));
        assertThat(embedded.get(embeddedKey), is(embeddedValue));
    }


    @Test
    public void test_EmbeddedIsReplaced() {
        //given
        ExtendedRepresentationModel e = new ExtendedRepresentationModel();
        String embeddedKey = "testEntry";
        Object embeddedValue = mock(Object.class);
        Object embeddedValueReplacement = mock(Object.class);
        e.addEmbedded(embeddedKey, embeddedValue);
        e.addEmbedded(embeddedKey, embeddedValueReplacement);

        //when
        Map<String, Object> embedded = e.get_embedded();

        //then
        assertThat(embedded.size(), is(1));
        assertThat(embedded.get(embeddedKey), is(embeddedValueReplacement));
    }


}