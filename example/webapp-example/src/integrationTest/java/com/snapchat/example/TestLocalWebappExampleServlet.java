package com.snapchat.example;

import com.google.inject.Inject;
import com.snapengine.repackaged.org.apache.http.HttpResponse;
import com.snapengine.repackaged.org.apache.http.HttpStatus;
import com.snapengine.repackaged.org.apache.http.client.HttpClient;
import com.snapengine.repackaged.org.apache.http.client.methods.HttpGet;
import com.snapengine.repackaged.org.apache.http.impl.client.BasicResponseHandler;
import com.snapengine.testing.LocalSnapEngineLauncher;
import com.snapengine.testing.SnapEngineTestRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * An example of how to use the {@code LocalSnapEngineLauncher} to start and stop
 * your application in a test environment.
 */
public class TestLocalWebappExampleServlet {

    @ClassRule public static SnapEngineTestRule rule = new SnapEngineTestRule();

    @Inject private static LocalSnapEngineLauncher launcher;

    @Inject private static HttpClient httpClient;

    // You can add additional properties to your SnapEngine process during setup
    @BeforeClass
    public static void init() {
        launcher.withProperty("server.address", "http://127.0.0.1:0/").start();
    }

    // After all of your tests are done, you can tear down the server.
    @AfterClass
    public static void finish() {
        launcher.stop();
    }

    @Test 
    public void doGet() throws Exception {
        String responseBody = sendTestRequest("/test");
        assertTrue(responseBody.indexOf("Hello, world!") >= 0);
    }

    private String sendTestRequest(String endpoint) throws Exception {
        String url = launcher.getServerAddress() + endpoint;
        HttpGet get = new HttpGet(url);

        HttpResponse response = httpClient.execute(get);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        String body = new BasicResponseHandler().handleResponse(response);

        return body;
    } 
}
