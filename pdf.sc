
/* A scala to typeset a single PDF from markdown source files.

Requirements:

0. Scala
1. pandoc (http://pandoc.org/)
2. a TeX system such as LaTeX (https://en.wikipedia.org/wiki/XeTeX)
3. a Unicode font including the ancient Greek range of glyphs.

A good default choice for font is Gentium (http://software.sil.org/gentium/).


Running:


Run this from the root directory of your thesis repository.

*/



import sys.process._
import java.io.File
import scala.io.Source

val srcDir = "ThesisWriting/"
val toc = Source.fromFile(srcDir + "toc.txt").getLines.toVector

val pairs = toc.map(_.split("#"))
val md = for (chap <- pairs) yield {
  val title = chap(0)
  val bodyLines = Source.fromFile(srcDir + chap(1)).getLines.toVector.mkString("\n\n")
  "#" + title +"\n\n" +  bodyLines  + "\n\n"
}


import java.io.PrintWriter
val tempFile = "temporaryComposite.md"
new PrintWriter(tempFile) { write(md.mkString("\n"));close;}


val pandoc = s"""pandoc --variable mainfont="Gentium" --latex-engine=xelatex -o thesis.pdf  """ + tempFile
println ("\n\nTypeseting with pandoc using command:")
println("\t" + pandoc)
pandoc.!
new File(tempFile).delete()
println("Done.  If successful, output is now in thesis.pdf.")
