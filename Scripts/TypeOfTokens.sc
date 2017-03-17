:load loadhmt.sc
import scala.io.math

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus.~~(CtsUrn("urn:cts:greekLit:tlg5026:"))
val scholiaTypes = Vector("msA","msAim","msAint","msAil","msAext")
println("Scholia Type\tNumber of Scholia\tNumber of Words\tNumber of Direct Voice Words\tNumber of Quoted Text Words\tNumber of Quoted Language Words\tNumber of Non-Direct Voice Word")

for (scholiaType <- scholiaTypes) {

  histogram(scholiaType,scholia)

}

def histogram(scholiaType: String, scholia: edu.holycross.shot.ohco2.Corpus) = {

  val scholiaTypeUrnString = "urn:cts:greekLit:tlg5026." + scholiaType +":"
  val scholiaTypeUrn = CtsUrn(scholiaTypeUrnString)
  val scholiaTypeCorpus = scholia ~~ scholiaTypeUrn
  val bk3String = "urn:cts:greekLit:tlg5026." + scholiaType +".hmt:3"
  val bk3Urn = CtsUrn(bk3String)
  val errantExtUrn = CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:12.A1")
  val editedCorpus = scholiaTypeCorpus.--(scholia.~~(bk3Urn)).--(scholia.~~(errantExtUrn))
  val scholiaFreq = editedCorpus.size
  val tokenAnalysis = TeiReader.fromCorpus(editedCorpus)
  val commentTokens = tokenAnalysis.filter(_.textNode.toString.contains("comment"))
  val totalTokens = commentTokens.size.toDouble
  val directVoiceTkns = commentTokens.filter(_.isDirectVoice).size.toDouble
  val citedVoiceTkns = commentTokens.filter(_.isCitation).size.toDouble
  val totDirectVoiceTkns = directVoiceTkns + citedVoiceTkns
  val PercDirectVsTotal = (totDirectVoiceTkns / totalTokens) * 100

  val quotedTextTkns = commentTokens.filter(_.isQuotedText).size.toDouble
  val PercQuotedTxtVsTotal = (quotedTextTkns / totalTokens) * 100

  val quotedLanguageTkns = commentTokens.filter(_.isQuotedLanguage).size.toDouble
  val PercQuotedLangVsTotal = (quotedLanguageTkns / totalTokens) * 100

  val nonDirectVoiceTkns = quotedTextTkns + quotedLanguageTkns
  val PercNonDirectVsTotal = (nonDirectVoiceTkns / totalTokens) * 100

  val percentages = Vector(PercDirectVsTotal,PercQuotedTxtVsTotal,PercQuotedLangVsTotal,PercNonDirectVsTotal)
  val roundPerc = percentages.map(math.BigDecimal(_).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  println(scholiaType + "\t" + scholiaFreq + "\t" + totalTokens.toInt + "\t" + totDirectVoiceTkns.toInt + " (" + roundPerc(0) + "%)\t" + quotedTextTkns.toInt + " (" + roundPerc(1) + "%)\t" + quotedLanguageTkns.toInt + " (" + roundPerc(2) + "%)\t" + nonDirectVoiceTkns.toInt + " (" + roundPerc(3) + "%)")
}
