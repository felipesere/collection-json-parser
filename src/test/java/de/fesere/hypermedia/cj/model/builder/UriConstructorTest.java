package de.fesere.hypermedia.cj.model.builder;

import org.junit.Test;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UriConstructorTest {

    URI base = URI.create("http://base.com");
    URI baseWithSlash = URI.create("http://base.com/");

    @Test
    public void test_simpleBase_baseIsNotChanged() {
        UriConstructor constructor = new UriConstructor(base);

        assertThat(base, is(equalTo(constructor.getBase())));
    }

    @Test
    public void test_simpleBase_relativURLwithStartingSlash() {
        UriConstructor constructor = new UriConstructor(base);

        URI result = constructor.buildAbsoluteHrefFromRelative("/foo");
        assertThat(URI.create("http://base.com/foo"), is(equalTo(result)));
    }

    @Test
    public void test_simpleBase_relativURLwithoutStartingSlash() {
        UriConstructor constructor = new UriConstructor(base);

        URI result = constructor.buildAbsoluteHrefFromRelative("foo");
        assertThat(URI.create("http://base.com/foo"), is(equalTo(result)));
    }

    @Test
    public void test_baseWithTrailingSlash_relativURLwithStartingSlash() {
        UriConstructor constructor = new UriConstructor(baseWithSlash);

        URI result = constructor.buildAbsoluteHrefFromRelative("/foo");
        assertThat(URI.create("http://base.com/foo"), is(equalTo(result)));
    }

    @Test
    public void test_baseWithTrailingSlash_relativURLwithoutStartingSlash() {

        UriConstructor constructor = new UriConstructor(baseWithSlash);

        URI result = constructor.buildAbsoluteHrefFromRelative("foo");
        assertThat(URI.create("http://base.com/foo"), is(equalTo(result)));
    }



}
