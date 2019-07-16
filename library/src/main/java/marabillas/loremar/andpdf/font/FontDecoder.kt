package marabillas.loremar.andpdf.font

import marabillas.loremar.andpdf.contents.PageObject
import marabillas.loremar.andpdf.contents.text.TextElement
import marabillas.loremar.andpdf.contents.text.TextObject
import marabillas.loremar.andpdf.document.AndPDFContext
import marabillas.loremar.andpdf.objects.*

internal class FontDecoder(
    private val context: AndPDFContext,
    private val pageObjects: ArrayList<PageObject>,
    private val fonts: HashMap<String, Font>
) {
    private val mainSB = StringBuilder()
    private val secondarySB = StringBuilder()

    fun decodeEncoded() {
        pageObjects
            .asSequence()
            .filter { it is TextObject }
            .forEach {
                val textObject = it as TextObject
                textObject.forEachIndexed forEachTextElement@{ i, e ->
                    mainSB.clear()
                    mainSB.append(e.tf, 0, e.tf.indexOf(' '))
                    val cmap = fonts[mainSB.toString()]?.cmap

                    var newTj: PDFObject? = null
                    when (e.tj) {
                        is PDFArray -> {
                            mainSB.clear().append('[')
                            e.tj.forEach { p ->
                                if (p is PDFString) {
                                    if (cmap != null) {
                                        mainSB.append(
                                            cmap.decodeString(p.original)
                                        )
                                    } else {
                                        mainSB.append(
                                            p.original
                                        )
                                    }
                                } else if (p is Numeric) {
                                    mainSB.append(p.value.toFloat())
                                }
                            }
                            mainSB.append(']')
                            newTj = mainSB.toPDFArray(context, secondarySB.clear(), -1, 0)
                        }
                        is PDFString -> {
                            if (cmap != null) {
                                newTj = cmap.decodeString(e.tj.original).toPDFString()
                            } else {
                                newTj = e.tj
                            }
                        }
                    }

                    val updated = TextElement(
                        td = e.td.copyOf(),
                        tj = newTj ?: e.tj,
                        tf = e.tf,
                        ts = e.ts,
                        rgb = e.rgb
                    )

                    textObject.update(updated, i)
                }
            }
    }
}