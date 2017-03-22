//This script creates an analysis of the various topics of a topic models
//By breaking the heighest weighted topics into the zones of scholia
//This script must be run from hmt-twiddle repo

:load loadhmt.sc



val nameFile = scala.io.Source.fromFile("../Thesis2016-2017/data/TopicModelData/names.txt").getLines.toVector

val startImUrns = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/startImUrns.txt").getLines.toVector.drop(1)
val endIntUrns = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/endIntUrns.txt").getLines.toVector.drop(1)
val startIntUrns = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/startIntUrns.txt").getLines.toVector.drop(1)
val endImUrns = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/endImUrns.txt").getLines.toVector.drop(1)


for (file <- nameFile) {

  val origFile = scala.io.Source.fromFile("../Thesis2016-2017/data/TopicModelData/ThetaTables-3-15-2017/" + file).getLines.toVector.map(_.split("\t")).map(e => Array(e(0).dropRight(8),e(1)))

  val totalScholia: Double = origFile.size

  val mainScholia: Double = origFile.filter(_(0).contains("6.msA.")).size

  val intermarg: Double = origFile.filter(_(0).contains("6.msAim.")).size

  val interior: Double = origFile.filter(_(0).contains("6.msAint.")).size

  val startIm: Double = startImUrns.map(function(_,origFile)).filterNot(_.size != 1).map(_(0)).filterNot(_(0).isEmpty).size
  val endInt: Double = endIntUrns.map(function(_,origFile)).filterNot(_.size != 1).map(_(0)).filterNot(_(0).isEmpty).size
  val startInt: Double = startIntUrns.map(function(_,origFile)).filterNot(_.size != 1).map(_(0)).filterNot(_(0).isEmpty).size
  val endIm: Double = endImUrns.map(function(_,origFile)).filterNot(_.size != 1).map(_(0)).filterNot(_(0).isEmpty).size

  val start = startIm + startInt
  val end = endInt + endIm

  require (intermarg == (startIm + endIm))
  require (interior == (startInt + endInt))

  val interlinear: Double = origFile.filter(_(0).contains("6.msAil.")).size

  val ext: Double = origFile.filter(_(0).contains("6.msAext.")).size

  require (totalScholia == mainScholia + interlinear + intermarg + interior + ext)

  println("\nThis is scholia set " + file + " contains:\nmain scholia: "
      + mainScholia + " (" + math.BigDecimal((mainScholia / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nintermarginal scholia: "
       + intermarg + " (" + math.BigDecimal((intermarg / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\ninterior scholia: "
       + interior + " (" + math.BigDecimal((interior / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nstart scholia: "
       + start + " (" + math.BigDecimal((start / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nend scholia: "
       + end + " (" + math.BigDecimal((end / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\ninterlinear scholia: "
       + interlinear + " (" + math.BigDecimal((interlinear / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nexterior scholia: "
       + ext + " (" + math.BigDecimal((ext / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nIn total there are " + totalScholia + " scholia.")

  println("\n\nWithin this set, " + file + ", here is how it splits up among the textual scholia:\nstartIm: " + startIm + " ("
    + math.BigDecimal((startIm / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    + "%)\nendInt: " + endInt + " (" + math.BigDecimal((endInt / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    + "%)\nstartInt: " + startInt + " (" + math.BigDecimal((startInt / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nendIm:"  + endIm +  " (" + math.BigDecimal((endIm / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "%)\nstart: "
  + start + " (" + math.BigDecimal((start / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "%)\nend:"  + end +  " ("
    + math.BigDecimal((end / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "%)\nim: " + intermarg +  " ("
      + math.BigDecimal((intermarg / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "%)\nint: " + interior
      +  " (" + math.BigDecimal((interior / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble + "%)" )

}

def function(urn: String, scholia: Vector[Array[String]]) = {
  val filteredSchol = scholia.filter(_(0) == urn)
  filteredSchol
}
