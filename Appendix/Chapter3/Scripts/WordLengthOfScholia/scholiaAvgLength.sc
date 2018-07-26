//This script was designed to count the number of words contained within each of the zones of the scholia
//and to calculate the average length (in words) of a scholion from each of the zones.
//This script also runs a Student's T-test comparing the average length (in words) and standard deviations for each of the scholia zones
//It requires one input, the archival edition of the scholia:

//Thesis2016-2017/Appendix/VersionsOfScholia/archived-version.tsv

import scala.io.Source
import scala.xml.XML
import scala.math

@main
def avgLength(fileName: String) {

  val srcFile = scala.io.Source.fromFile(fileName).getLines.toVector
  val twoColumnVector = srcFile.map(_.split("\t"))
  val allScholiaComments = twoColumnVector.filter(_(0).contains("5026")).filterNot(_(0).contains("lemma"))
  val extractedTokens = allScholiaComments.map(c => (c(0),extract(c(1))))

  val mainTokens = extractedTokens.filter(_._1.contains("6.msA.h")).filterNot(_._2.isEmpty)
  val imTokens = extractedTokens.filter(_._1.contains("6.msAim.h")).filterNot(_._2.isEmpty)
  val intTokens = extractedTokens.filter(_._1.contains("6.msAint.h")).filterNot(_._2.isEmpty)
  val ilTokens = extractedTokens.filter(_._1.contains("6.msAil.h")).filterNot(_._2.isEmpty)
  val extTokens = extractedTokens.filter(_._1.contains("6.msAext.h")).filterNot(_._2.isEmpty)

  val mainScholCount = mainTokens.size.toDouble
  val mainWdsPerSchol = mainTokens.map(_._2.size)
  val statsMain = statsCalc(mainScholCount,mainWdsPerSchol)

  val imScholCount = imTokens.size.toDouble
  val imWdsPerSchol = imTokens.map(_._2.size)
  val statsIm = statsCalc(imScholCount,imWdsPerSchol)

  val intScholCount = intTokens.size.toDouble
  val intWdsPerSchol = intTokens.map(_._2.size)
  val statsInt = statsCalc(intScholCount,intWdsPerSchol)

  val ilScholCount = ilTokens.size.toDouble
  val ilWdsPerSchol = ilTokens.map(_._2.size)
  val statsIl = statsCalc(ilScholCount,ilWdsPerSchol)

  val extScholCount = extTokens.size.toDouble
  val extWdsPerSchol = extTokens.map(_._2.size)
  val statsExt = statsCalc(extScholCount,extWdsPerSchol)

  val tTestMainIm = tTest(statsMain,mainScholCount,statsIm,imScholCount)
  val tTestMainInt = tTest(statsMain,mainScholCount,statsInt,intScholCount)
  val tTestMainIl = tTest(statsMain,mainScholCount,statsIl,ilScholCount)
  val tTestMainExt = tTest(statsMain,mainScholCount,statsExt,extScholCount)
  val tTestImInt = tTest(statsIm,imScholCount,statsInt,intScholCount)
  val tTestImIl = tTest(statsIm,imScholCount,statsIl,ilScholCount)
  val tTestImExt = tTest(statsIm,imScholCount,statsExt,extScholCount)
  val tTestIntIl = tTest(statsInt,intScholCount,statsIl,ilScholCount)
  val tTestIntExt = tTest(statsInt,intScholCount,statsExt,extScholCount)
  val tTestIlExt = tTest(statsIl,ilScholCount,statsExt,extScholCount)

  println("Type of Scholia\tCount\tMedian\tMean\tStandard Deviation\n")
  println("Main Scholia\t" + mainScholCount + "\t" + statsMain._3 + "\t" + math.BigDecimal(statsMain._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsMain._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  println("Intermarginal Scholia\t" + imScholCount + "\t" + statsIm._3 + "\t" + math.BigDecimal(statsIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsIm._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  println("Interior Scholia\t" + intScholCount + "\t" + statsInt._3 + "\t" + math.BigDecimal(statsInt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsInt._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  println("Interlinear Scholia\t" + ilScholCount + "\t" + statsIl._3 + "\t" + math.BigDecimal(statsIl._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsIl._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  println("Exterior Scholia\t" + extScholCount + "\t" + statsExt._3 + "\t" + math.BigDecimal(statsExt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsExt._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)

  println("\n\nTypes of Scholia\tT-value\tStatistically Significant (p=0.05)\n")
  println("Main and Intermarginal\t" + math.BigDecimal(tTestMainIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestMainIm._2)
  println("Main and Interior\t" + math.BigDecimal(tTestMainInt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestMainInt._2)
  println("Main and Interlinear\t" + math.BigDecimal(tTestMainIl._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestMainIl._2)
  println("Main and Exterior\t" + math.BigDecimal(tTestMainExt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestMainExt._2)
  println("Intermarginal and Interior\t" + math.BigDecimal(tTestImInt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestImInt._2)
  println("Intermarginal and Interlinear\t" + math.BigDecimal(tTestImIl._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestImIl._2)
  println("Intermarginal and Exterior\t" + math.BigDecimal(tTestImExt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestImExt._2)
  println("Interior and Interlinear\t" + math.BigDecimal(tTestIntIl._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestIntIl._2)
  println("Interior and Exterior\t" + math.BigDecimal(tTestIntExt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestIntExt._2)
  println("Interlinear and Exterior\t" + math.BigDecimal(tTestIlExt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestIlExt._2)
}

def extract(srcXml: String) = {

  val parsableXml = scala.xml.XML.loadString(srcXml)
  val paragraph = parsableXml \ "p"
  val textString = paragraph.text.mkString
  val rawTokens = textString.replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+","").split(" ")
  val finalizedTokens = rawTokens.filterNot(_.isEmpty).filterNot(_.matches("[A-Za-z0-9]+"))
  finalizedTokens

}

def statsCalc(totalSchol: Double, listOfLengths: Vector[Int]) = {

  var totWords: Int = 0
  for (num <- listOfLengths) {
    totWords += num

  }
  val finalTotal = totWords.toDouble
  val average = finalTotal / totalSchol

  var numerator: Double = 0.0
  for (num <- listOfLengths) {
    numerator += math.pow((num - average),2.0)
    numerator
  }

  val quotient: Double = numerator / (totalSchol - 1.0)
  val sd = math.sqrt(quotient)

  val orderedWdCt = listOfLengths.sorted
  val scholCount = orderedWdCt.size

  var median: Int = 0

  if (scholCount%2 == 0) {
    val evenBaseNumber: Int = (scholCount / 2)
    median += math.round((orderedWdCt(evenBaseNumber - 1 ) + orderedWdCt(evenBaseNumber)).toDouble / 2).toInt
  } else{
    val oddBaseNumber: Int = (scholCount / 2)
    median += orderedWdCt(oddBaseNumber)
  }

  val meanSDMedian = (average,sd,median)
  meanSDMedian
}

def tTest(dataset1: (Double,Double,Int), size1: Double, dataset2: (Double,Double,Int), size2: Double) = {

  val mean1 = dataset1._1
  val mean2 = dataset2._1

  val sd1 = dataset1._2
  val sd2 = dataset2._2

  val meansDiff = math.abs(mean1 - mean2)

  val var1 = math.pow(sd1,2.0)
  val varOverCount1 = var1 / size1

  val var2 = math.pow(sd2,2.0)
  val varOverCount2 = var2 / size2

  val denominatorSquared = varOverCount1 + varOverCount2

  val denominator = math.sqrt(denominatorSquared)
  val tValue = meansDiff / denominator

  var significance = (tValue,"not statistically Significant")
  if (tValue > 1.645) {
     significance = (tValue,"Statistically Significant")
  }

  significance

}
