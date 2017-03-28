import scala.io._
import scala.math._

val table = scala.io.Source.fromFile("data/DiscourseData/DiscourseHistogram.tsv").getLines.toVector.map(_.split("\t")).drop(1).dropRight(1)
val editedTable = table.map(f => Array(f(0),f(4),f(5),f(6)))
val noPerc = editedTable.map(_.map(_.split(" ")(0)))


println("Type Of Scholia\tTotal Words in Indirect Discourse\tWords in Quoted Text\tWords in Quoted Language")


def rearrangingTable (dataset: Vector[Array[String]]) = {

  for (row <- dataset) {
    val scholiaType = row(0)
    val quotedTxt = row(1).toDouble
    val quotedLang = row(2).toDouble
    val indirectDiscourse = row(3).toDouble

    val qTxtPerc = math.BigDecimal((quotedTxt / indirectDiscourse) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    val qLangPerc = math.BigDecimal((quotedLang / indirectDiscourse) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    println(scholiaType + "\t" + indirectDiscourse + "\t" + quotedTxt + " (" + qTxtPerc + "%)\t" + quotedLang + " (" + qLangPerc + "%)")
  }

}

rearrangingTable(noPerc)


val otherQTxt = noPerc.map(_(1)).drop(1).map(_.toInt)
var qTxtSum: Int = 0
for (num <- otherQTxt) {
  qTxtSum += num
}
val allQLang = noPerc.map(_(2)).drop(1).map(_.toInt)
var qLangSum: Int = 0
for (num <- allQLang) {
  qLangSum += num
}
val allIndirect = noPerc.map(_(3)).drop(1).map(_.toInt)
var indirectSum: Int = 0
for (num <- allIndirect) {
  indirectSum += num
}

val discourseData = Vector(Array("msA",noPerc(0)(1),noPerc(0)(2),noPerc(0)(3)),Array("otherSchol",qTxtSum.toString,qLangSum.toString,indirectSum.toString))



def zTest(dataset1: Array[String], dataset2: Array[String]) = {

  val scholiaType1 = dataset1(0)
  val pop1: Double = dataset1(3).toDouble
  val freq1: Double = dataset1(1).toDouble
  val phat1: Double = freq1 / pop1
  val oneMinusPhat1: Double = 1 - phat1


  val scholiaType2 = dataset2(0).toString
  val pop2: Double = dataset2(3).toDouble
  val freq2: Double = dataset2(1).toDouble
  val phat2: Double = freq2 / pop2
  val oneMinusPhat2: Double = 1 - phat2


  val proportionDiff: Double = phat1 - phat2

  val nullP: Double = ((freq1 + freq2)/(pop1 + pop2))
  val oneMinusNullP: Double = 1 - nullP
  val pOneMinusNullP: Double = nullP * oneMinusNullP

  val oneOverPop1: Double = 1 / pop1
  val oneOverPop2: Double = 1 / pop2
  val oneOverPopSum: Double = oneOverPop1 + oneOverPop2

  val variance: Double = pOneMinusNullP * oneOverPopSum
  val standardDev: Double = math.sqrt(variance)

  val zScore: Double = proportionDiff / standardDev
  val roundedzScore = math.abs(math.BigDecimal(zScore).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)

  var significance = ""
  if (zScore > 1.96 ) {
    significance += "Statisically Significant"
  } else if (zScore < -1.96)  {
    significance += "Statisically Significant"
  }  else {
    significance += "Not Statistically Significant"
  }


  println(scholiaType1 + " & " + scholiaType2 + "\t" + roundedzScore + "\t" + significance)

}

zTest(discourseData(0),discourseData(1))
zTest(noPerc(0),noPerc(1))
zTest(noPerc(0),noPerc(2))
zTest(noPerc(0),noPerc(3))
zTest(noPerc(0),noPerc(4))
zTest(noPerc(1),noPerc(2))
zTest(noPerc(1),noPerc(3))
zTest(noPerc(1),noPerc(4))
zTest(noPerc(2),noPerc(3))
zTest(noPerc(2),noPerc(4))
zTest(noPerc(3),noPerc(4))
