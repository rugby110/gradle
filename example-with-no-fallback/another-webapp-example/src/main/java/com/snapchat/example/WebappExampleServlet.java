package com.snapchat.example;

import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebappExampleServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(WebappExampleServlet.class);

    @Override
    public void init() {
        logger.info("init");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }

    @Override
    public void doPost (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        logger.info("{} {} {} {}", "POST", req.getRequestURI(), req.getRemoteAddr(), req.getRemoteUser());
        doGet (req, res);
    }

    @Override
    public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        logger.info("{} {} {} {}", "GET", req.getRequestURI(), req.getRemoteAddr(), req.getRemoteUser());
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println(getOutputString());
        out.close();
    }

    @VisibleForTesting String getOutputString() {
        String buildNumber = System.getProperty("JENKINS", "");
        return "<html><body>Hello, world! " + buildNumber + "</body></html>";
    }
}
