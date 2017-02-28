import scala.io.Source
import scala.xml.XML
import scala.math

@main
def lemmaData(fileName: String) {

  val srcFile = scala.io.Source.fromFile(fileName).getLines.toVector
  val twoColumnVector = srcFile.map(_.split("\t"))
  val allLemmata = twoColumnVector.filter(_(0).contains("5026")).filterNot(_(0).contains("comment"))

  val mainLemmata = allLemmata.filter(_(0).contains("6.msA.h"))
  val mainLemmataData = lemmataFreq("Main Scholia", mainLemmata)

  val imLemmata = allLemmata.filter(_(0).contains("6.msAim.h"))
  val imLemmataData = lemmataFreq("Intermarginal Scholia", imLemmata)

  val intLemmata = allLemmata.filter(_(0).contains("6.msAint.h"))
  val intLemmataData = lemmataFreq("Interior Scholia", intLemmata)

  val ilLemmata = allLemmata.filter(_(0).contains("6.msAil.h"))
  val ilLemmataData = lemmataFreq("Interlinear Scholia", ilLemmata)

  val extLemmata = allLemmata.filter(_(0).contains("6.msAext.h"))
  val extLemmataData = lemmataFreq("Exterior Scholia", extLemmata)

  val allData: Vector[String] = Vector(mainLemmataData,imLemmataData,intLemmataData,ilLemmataData,extLemmataData)

  print("Type of Scholia\tNumber of Scholia\tNumber of Lemmata\tFrequency of Scholia")

  for (line <- allData) {
    println(line)
  }

}

  def lemmataFreq(scholType: String, lemmata: Vector[Array[String]]) = {

    val scholCount = lemmata.size
    val lemmataText = lemmata.map(l => XML.loadString(l(1)).text)
    val editedLemmata = lemmataText.map(_.replaceAll(" ",""))
    val emptyLemmata = editedLemmata.filter(_.isEmpty).size
    val nonEmptyLemmata = editedLemmata.filterNot(_.isEmpty).size

    require (emptyLemmata + nonEmptyLemmata == scholCount)

    val freqLemmata: Double = (nonEmptyLemmata.toDouble / scholCount.toDouble) * 100
    val percentage = math.BigDecimal(freqLemmata).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    val lemmataData: String = scholType + "\t" + scholCount.toString + "\t" + nonEmptyLemmata.toString + "\t" + percentage.toString + "%"

    lemmataData


  }
