//This is an executable script that is meant to compare the intermarginal/interior model and start-of-line/end-of-line 
//model of the scholia by analyzing their descriptive statistics.
//Specifically this script looks at how the four individual columns of scholia that can be classified by either model
// compare in terms of length of scholia in words.
//Requires 2 parameters.
//1: archive index of schol to folio: Thesis2016-2017/Appendix/Chapters3&4/Data/StartEndIdentification/scholToFolioIndx.csv
//2: archival edition of the HMT: Thesis2016-2017/Appendix/VersionsOfScholia/archived-version.tsv

import scala.io.Source
import scala.math._
import scala.xml.XML

@main
def main(fileName: String, fileName2: String) {
val scholIndex = scala.io.Source.fromFile(fileName).getLines.toVector.filterNot(_.contains("Folio")).map(_.replaceAll("\"",""))

val intSchol = scholIndex.filter(_.contains("msAint")).map(_.split(","))
val intToFolio = intSchol.map(_.filter(_.contains("urn"))).map(arr => (arr(0),arr(2)))
val startIntUrns = intToFolio.filterNot(_._2.contains("v")).map(_._1)
val endIntUrns = intToFolio.filter(_._2.contains("v")).map(_._1)

val imSchol = scholIndex.filter(_.contains("msAim")).map(_.split(","))
val imToFolio = imSchol.map(_.filter(_.contains("urn"))).map(arr => (arr(0),arr(2)))
val startImUrns = imToFolio.filter(_._2.contains("v")).map(_._1)
val endImUrns = imToFolio.filterNot(_._2.contains("v")).map(_._1)

val startUrns = startIntUrns ++ startImUrns
val endUrns = endImUrns ++ endIntUrns

val scholFile = scala.io.Source.fromFile(fileName2).getLines.toVector
val twoColumnVector = scholFile.map(_.split("\t"))
val allScholiaComments = twoColumnVector.filter(_(0).contains("5026")).filterNot(_(0).contains("lemma"))
val extractedTokens = allScholiaComments.map(c => (c(0),extract(c(1)))).map(c => (c._1.dropRight(8),c._2))


val startIm = startImUrns.map(function(_,extractedTokens)).filterNot(_.size != 1).map(_(0)).filterNot(_._2.isEmpty)
val endInt = endIntUrns.map(function(_,extractedTokens)).filterNot(_.size != 1).map(_(0)).filterNot(_._2.isEmpty)
val startInt = startIntUrns.map(function(_,extractedTokens)).filterNot(_.size != 1).map(_(0)).filterNot(_._2.isEmpty)
val endIm = endImUrns.map(function(_,extractedTokens)).filterNot(_.size != 1).map(_(0)).filterNot(_._2.isEmpty)
val start = startUrns.map(function(_,extractedTokens)).filterNot(_.size != 1).map(_(0)).filterNot(_._2.isEmpty)
val end = endUrns.map(function(_,extractedTokens)).filterNot(_.size != 1).map(_(0)).filterNot(_._2.isEmpty)

val startImScholCount = startIm.size.toDouble
val startImWdsPerSchol = startIm.map(_._2.size)
val statsStartIm = statsCalc(startImScholCount,startImWdsPerSchol)

val endIntScholCount = endInt.size.toDouble
val endIntWdsPerSchol = endInt.map(_._2.size)
val statsEndInt = statsCalc(endIntScholCount,endIntWdsPerSchol)

val startIntScholCount = startInt.size.toDouble
val startIntWdsPerSchol = startInt.map(_._2.size)
val statsStartInt = statsCalc(startIntScholCount,startIntWdsPerSchol)

val endImScholCount = endIm.size.toDouble
val endImWdsPerSchol = endIm.map(_._2.size)
val statsEndIm = statsCalc(endImScholCount,endImWdsPerSchol)

val startScholCount = start.size.toDouble
val startWdsPerSchol = start.map(_._2.size)
val statsStart = statsCalc(startScholCount,startWdsPerSchol)

val endScholCount = end.size.toDouble
val endWdsPerSchol = end.map(_._2.size)
val statsEnd = statsCalc(endScholCount,endWdsPerSchol)

println("Type of Scholia\tCount\tMedian\tMean\tStandard Deviation\n")
println("Start Im Scholia\t" + startImScholCount + "\t" + statsStartIm._3 + "\t" + math.BigDecimal(statsStartIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsStartIm._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
println("End Int Scholia\t" + endIntScholCount + "\t" + statsEndInt._3 + "\t" + math.BigDecimal(statsEndInt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsEndInt._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
println("Start Int Scholia\t" + startIntScholCount + "\t" + statsStartInt._3 + "\t" + math.BigDecimal(statsStartInt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsStartInt._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
println("End Im Scholia\t" + endImScholCount + "\t" + statsEndIm._3 + "\t" + math.BigDecimal(statsEndIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsEndIm._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
println("Total End Scholia\t" + endScholCount + "\t" + statsEnd._3 + "\t" + math.BigDecimal(statsEnd._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsEnd._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
println("Total Start Scholia\t" + startScholCount + "\t" + statsStart._3 + "\t" + math.BigDecimal(statsStart._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + math.BigDecimal(statsStart._2).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)

val tTestStartImEndIm = tTest(statsStartIm,startImScholCount,statsEndIm,endImScholCount)
val tTestStartIntEndInt = tTest(statsStartInt,startIntScholCount,statsEndInt,endIntScholCount)
val tTestStartIntStartIm = tTest(statsStartIm,startImScholCount,statsStartInt,startIntScholCount)
val tTestEndIntEndIm = tTest(statsEndInt,endIntScholCount,statsEndIm,endImScholCount)
val tTestEndIntStartIm = tTest(statsEndInt,endIntScholCount,statsStartIm,startImScholCount)
val tTestStartIntEndIm = tTest(statsEndIm,endImScholCount,statsStartInt,startIntScholCount)

val tTestStartEnd =tTest(statsStart,startScholCount,statsEnd,endScholCount)

println("\n\nTypes of Scholia\tT-value\tStatistically Significant (p=0.05)")
println("StartIm and EndIm\t" + math.BigDecimal(tTestStartImEndIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestStartImEndIm._2)
println("StartInt and EndInt\t" + math.BigDecimal(tTestStartIntEndInt._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestStartIntEndInt._2)
println("StartIm and StartInt\t" + math.BigDecimal(tTestStartIntStartIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestStartIntStartIm._2)
println("EndIm and EndInt\t" + math.BigDecimal(tTestEndIntEndIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestEndIntEndIm._2)
println("StartIm and EndInt\t" + math.BigDecimal(tTestEndIntStartIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestEndIntStartIm._2)
println("StartInt and EndIm\t" + math.BigDecimal(tTestStartIntEndIm._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestStartIntEndIm._2)
println("Start and End\t" + math.BigDecimal(tTestStartEnd._1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "\t" + tTestStartEnd._2)




}

def extract(srcXml: String) = {

  val parsableXml = scala.xml.XML.loadString(srcXml)
  val paragraph = parsableXml \ "p"
  val textString = paragraph.text.mkString
  val rawTokens = textString.replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+","").split(" ")
  val finalizedTokens = rawTokens.filterNot(_.isEmpty).filterNot(_.matches("[A-Za-z0-9]+"))
  finalizedTokens

}

def function(urn: String, scholia: Vector[(String,Array[String])]) = {
  val filteredSchol = scholia.filter(_._1 == urn)
  filteredSchol
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
