package com.snapchat.example;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.snapengine.annotation.SnapletModule;

@SnapletModule
public class WebappExampleModule extends ServletModule {
    @Override
    public void configureServlets() {
        // Configure the servlet
        serve("/*").with(WebappExampleServlet.class);
        bind(WebappExampleServlet.class).in(Singleton.class);
    }
}
