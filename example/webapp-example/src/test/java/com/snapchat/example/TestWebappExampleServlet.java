package com.snapchat.example;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * An example of unit testing your application
 */
public class TestWebappExampleServlet {

    @Test
    public void getString() {
        WebappExampleServlet servlet = new WebappExampleServlet();
        String string = servlet.getOutputString();
        
        assertTrue(string.indexOf("Hello, world!") >= 0);
    }

    @Test
    public void getStringWithJenkinsProperty() {
        // temporarily back up system property
        String oldProperty = System.getProperty("JENKINS");
        System.setProperty("JENKINS", "DJ_Khaled");

        WebappExampleServlet servlet = new WebappExampleServlet();
        String string = servlet.getOutputString();

        // restore system property
        if(oldProperty == null) {
            System.clearProperty("JENKINS");
        } else {
            System.setProperty("JENKINS", oldProperty);
        }
        
        assertTrue(string.indexOf("DJ_Khaled") >= 0);
    }
}
