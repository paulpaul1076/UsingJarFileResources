package ru.company.project

import java.nio.file.{FileSystems, Files, Path, Paths}
import java.util

object Main {
  def main(args: Array[String]): Unit = {

    val uri = this.getClass.getClassLoader.getResource("resource1").toURI

    // create file system
    val env = new util.HashMap[String, String]()
    env.put("create", "true")
    val jarFS = FileSystems.newFileSystem(uri, env)

    val file = Paths.get(uri)

    Files.walk(file).forEach(f => {
      println(f)
      if (Files.isRegularFile(f))
        copyToLocal(f)
    })

    jarFS.close()
  }

  def copyToLocal(file: Path): Unit = {
    val content = Files.readAllLines(file)
    val localFSFile = FileSystems.getDefault.getPath(file.toString.substring(1))
    localFSFile.toFile.getParentFile.mkdirs()
    Files.write(localFSFile, content)
  }
}
