:load loadhmt.sc
import scala.math._

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus.~~(CtsUrn("urn:cts:greekLit:tlg5026:"))
val scholiaTypes = Vector("msA.hmt","msAim.hmt","msAint.hmt","msAil.hmt","msAext.hmt")
val commentUrns = scholia.urns.map(_.toString).filter(_.contains("comment")).map(CtsUrn(_))
val bk3 = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:3"),CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:3"),CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:3"),CtsUrn("urn:cts:greekLit:tlg5026.msAil.hmt:3"),CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:3"))
val bk6 = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:6"),CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:6"),CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:6"),CtsUrn("urn:cts:greekLit:tlg5026.msAil.hmt:6"),CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:6"))
val errantExtUrn = CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:12.A1")
val editedCorpus = scholia.--(scholia.~~(bk3)).--(scholia.~~(bk6)).--(scholia.~~(errantExtUrn)).~~(commentUrns)
val tokenAnalysis = TeiReader.fromCorpus(editedCorpus)

val totalTokens = tokenAnalysis.size.toDouble
var totalTknsSizes = ""
for (s <- scholiaTypes) {
  totalTknsSizes += tokenAnalysis.filter(_.textNode.toString.contains("urn:cts:greekLit:tlg5026." + s + ":")).size.toString + "\n"
}
val totalSizes = totalTknsSizes.split("\n").filterNot(_.isEmpty).map(_.toDouble)
val totalPerc = totalSizes.map(n => (n / totalTokens) * 100 )
val totalRoundedPerc = totalPerc.map(math.BigDecimal(_).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
val totalData = totalSizes zip totalRoundedPerc

val directVoiceTkns = tokenAnalysis.filter(_.isDirectVoice)
val citedVoiceTkns = tokenAnalysis.filter(_.isCitation)
val totDirectVoiceTkns = directVoiceTkns ++ citedVoiceTkns
val directTknsSize = totDirectVoiceTkns.size.toDouble
var directTknsSizes = ""
for (s <- scholiaTypes) {
  directTknsSizes += totDirectVoiceTkns.filter(_.textNode.toString.contains("urn:cts:greekLit:tlg5026." + s + ":")).size.toString + "\n"
}
val directSizes = directTknsSizes.split("\n").filterNot(_.isEmpty).map(_.toDouble)
val directPerc = directSizes.map(n => (n / directTknsSize) * 100 )
val directRoundedPerc = directPerc.map(math.BigDecimal(_).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
val directData = directSizes zip directRoundedPerc

val quotedTextTkns = tokenAnalysis.filter(_.isQuotedText)
val quotedTextTknsSize = quotedTextTkns.size.toDouble
var qTxtTknsSizes = ""
for (s <- scholiaTypes) {
  qTxtTknsSizes += quotedTextTkns.filter(_.textNode.toString.contains("urn:cts:greekLit:tlg5026." + s + ":")).size.toString + "\n"
}
val qTxtSizes = qTxtTknsSizes.split("\n").filterNot(_.isEmpty).map(_.toDouble)
val qTxtPerc = qTxtSizes.map(n => (n / quotedTextTknsSize) * 100)
val qTxtRoundedPerc = qTxtPerc.map(math.BigDecimal(_).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
val qTxtData = qTxtSizes zip qTxtRoundedPerc


val quotedLanguageTkns = tokenAnalysis.filter(_.isQuotedLanguage)
val quotedLangTknsSize = quotedLanguageTkns.size.toDouble
var qLangTknsSizes = ""
for (s <- scholiaTypes) {
  qLangTknsSizes += quotedLanguageTkns.filter(_.textNode.toString.contains("urn:cts:greekLit:tlg5026." + s + ":")).size.toString + "\n"
}
val qLangSizes = qLangTknsSizes.split("\n").filterNot(_.isEmpty).map(_.toDouble)
val qLangPerc = qLangSizes.map(n => (n / quotedLangTknsSize) * 100)
val qLangRoundedPerc = qLangPerc.map(math.BigDecimal(_).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
val qLangData = qLangSizes zip qLangRoundedPerc


val nonDirectVoiceTkns = quotedTextTkns ++ quotedLanguageTkns
val nonDirectTknsSize = nonDirectVoiceTkns.size.toDouble
var nonDirectTknsSizes = ""
for (s <- scholiaTypes) {
  nonDirectTknsSizes += nonDirectVoiceTkns.filter(_.textNode.toString.contains("urn:cts:greekLit:tlg5026." + s + ":")).size.toString + "\n"
}
val nonDirectSizes = nonDirectTknsSizes.split("\n").filterNot(_.isEmpty).map(_.toDouble)
val nonDirectPerc = nonDirectSizes.map(n => (n / nonDirectTknsSize) * 100)
val nonDirectRoundedPerc = nonDirectPerc.map(math.BigDecimal(_).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
val nonDirectData = nonDirectSizes zip nonDirectRoundedPerc

val allData = Vector(directData,qTxtData,qLangData,nonDirectData,totalData)




println("Type of Discourse\tNumber of Words Within that Type of Discourse\tNumber of words within Discouse and within msA\tNumber of words within Discouse and within msAim\tNumber of words within Discouse and within msAint\tNumber of words within Discouse and within msAil\tNumber of words within Discouse and within msAext")
println("Direct Discourse\t" + directTknsSize + "\t" + allData(0)(0)._1 + " (" + allData(0)(0)._2 + "%)\t" + allData(0)(1)._1 + " (" + allData(0)(1)._2 + "%)\t" + allData(0)(2)._1 + " (" + allData(0)(2)._2 + "%)\t" + allData(0)(3)._1 + " (" + allData(0)(3)._2 + "%)\t" + allData(0)(4)._1 + " (" + allData(0)(4)._2 + "%)")
println("Quoted Text\t" + quotedTextTknsSize + "\t" + allData(1)(0)._1 + " (" + allData(1)(0)._2 + "%)\t" + allData(1)(1)._1 + " (" + allData(1)(1)._2 + "%)\t" + allData(1)(2)._1 + " (" + allData(1)(2)._2 + "%)\t" + allData(1)(3)._1 + " (" + allData(1)(3)._2 + "%)\t" + allData(1)(4)._1 + " (" + allData(1)(4)._2 + "%)")
println("Quoted Language\t" + quotedLangTknsSize + "\t" + allData(2)(0)._1 + " (" + allData(2)(0)._2 + "%)\t" + allData(2)(1)._1 + " (" + allData(2)(1)._2 + "%)\t" + allData(2)(2)._1 + " (" + allData(2)(2)._2 + "%)\t" + allData(2)(3)._1 + " (" + allData(2)(3)._2 + "%)\t" + allData(2)(4)._1 + " (" + allData(2)(4)._2 + "%)")
println("Indirect Discourse\t" + nonDirectTknsSize + "\t" + allData(3)(0)._1 + " (" + allData(3)(0)._2 + "%)\t" + allData(3)(1)._1 + " (" + allData(3)(1)._2 + "%)\t" + allData(3)(2)._1 + " (" + allData(3)(2)._2 + "%)\t" + allData(3)(3)._1 + " (" + allData(3)(3)._2 + "%)\t" + allData(3)(4)._1 + " (" + allData(3)(4)._2 + "%)")
println("Total\t" + totalTokens + "\t" + allData(4)(0)._1 + " (" + allData(4)(0)._2 + "%)\t" + allData(4)(1)._1 + " (" + allData(4)(1)._2 + "%)\t" + allData(4)(2)._1 + " (" + allData(4)(2)._2 + "%)\t" + allData(4)(3)._1 + " (" + allData(4)(3)._2 + "%)\t" + allData(4)(4)._1 + " (" + allData(4)(4)._2 + "%)")
