//this script is meant to run in the sbt console within the hmt-twiddle repo
//It is designed to give descriptive statistics about the Textual scholia,
//Though broken up into the various columns of describing them
//Uses source material from Thesis2016-2017/data/DiscourseData/TextualScholia/TextualScholiaColumns

:load loadhmt.sc
import scala.math._

val startIm = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/startImUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val endInt = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/endIntUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val startInt = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/startIntUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val endIm = scala.io.Source.fromFile("../Thesis2016-2017/data/TextualScholiaColumns/endImUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val start = startIm ++ startInt
val end = endInt ++ endIm
val int = startInt ++ endInt
val im = startIm ++ endIm

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus.~~(CtsUrn("urn:cts:greekLit:tlg5026:"))
val scholiaTypes = Vector(startIm,endInt,startInt,endIm,start,end,im,int)

println("Scholia Type\tNumber of Words\tNumber of Direct Voice Words\tNumber of Quoted Text Words\tNumber of Quoted Language Words\tNumber of Non-Direct Voice Word")

scholiaTypes.map(histogram(_,scholia))

def histogram(scholiaType: Vector[CtsUrn], scholia: edu.holycross.shot.ohco2.Corpus) = {

  val scholiaTypeCorpus = scholia ~~ scholiaType
  val bk3Urns = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:3"),CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:3"))
  val bk6Urns = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:6"),CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:6"))
  val errantExtUrn = CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:12.A1")
  val editedCorpus = scholiaTypeCorpus.--(scholia.~~(bk3Urns)).--(scholia.~~(bk6Urns)).--(scholia.~~(errantExtUrn))
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
  println("Scholia Type" + "\t" + totalTokens.toInt + "\t" + totDirectVoiceTkns.toInt + " (" + roundPerc(0) + "%)\t" + quotedTextTkns.toInt + " (" + roundPerc(1) + "%)\t" + quotedLanguageTkns.toInt + " (" + roundPerc(2) + "%)\t" + nonDirectVoiceTkns.toInt + " (" + roundPerc(3) + "%)")
  val relevantPerc = Vector(roundPerc(0),roundPerc(3))
  relevantPerc
}
