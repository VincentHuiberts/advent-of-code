package nl.tintie.aoc

import org.jcodec.api.SequenceEncoder
import org.jcodec.scale.AWTUtil
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


class ImageWriter(
    val year: Int,
    val day: Int
) {
    val sequence = mutableMapOf<String, Int>()

    fun incrementSequence(name: String) = sequence.set(name, sequence.getOrDefault(name, 0) + 1)

    fun writeText(seriesName: String, lines: List<String>, preferredImageHeight: Int? = null, preferredImageWidth: Int? = null) {
        incrementSequence(seriesName)
        val outputFont = Font(Font.MONOSPACED, Font.PLAIN, 20)
        val (fontWidth, fontHeight) = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().run {
            font = outputFont
            fontMetrics.stringWidth("A") to fontMetrics.height.also { dispose() }
        }

        val imageWidth = preferredImageHeight ?: lines.map { it.length }.maxOrNull()!! * fontWidth
        val imageHeight = preferredImageWidth ?: lines.size * fontHeight

        val img = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB).apply {
            createGraphics().apply {
                font = outputFont
                color = Color.BLACK
                fillRect(0, 0, imageWidth, imageHeight)
                color = Color.GREEN
                lines.forEachIndexed { i, line ->
                    drawString(line, 0, fontMetrics.ascent + i * fontHeight)
                }
                dispose()
            }
        }

        val fileName = "Image-${sequence[seriesName].toString().padStart(10, '0')}.png"
        try {
            val outputFile = File("$OUTPUT_DIR/$year/$day/$seriesName/$fileName")
            outputFile.parentFile.mkdirs()
            ImageIO.write(img, "png", outputFile)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun imagesToVideo(seriesName: String) {
        val outputFile = File("$OUTPUT_DIR/$year/$day/$seriesName.mp4")
        val enc = SequenceEncoder.createSequenceEncoder(outputFile, 60)

        File("$OUTPUT_DIR/$year/$day/$seriesName/").listFiles()!!.sorted().forEach { frame ->
            val bufferedImage: BufferedImage = ImageIO.read(frame);
            enc.encodeNativeFrame(AWTUtil.fromBufferedImageRGB(bufferedImage))
        }
        enc.finish()
    }

    companion object {
        val OUTPUT_DIR = "out"
    }
}
