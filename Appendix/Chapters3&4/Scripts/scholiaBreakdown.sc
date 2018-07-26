//This script creates an analysis of the various topics of a topic models
//By breaking the heighest weighted topics into the zones of scholia

import scala.io.Source

@main
def scholiaBreakdown (folder: String) {

  val nameFile = Source.fromFile(folder).getLines.toVector

  for (file <- nameFile) {

    val origFile = Source.fromFile("ThetaTables-3-15-2017/" + file).getLines.toVector

    val totalScholia: Double = origFile.size

    val mainScholia: Double = origFile.filter(_.contains("6.msA.")).size

    val interlinear: Double = origFile.filter(_.contains("6.msAil.")).size

    val intermarg: Double = origFile.filter(_.contains("6.msAim.")).size

    val interior: Double = origFile.filter(_.contains("6.msAint.")).size

    val ext: Double = origFile.filter(_.contains("6.msAext.")).size

    require (totalScholia == mainScholia + interlinear + intermarg + interior + ext)

    println("\nThis is scholia set " + file + " contains:\nmain scholia: "
      + mainScholia + " (" + math.BigDecimal((mainScholia / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nintermarginal scholia: "
       + intermarg + " (" + math.BigDecimal((intermarg / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\ninterior scholia: "
       + interior + " (" + math.BigDecimal((interior / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\ninterlinear scholia: "
       + interlinear + " (" + math.BigDecimal((interlinear / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nexterior scholia: "
       + ext + " (" + math.BigDecimal((ext / totalScholia)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  + "%)\nIn total there are " + totalScholia + " scholia.")
  }
}
