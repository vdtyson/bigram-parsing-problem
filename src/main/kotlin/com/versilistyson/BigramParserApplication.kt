package com.versilistyson

// run sample with flag -i src/test/resources/test-files/sample-case.txt
// -o src/test/resources/test-files/write.txt (optionally outputs to write.txt)
fun main(args: Array<String>) {
    runCatching {  createCLI().execute(args) }.onFailure { t ->  t.printStackTrace() }
}

fun createCLI(): ParserCLI {
    val resolver = BigramResolver()
    val fileManager = FileManager()
    return ParserCLI(resolver, fileManager)
}
