package com.versilistyson

class BigramResolver {

    /**
     * Converts lists of strings to bigram histogram.
     * Changes all characters to lower and removes any non letter characters.
     *
     * @param content the passed list
     * @return a histogram represented as a map of bigrams (String) to occurrences (Int)
     */
    fun convertToHistogram(content: List<String>): Map<String, Int> {

        val bigramToOccurrences = mutableMapOf<String, Int>()

        for (i in 0 until content.lastIndex) {
            val bigram = content.resolveBigram(i)
            bigramToOccurrences[bigram] = bigramToOccurrences.getOrDefault(bigram, 0) + 1
        }

        return bigramToOccurrences
    }

    private fun List<String>.resolveBigram(startIndex: Int): String {
        return "${resolveWordFromIndex(startIndex)} ${resolveWordFromIndex(startIndex + 1)}"
    }

    private fun List<String>.resolveWordFromIndex(index: Int): String {
        return this[index].toLowerCase().replace(Regex("[^a-zA-Z]+"), "")
    }
}
