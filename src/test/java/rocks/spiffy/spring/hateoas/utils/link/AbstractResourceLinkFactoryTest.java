package rocks.spiffy.spring.hateoas.utils.link;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.Link;

import java.net.URI;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Andrew Hill
 */
public class AbstractResourceLinkFactoryTest {

    @Test
    public void testGetLinkForIdentifier() {
        //given
        ResourceLinkFactory linkFactory = Mockito.mock(AbstractResourceLinkFactory.class);

        String identifier = "identifier";
        String relation = "relation";
        Link mockLink = mock(Link.class);
        Link mockRelationLink = mock(Link.class);

        when(linkFactory.getLinkForIdentifier(identifier)).thenReturn(mockLink);
        when(mockLink.withRel(relation)).thenReturn(mockRelationLink);

        doCallRealMethod().when(linkFactory).getLinkForIdentifier(relation, identifier);

        //when
        Link linkForIdentifier = linkFactory.getLinkForIdentifier(relation, identifier);

        //then
        assertThat(linkForIdentifier, is(mockRelationLink));
    }

    @Test
    public void testGetUriForIdentifier() {
        //given
        ResourceLinkFactory linkFactory = Mockito.mock(AbstractResourceLinkFactory.class);

        String identifier = "identifier";

        Link aLink = mock(Link.class);
        String mockUri = "http://this.is.a.test:8080";
        when(aLink.getHref()).thenReturn(mockUri);
        when(linkFactory.getLinkForIdentifier(identifier)).thenReturn(aLink);

        doCallRealMethod().when(linkFactory).getUriForIdentifier(identifier);

        //when
        Optional<URI> uriForIdentifier = linkFactory.getUriForIdentifier(identifier);

        //then
        assertThat(uriForIdentifier.isPresent(), is(true));
        assertThat(uriForIdentifier.get().toString(), is(mockUri));
    }

    @Test
    public void testGetUriForIdentifier_badUri() {
        //given
        ResourceLinkFactory linkFactory = Mockito.mock(AbstractResourceLinkFactory.class);

        String identifier = "identifier";

        Link aLink = mock(Link.class);
        String mockUri = "this is not a valid uri";
        when(aLink.getHref()).thenReturn(mockUri);
        when(linkFactory.getLinkForIdentifier(identifier)).thenReturn(aLink);

        doCallRealMethod().when(linkFactory).getUriForIdentifier(identifier);

        //when
        Optional<URI> uriForIdentifier = linkFactory.getUriForIdentifier(identifier);

        //then
        assertThat(uriForIdentifier.isPresent(), is(false));
    }

    @Test
    public void testGetUriForEntity() {
        //given
        String href = "http://some.url:8080";
        String someIdentifier = "someIdentifier";

        ResourceLinkFactory linkFactory = Mockito.mock(AbstractResourceLinkFactory.class);
        Object e = mock(Object.class);

        when(linkFactory.getIdentifierForEntity(e)).thenReturn(someIdentifier);
        URI uri = URI.create(href);
        Optional<URI> optUri = Optional.of(uri);
        when(linkFactory.getUriForIdentifier(someIdentifier)).thenReturn(optUri);

        doCallRealMethod().when(linkFactory).getUriForEntity(e);

        //when
        Optional<URI> uriForIdentifier = linkFactory.getUriForEntity(e);

        //then
        assertThat(uriForIdentifier.isPresent(), is(true));
        assertThat(uriForIdentifier.get(), is(uri));
    }
}