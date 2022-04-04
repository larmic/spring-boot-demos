package de.larmic.properties.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension

@ExtendWith(OutputCaptureExtension::class)
internal class HelloServiceTest {

    @Test
    fun testLogHelloProperties(output: CapturedOutput) {
        val helloService = HelloService(HelloProperties("hello"))

        helloService.logHelloProperties()

        assertThat(output.out.trim { it <= ' ' }).endsWith("Properties (hello.value): hello")
    }
}