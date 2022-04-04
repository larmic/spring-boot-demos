package de.larmic.properties.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
class HelloServiceTest {

    @Test
    void testLogHelloProperties(final CapturedOutput output) {
        final var helloService = new HelloService(new HelloProperties("hello"));

        helloService.logHelloProperties();

        assertThat(output.getOut().trim()).endsWith("Properties (hello.value): hello");
    }
}