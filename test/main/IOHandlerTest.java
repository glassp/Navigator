package main;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOHandlerTest {

    @Test
    public void pathToBin() {
        IOHandler ioHandler = new IOHandler();
        assertEquals("/home/otakupasi/IdeaProjects/Navigator/bin/", ioHandler.pathToBin());
    }
}
