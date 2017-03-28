:load loadhmt.sc
import scala.math._

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus.~~(CtsUrn("urn:cts:greekLit:tlg5026:"))
val scholiaTypes = Vector("msA","msAim","msAint","msAil","msAext")

def histogram(scholiaType: String, scholia: edu.holycross.shot.ohco2.Corpus) = {

  val commentUrns = scholia.urns.map(_.toString).filter(_.contains("comment")).map(CtsUrn(_))
  val scholiaTypeUrn = CtsUrn("urn:cts:greekLit:tlg5026." + scholiaType +":")
  val bk3 = CtsUrn("urn:cts:greekLit:tlg5026." + scholiaType +".hmt:3")
  val bk6 = CtsUrn("urn:cts:greekLit:tlg5026." + scholiaType +".hmt:6")
  val errantExtUrn = CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:12.A1")
  val editedCorpus = scholia.--(scholia.~~(bk3)).--(scholia.~~(bk6)).--(scholia.~~(errantExtUrn)).~~(scholiaTypeUrn).~~(commentUrns)
  val scholiaFreq = editedCorpus.size
  val tokenAnalysis = TeiReader.fromCorpus(editedCorpus)
  val totalTokens = tokenAnalysis.size.toDouble
  val directVoiceTkns = tokenAnalysis.filter(_.isDirectVoice).size.toDouble
  val citedVoiceTkns = tokenAnalysis.filter(_.isCitation).size.toDouble
  val totDirectVoiceTkns = directVoiceTkns + citedVoiceTkns
  val PercDirectVsTotal = (totDirectVoiceTkns / totalTokens)

  val quotedTextTkns = tokenAnalysis.filter(_.isQuotedText).size.toDouble
  val PercQuotedTxtVsTotal = (quotedTextTkns / totalTokens)

  val quotedLanguageTkns = tokenAnalysis.filter(_.isQuotedLanguage).size.toDouble
  val PercQuotedLangVsTotal = (quotedLanguageTkns / totalTokens)

  val nonDirectVoiceTkns = quotedTextTkns + quotedLanguageTkns
  val PercNonDirectVsTotal = (nonDirectVoiceTkns / totalTokens)

  val percentages = Vector(PercDirectVsTotal,PercQuotedTxtVsTotal,PercQuotedLangVsTotal,PercNonDirectVsTotal)

  val dataVec = Vector(scholiaType,totalTokens,percentages(0),percentages(1),percentages(2),percentages(3))
  dataVec
}

val discourseData = scholiaTypes.map(histogram(_,scholia))

def zTest(dataset1: Vector[Any], dataset2: Vector[Any]) = {

  val scholiaType1 = dataset1(0).toString
  val phat1: Double = dataset1(5).toString.toDouble
  val oneMinusPhat1: Double = 1 - phat1
  val pop1: Double = dataset1(1).toString.toDouble
  val freq1: Double = phat1 * pop1

  val scholiaType2 = dataset2(0).toString
  val phat2: Double = dataset2(5).toString.toDouble
  val oneMinusPhat2: Double = 1 - phat2
  val pop2: Double = dataset2(1).toString.toDouble
  val freq2: Double = phat2 * pop2

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

println("Zones Being Compared\tZ-Score\tSignificance")

zTest(discourseData(0),discourseData(1))
zTest(discourseData(0),discourseData(2))
zTest(discourseData(0),discourseData(3))
zTest(discourseData(0),discourseData(4))
zTest(discourseData(1),discourseData(2))
zTest(discourseData(1),discourseData(3))
zTest(discourseData(1),discourseData(4))
zTest(discourseData(2),discourseData(3))
zTest(discourseData(2),discourseData(4))
zTest(discourseData(3),discourseData(4))
