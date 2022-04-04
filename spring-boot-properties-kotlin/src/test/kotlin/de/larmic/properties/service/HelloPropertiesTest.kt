package de.larmic.properties.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class HelloPropertiesTest {

    @Autowired
    private lateinit var properties: HelloProperties

    @Test
    fun `assert properties`() {
        assertThat(properties.value).isEqualTo("test properties")
    }
}