package com.jpozarycki.sheetNodeConverter.controllers;

import org.junit.Before;
import play.mvc.Http;

import static org.mockito.Mockito.mock;

public abstract class AbstractControllerTest {

    @Before
    public void setUp() {
        setupHttpContext();
    }

    protected void setupHttpContext() {
        Http.Context context = mock(Http.Context.class);
        Http.Context.current.set(context);
    }
}
