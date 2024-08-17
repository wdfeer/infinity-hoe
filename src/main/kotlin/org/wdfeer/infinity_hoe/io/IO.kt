package org.wdfeer.infinity_hoe.io

import org.wdfeer.infinity_hoe.InfinityHoe
import java.io.InputStream

object IO {
    fun getFileInJar(path: String): InputStream? = InfinityHoe::class.java.classLoader.getResourceAsStream(path)
}