//this script is meant to run in the sbt console within the hmt-twiddle repo
//This script is meant to give descriptive statistics about the frequency of types of
//discourse across the 5 zones of scholia.

:load loadhmt.sc
import scala.math._

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus.~~(CtsUrn("urn:cts:greekLit:tlg5026:"))
val scholiaTypes = Vector("msA","msAim","msAint","msAil","msAext")
println("Scholia Type\tNumber of Scholia\tNumber of Words\tNumber of Direct Voice Words\tNumber of Quoted Text Words\tNumber of Quoted Language Words\tNumber of Non-Direct Voice Word")

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
  val PercDirectVsTotal = (totDirectVoiceTkns / totalTokens) * 100

  val quotedTextTkns = tokenAnalysis.filter(_.isQuotedText).size.toDouble
  val PercQuotedTxtVsTotal = (quotedTextTkns / totalTokens) * 100

  val quotedLanguageTkns = tokenAnalysis.filter(_.isQuotedLanguage).size.toDouble
  val PercQuotedLangVsTotal = (quotedLanguageTkns / totalTokens) * 100

  val nonDirectVoiceTkns = quotedTextTkns + quotedLanguageTkns
  val PercNonDirectVsTotal = (nonDirectVoiceTkns / totalTokens) * 100

  val percentages = Vector(PercDirectVsTotal,PercQuotedTxtVsTotal,PercQuotedLangVsTotal,PercNonDirectVsTotal)
  val roundPerc = percentages.map(math.BigDecimal(_).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  println(scholiaType + "\t" + scholiaFreq + "\t" + totalTokens.toInt + "\t" + totDirectVoiceTkns.toInt + " (" + roundPerc(0) + "%)\t" + quotedTextTkns.toInt + " (" + roundPerc(1) + "%)\t" + quotedLanguageTkns.toInt + " (" + roundPerc(2) + "%)\t" + nonDirectVoiceTkns.toInt + " (" + roundPerc(3) + "%)")
}


for (scholiaType <- scholiaTypes) {

  histogram(scholiaType,scholia)

}
