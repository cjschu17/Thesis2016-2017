import scala.math._

@main
def zTest(scholiaType1: String, freq1: Double, pop1: Double, scholiaType2: String, freq2: Double, pop2: Double, zStandard: Double) {

  val phat1: Double = freq1 / pop1
  val oneMinusPhat1: Double = 1 - phat1

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
  if (zScore > zStandard ) {
    significance += "Statisically Significant"
  } else if (zScore < -zStandard)  {
    significance += "Statisically Significant"
  }  else {
    significance += "Not Statistically Significant"
  }


  println(scholiaType1 + " & " + scholiaType2 + "\t" + roundedzScore + "\t" + significance)

}
