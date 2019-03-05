package marabillas.loremar.pdfparser.objects

import java.io.RandomAccessFile

open class StreamObject(private val file: RandomAccessFile, private val start: Long) : PDFObject(file, start) {
    val dictionary = Dictionary(file, start)
}