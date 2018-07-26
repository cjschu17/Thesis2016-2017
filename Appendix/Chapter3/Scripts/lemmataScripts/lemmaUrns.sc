//this script is for extrapoltating the Urns of non-main scholia who have a lemma.
//Source file is Thesis2016-2017/Appendix/VersionsOfScholia/archived-version.tsv

import scala.io.Source
import scala.xml.XML
import scala.math

@main
def lemmaData(fileName: String) {

  val srcFile = scala.io.Source.fromFile(fileName).getLines.toVector
  val twoColumnVector = srcFile.map(_.split("\t"))
  val allLemmata = twoColumnVector.filter(_(0).contains("5026")).filterNot(_(0).contains("comment"))

  val imLemmata = allLemmata.filter(_(0).contains("6.msAim.h"))
  val nonEmptyIm = extractLemma(imLemmata)
  println("Intermarginal Scholia\n")
  for (n <- nonEmptyIm) {
    println(n)
  }
println("\n\n")

  val intLemmata = allLemmata.filter(_(0).contains("6.msAint.h"))
  val nonEmptyInt = extractLemma(intLemmata)
  println("Interior Scholia \n")
  for (n <- nonEmptyInt) {
    println(n)
  }
println("\n\n")

  val ilLemmata = allLemmata.filter(_(0).contains("6.msAil.h"))
  val nonEmptyIl = extractLemma(ilLemmata)
  println("Interlinear Scholia\n")
  for (n <- nonEmptyIl) {
    println(n)
  }
  println("\n\n")

  val extLemmata = allLemmata.filter(_(0).contains("6.msAext.h"))
  val nonEmptyExt = extractLemma(extLemmata)
  println("Exterior Scholia\n")
  for (n <- nonEmptyExt) {
    println(n)
  }

}

  def extractLemma(lemmata: Vector[Array[String]]) = {

    val lemmataText = lemmata.map(l => (l(0),XML.loadString(l(1)).text))
    val editedLemmata = lemmataText.map(t => (t._1,t._2.replaceAll(" ","")))
    val nonEmptyLemmata = editedLemmata.filterNot(_._2.isEmpty)
    val lemmaUrns = nonEmptyLemmata.map(_._1)
    lemmaUrns
  }
