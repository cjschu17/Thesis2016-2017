//this script is meant to run in the sbt console within the hmt-twiddle repo
//It is meant to conduct a chi-squared test, to determine which of the two models of
//the, so-called textual scholia, is more accuracte. Start-End or Im-Int.
//It does this by looking that the number of scholia within each classification to see if
//there is a difference in kind of discourse between the two models.

:load loadhmt.sc
import scala.math._

val startIm = scala.io.Source.fromFile("../startImUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val endInt = scala.io.Source.fromFile("../endIntUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val startInt = scala.io.Source.fromFile("../startIntUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val endIm = scala.io.Source.fromFile("../endImUrns.txt").getLines.toVector.drop(1).map(CtsUrn(_))
val start = startIm ++ startInt
val end = endInt ++ endIm
val int = startInt ++ endInt
val im = startIm ++ endIm

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus.~~(CtsUrn("urn:cts:greekLit:tlg5026:"))
val scholiaTypes = Vector(start,end,int,im)

println("Scholia Type\tNumber Of Scholia\tNumber of Scholia With Non-Direct Speech\tNumber of Scholia with no Non-Direct Speech")


val data = scholiaTypes.map(frequency(_,scholia))
seChiSq(data(0),data(1))
intImChiSq(data(2),data(3))



def frequency(scholiaType: Vector[CtsUrn], scholia: edu.holycross.shot.ohco2.Corpus) = {

  val commentUrns = scholia.urns.map(_.toString).filter(_.contains("comment")).map(CtsUrn(_))
  val bk3Urns = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:3"),CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:3"))
  val bk6Urns = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:6"),CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:6"))
  val errantExtUrn = CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:12.A1")
  val editedCorpus = scholia.--(scholia.~~(bk3Urns)).--(scholia.~~(bk6Urns)).--(scholia.~~(errantExtUrn)).~~(scholiaType).~~(commentUrns)
  val tokensPerScholia = editedCorpus.nodes.map(node => TeiReader.fromCorpus(Corpus(Vector(node)))).filterNot(_.size == 0)
  val numberOfScholia = tokensPerScholia.size
  val scholionLength = tokensPerScholia.map(_.size.toDouble)
  val indirectVoiceSchol = tokensPerScholia.map(_.filter(_.notDirectVoice)).filterNot(_.isEmpty).size.toDouble
  val noIndirectVoiceSchol = numberOfScholia - indirectVoiceSchol
  val frequencies = Array(indirectVoiceSchol,noIndirectVoiceSchol)
  println("Scholia Type\t" + numberOfScholia + "\t" + indirectVoiceSchol.toInt + "\t" + noIndirectVoiceSchol.toInt)
  frequencies
}

def seChiSq(startData: Array[Double], endData: Array[Double]) = {

  val a = startData(0)
  val b = startData(1)
  val c = endData(0)
  val d = endData(1)

  val numerator1 = math.pow(((a * d)-(b * c)),2.0)
  val numerator2 = (a + b + c + d)
  val numeratorFinal = numerator1 * numerator2

  val denominator = ((a + b) * (c + d) * (b + d) * (a + c))

  val chiSq = numeratorFinal / denominator

  var significance = ""
  if (chiSq > 3.841) {
    significance += "Statisically Significant"
  } else {
    significance += "Not Statistically Significant"
  }

  val significanceArray = Array("Start End Data",math.BigDecimal(chiSq).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble,significance)
  println(significanceArray(0) + "\t" + significanceArray(1) + "\t" + significanceArray(2))

}

def intImChiSq(intData: Array[Double], imData: Array[Double]) = {

  val a = intData(0)
  val b = intData(1)
  val c = imData(0)
  val d = imData(1)

  val numerator1 = math.pow(((a * d)-(b * c)),2.0)
  val numerator2 = (a + b + c + d)
  val numeratorFinal = numerator1 * numerator2

  val denominator = ((a + b) * (c + d) * (b + d) * (a + c))

  val chiSq = numeratorFinal / denominator

  var significance = ""
  if (chiSq > 3.841) {
    significance += "Statisically Significant"
  } else {
    significance += "Not Statistically Significant"
  }

  val significanceArray = Array("Int Im Data",math.BigDecimal(chiSq).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble,significance)
  println(significanceArray(0) + "\t" + significanceArray(1) + "\t" + significanceArray(2))

}
