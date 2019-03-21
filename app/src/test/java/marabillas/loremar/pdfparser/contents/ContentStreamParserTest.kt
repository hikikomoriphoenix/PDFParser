package marabillas.loremar.pdfparser.contents

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ContentStreamParserTest {
    @Test
    fun testGetNextToken() {
        var s = "/HelloWorld(Hi)"
        var token = ContentStreamParser.getNextToken(s)
        assertThat(token, `is`("/HelloWorld"))

        s = "12 0 R /Goodbye"
        token = ContentStreamParser.getNextToken(s)
        assertThat(token, `is`("12 0 R"))

        s = "Tf\n(Hello World)"
        token = ContentStreamParser.getNextToken(s)
        assertThat(token, `is`("Tf"))

        s = "1.2 3.4"
        token = ContentStreamParser.getNextToken(s)
        assertThat(token, `is`("1.2"))

        s = "ET"
        token = ContentStreamParser.getNextToken(s)
        assertThat(token, `is`("ET"))
    }
}