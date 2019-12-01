package de.openmail

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class OpenmailApplicationTests {

	@Test
	fun contextLoads() {
        print("Hallo Tests laufen!")
	}

    @Test
    fun secoundTest() {
        print("der 2te test läuft auch\n Yey" )
    }

}
