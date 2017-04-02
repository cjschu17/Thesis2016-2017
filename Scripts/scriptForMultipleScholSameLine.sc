import scala.io.Source

val iliadToSchol = Source.fromFile("data/ScholiaOverlap/multipleSchol.tsv").getLines.toVector.map(_.split("\t")).map(line => (line(0),line(1).split(",")))

val byzorthoText = Source.fromFile("data/scholia-byzortho.tsv").getLines.toVector.map(_.split("\t"))
val noLemma = byzorthoText.filter(_(0).contains("comment")).map(line => Array(line(0).dropRight(8),line(1)))

iliadToSchol.map(function(_,noLemma))

def function(lineWithSchol: (String, Array[String]), byzorthoEdition: Vector[Array[String]]) = {

  val lineUrn = lineWithSchol._1
  val scholUrns = lineWithSchol._2

  val scholiaText = scholUrns.map(function2(_,byzorthoEdition)).map(_.flatten).map(_.toArray).toVector
  val scholLabels = scholUrns.map(_.drop(25))
  val scholiaSplit = scholiaText.map(arr => (arr(0),arr(1).split(" ").map(_.replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+","")).filterNot(_.isEmpty)))
  var result = ""
  val resultTuple = if (scholiaSplit.size == 2) {
      result += function3(scholiaSplit(0)._2,scholiaSplit(1)._2)
      val results1 = result.split("_")
      val tuple = (scholLabels(0) + " vs " + scholLabels(1),results1(0))
      tuple
  } else if (scholiaSplit.size == 3) {
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  function3(scholiaSplit(1)._2,scholiaSplit(2)._2)
    val results2 = result.split("_")
    val tuple = ((scholLabels(0) + "" + scholLabels(1),results2(0)),(scholLabels(0) + " vs " + scholLabels(2),results2(1)),(scholLabels(1) + " vs " + scholLabels(2),results2(2)))
    tuple
  } else if (scholiaSplit.size == 4) {
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(3)._2)
    result +=  function3(scholiaSplit(1)._2,scholiaSplit(2)._2)
    result +=  function3(scholiaSplit(1)._2,scholiaSplit(3)._2)
    result +=  function3(scholiaSplit(2)._2,scholiaSplit(3)._2)
    val results3 = result.split("_")
    val tuple = ((scholLabels(0) + " vs " + scholLabels(1),results3(0)),(scholLabels(0) + " vs " + scholLabels(2),results3(1)),(scholLabels(0) + " vs " + scholLabels(3),results3(2)),(scholLabels(1) + " vs " + scholLabels(2),results3(3)),(scholLabels(1) + " vs " + scholLabels(3),results3(4)),(scholLabels(2) + " vs " + scholLabels(3),results3(5)))
    tuple
  } else if (scholiaSplit.size == 5) {
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(1)._2)
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(2)._2)
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(3)._2)
    result +=  function3(scholiaSplit(0)._2,scholiaSplit(4)._2)
    result +=  function3(scholiaSplit(1)._2,scholiaSplit(2)._2)
    result +=  function3(scholiaSplit(1)._2,scholiaSplit(3)._2)
    result +=  function3(scholiaSplit(1)._2,scholiaSplit(4)._2)
    result +=  function3(scholiaSplit(2)._2,scholiaSplit(3)._2)
    result +=  function3(scholiaSplit(2)._2,scholiaSplit(4)._2)
    result +=  function3(scholiaSplit(3)._2,scholiaSplit(4)._2)
    val results4 = result.split("_")
    val tuple = ((scholLabels(0) + " vs " + scholLabels(1),results4(0)),(scholLabels(0) + " vs " + scholLabels(2),results4(1)),(scholLabels(0) + " vs " + scholLabels(3),results4(2)),(scholLabels(0) + " vs " + scholLabels(4),results4(3)),(scholLabels(1) + " vs " + scholLabels(2),results4(4)),(scholLabels(1) + " vs " + scholLabels(3),results4(5)),(scholLabels(1) + " vs " + scholLabels(4),results4(6)),(scholLabels(2) + " vs " + scholLabels(3),results4(7)),(scholLabels(2) + " vs " + scholLabels(4),results4(8)),(scholLabels(3) + " vs " + scholLabels(4),results4(9)))
    tuple
  }

  val modifiedresultTuple = resultTuple.toString.split("\\),\\(").map(_.replaceAll("[\\(\\)]","")).map(_.split(",")).toVector.map(arr => (arr(0),arr(1)))
  modifiedresultTuple

}

def function2(scholUrn: String, byzorthoEdition: Vector[Array[String]]) = {

  byzorthoEdition.filter(_(0) == scholUrn)

}

def function3 (scholion1:Array[String], scholion2: Array[String]) = {


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
  val shortScholInLongSchol = shorterSchol.map(function4(_,longerSchol))
  val shortScholSize = shorterSchol.size.toDouble
  val trues = shortScholInLongSchol.filter(_.matches("true")).size.toDouble
  val truePerc = (trues / shortScholSize).toString + "_"
  truePerc

}

def function4(word: String, scholion: Array[String]): String = {

  var boolean = ""
  if (scholion.filter(_ == word).size > 0) {
    boolean += "true"
  } else {
    boolean += "false"
  }
  boolean

}
