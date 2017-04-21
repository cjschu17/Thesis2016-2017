import scala.io.Source
import scala.math._



def finalResult(lineWithSchol: (String, Array[String]), byzorthoEdition: Vector[Array[String]]) = {

  val lineUrn = lineWithSchol._1
  val scholUrns = lineWithSchol._2

  val scholiaText = scholUrns.map(textRetrieval(_,byzorthoEdition)).map(_.flatten).map(_.toArray).toVector.filter(_.size > 0)
  val scholLabels = scholUrns.map(_.drop(25))
  val scholiaSplit = scholiaText.map(arr => (arr(0),arr(1).split(" ").map(_.replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+","")).filterNot(_.isEmpty)))
  var result = ""
  val resultTuple = if (scholiaSplit.size == 2) {
      result += scholComparResult(scholiaSplit(0)._2,scholiaSplit(1)._2)
      val results2 = result.split("_")
      val tuple = (scholLabels(0) + " vs " + scholLabels(1),results2(0))
      tuple
  } else if (scholiaSplit.size == 3) {
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(2)._2)
    val results3 = result.split("_")
    val tuple = ((scholLabels(0) + " vs " + scholLabels(1),results3(0)),
    (scholLabels(0) + " vs " + scholLabels(2),results3(1)),
    (scholLabels(1) + " vs " + scholLabels(2),results3(2)))
    tuple
  } else if (scholiaSplit.size == 4) {
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(3)._2)
    val results4 = result.split("_")
    val tuple = ((scholLabels(0) + " vs " + scholLabels(1),results4(0)),(scholLabels(0) + " vs " + scholLabels(2),results4(1)),(scholLabels(0) + " vs " + scholLabels(3),results4(2)),(scholLabels(1) + " vs " + scholLabels(2),results4(3)),(scholLabels(1) + " vs " + scholLabels(3),results4(4)),(scholLabels(2) + " vs " + scholLabels(3),results4(5)))
    tuple
  } else if (scholiaSplit.size == 5) {
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(3)._2,scholiaSplit(4)._2)
    val results5 = result.split("_")
    val tuple = ((scholLabels(0) + " vs " + scholLabels(1),results5(0)),(scholLabels(0) + " vs " + scholLabels(2),results5(1)),(scholLabels(0) + " vs " + scholLabels(3),results5(2)),(scholLabels(0) + " vs " + scholLabels(4),results5(3)),(scholLabels(1) + " vs " + scholLabels(2),results5(4)),(scholLabels(1) + " vs " + scholLabels(3),results5(5)),(scholLabels(1) + " vs " + scholLabels(4),results5(6)),(scholLabels(2) + " vs " + scholLabels(3),results5(7)),(scholLabels(2) + " vs " + scholLabels(4),results5(8)),(scholLabels(3) + " vs " + scholLabels(4),results5(9)))
    tuple
  } else if (scholiaSplit.size == 6) {
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(3)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(3)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(4)._2,scholiaSplit(5)._2)
    val results6 = result.split("_")
    val tuple = ((scholLabels(0) + " vs " + scholLabels(1),results6(0)),
    (scholLabels(0) + " vs " + scholLabels(2),results6(1)),
    (scholLabels(0) + " vs " + scholLabels(3),results6(2)),
    (scholLabels(0) + " vs " + scholLabels(4),results6(3)),
    (scholLabels(0) + " vs " + scholLabels(5),results6(4)),
    (scholLabels(1) + " vs " + scholLabels(2),results6(5)),
    (scholLabels(1) + " vs " + scholLabels(3),results6(6)),
    (scholLabels(1) + " vs " + scholLabels(4),results6(7)),
    (scholLabels(1) + " vs " + scholLabels(5),results6(8)),
    (scholLabels(2) + " vs " + scholLabels(3),results6(9)),
    (scholLabels(2) + " vs " + scholLabels(4),results6(10)),
    (scholLabels(2) + " vs " + scholLabels(5),results6(11)),
    (scholLabels(3) + " vs " + scholLabels(4),results6(12)),
    (scholLabels(3) + " vs " + scholLabels(5),results6(13)),
    (scholLabels(4) + " vs " + scholLabels(5),results6(14)))
    tuple
  } else if (scholiaSplit.size == 7) {
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(0)._2,scholiaSplit(6)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(2)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(1)._2,scholiaSplit(6)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(3)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(2)._2,scholiaSplit(6)._2)
    result +=  scholComparResult(scholiaSplit(3)._2,scholiaSplit(4)._2)
    result +=  scholComparResult(scholiaSplit(3)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(3)._2,scholiaSplit(6)._2)
    result +=  scholComparResult(scholiaSplit(4)._2,scholiaSplit(5)._2)
    result +=  scholComparResult(scholiaSplit(4)._2,scholiaSplit(6)._2)
    result +=  scholComparResult(scholiaSplit(5)._2,scholiaSplit(6)._2)
    val results4 = result.split("_")
    val tuple = ((scholLabels(0) + " vs " + scholLabels(1),results4(0)),
    (scholLabels(0) + " vs " + scholLabels(2),results4(1)),
    (scholLabels(0) + " vs " + scholLabels(3),results4(2)),
    (scholLabels(0) + " vs " + scholLabels(4),results4(3)),
    (scholLabels(0) + " vs " + scholLabels(5),results4(4)),
    (scholLabels(0) + " vs " + scholLabels(6),results4(5)),
    (scholLabels(1) + " vs " + scholLabels(2),results4(6)),
    (scholLabels(1) + " vs " + scholLabels(3),results4(7)),
    (scholLabels(1) + " vs " + scholLabels(4),results4(8)),
    (scholLabels(1) + " vs " + scholLabels(5),results4(9)),
    (scholLabels(1) + " vs " + scholLabels(6),results4(10)),
    (scholLabels(2) + " vs " + scholLabels(3),results4(11)),
    (scholLabels(2) + " vs " + scholLabels(4),results4(12)),
    (scholLabels(2) + " vs " + scholLabels(5),results4(13)),
    (scholLabels(2) + " vs " + scholLabels(6),results4(14)),
    (scholLabels(3) + " vs " + scholLabels(4),results4(15)),
    (scholLabels(3) + " vs " + scholLabels(5),results4(16)),
    (scholLabels(3) + " vs " + scholLabels(6),results4(17)),
    (scholLabels(4) + " vs " + scholLabels(5),results4(18)),
    (scholLabels(4) + " vs " + scholLabels(6),results4(19)),
    (scholLabels(5) + " vs " + scholLabels(6),results4(20)))
    tuple
  }  else {
    result += "null,null),(null,null"
    result
  }

  val modifiedresultTuple = resultTuple.toString.split("\\),\\(").map(_.replaceAll("[\\(\\)]","")).map(_.split(",")).toVector.map(arr => (arr(0),arr(1)))
  Vector(lineUrn) zip Vector(modifiedresultTuple)

}

def textRetrieval(scholUrn: String, byzorthoEdition: Vector[Array[String]]) = {

  byzorthoEdition.filter(_(0) == scholUrn)

}

def scholComparResult (scholion1:Array[String], scholion2: Array[String]) = {


  val scholion1Size = scholion1.size
  val scholion2Size = scholion2.size

  var longerSchol: Array[String] = Array("")
  var shorterSchol: Array[String] = Array("")
  if (scholion1Size > scholion2Size) {
    longerSchol = scholion1
    shorterSchol = scholion2
  } else if (scholion1Size == scholion2Size) {
    longerSchol = scholion1
    shorterSchol = scholion2
  } else {
    longerSchol = scholion2
    shorterSchol = scholion1
  }
  val shortScholInLongSchol = shorterSchol.map(scholComparBoolean(_,longerSchol))
  val shortScholSize = shorterSchol.size.toDouble
  val trues = shortScholInLongSchol.filter(_.matches("true")).size.toDouble
  val truePerc = math.BigDecimal((trues / shortScholSize) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  val percentageString = truePerc.toString + "%_"
  percentageString

}

def scholComparBoolean(word: String, scholion: Array[String]): String = {

  var boolean = ""
  if (scholion.filter(_ == word).size > 0) {
    boolean += "true"
  } else {
    boolean += "false"
  }
  boolean

}

val iliadToSchol = Source.fromFile("data/ScholiaOverlap/multipleSchol.tsv").getLines.toVector.map(_.split("\t")).map(line => (line(0),line(1).split(","))).filterNot(_._2.size == 17).filterNot(_._2.size == 8)

val byzorthoText = Source.fromFile("data/scholia-byzortho.tsv").getLines.toVector.map(_.split("\t"))
val noLemma = byzorthoText.filter(_(0).contains("comment")).map(line => Array(line(0).dropRight(8),line(1)))

val numScholiaOnLine = iliadToSchol.map(_._2.size).distinct.sortWith(_ < _)

for (i <- numScholiaOnLine) {

  println("\nLines with " + i.toString + " Scholia\n")
  val groupingBySize = iliadToSchol.filter(_._2.size == i)
  val lineToSchol = groupingBySize.map(finalResult(_,noLemma)).flatten
  val noNulls = lineToSchol.filterNot(_._2.mkString(",").contains("null"))
  for (line <- noNulls) {
    val scholScores = line._2
    for (score <- scholScores) {
      println(line._1 + "\t" + score._1 + "\t" + score._2)
    }
  }

}
