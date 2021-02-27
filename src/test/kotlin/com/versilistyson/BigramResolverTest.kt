package com.versilistyson

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BigramResolverTest {

    private val bigramResolver = BigramResolver()

    @Nested
    @DisplayName("When calling convertToHistogram")
    inner class WhenCallingConvertToHistogram {
        @Nested
        @DisplayName("And content is empty")
        inner class AndContentIsEmpty {
            @Test
            fun `Should return empty`() {
                // GIVEN
                val expected = mutableMapOf<String, Int>()
                val content = listOf<String>()

                // WHEN
                val actual = bigramResolver.convertToHistogram(content)


                // THEN
                assertEquals(expected, actual)
            }
        }

        @Nested
        @DisplayName("And passed sample test case")
        inner class AndPassedSampleTestCase {
            @Test
            fun `Should return expected histogram`() {
                // GIVEN
                val expected =
                    mutableMapOf(
                        "the quick" to 2,
                        "quick brown" to 1,
                        "brown fox" to 1,
                        "fox and" to 1,
                        "and the" to 1,
                        "quick blue" to 1,
                        "blue hare" to 1,
                    )

                val content = listOf("the", "quick", "brown", "fox", "and", "the", "quick", "blue", "hare")

                // WHEN
                val actual = bigramResolver.convertToHistogram(content)

                // THEN
                assertEquals(expected, actual)
            }
        }

        @Nested
        @DisplayName("And passed content that includes non letters")
        inner class AndPassedContentThatIncludesNonLetters {
            @Test
            fun `Should successfully parse histogram ignoring special characters`() {
                // GIVEN
                val expected =
                    mutableMapOf(
                        "hi world" to 1,
                        "world hello" to 1,
                        "hello there" to 2,
                        "there hello" to 1
                    )

                val content =
                    listOf("h5445i", "world03-0", "hel$()@*lo", "ther)#(#e!", "h844090328ello", "t03993290here.")

                // WHEN
                val actual = bigramResolver.convertToHistogram(content)

                // THEN
                assertEquals(expected, actual)
            }
        }

        @Nested
        @DisplayName("And passed content that includes capitals")
        inner class AndPassedContentThatIncludesCapitals {
            @Test
            fun `Should successfully parse histogram with bigrams listed in all lowercase`() {
                // GIVEN
                val content = listOf("lEtS", "TRY", "thIS")
                val expected =
                    mutableMapOf(
                        "lets try" to 1,
                        "try this" to 1
                    )

                // WHEN
                val actual = bigramResolver.convertToHistogram(content)

                // THEN
                assertEquals(expected, actual)
            }
        }
    }
}
