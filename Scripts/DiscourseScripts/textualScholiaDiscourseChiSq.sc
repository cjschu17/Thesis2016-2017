//this script is meant to run in the sbt console within the hmt-twiddle repo
//It is meant to conduct a chi-squared test, to determine which of the two models of
//the, so-called textual scholia, is more accuracte. Start-End or Im-Int.
//It does this by looking that the number of scholia within each classification to see if
//there is a difference in kind of discourse between the two models.

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

println("Scholia Type\tNumber of Scholia With Non-Direct Speech\tNumber of Scholia with no Non-Direct Speech\tNumber Of Scholia")


val data = scholiaTypes.map(frequency(_,scholia))

chiSq("StartIm EndIm",data(0),data(3))
chiSq("StartInt EndInt",data(2),data(1))
chiSq("StartIm StartInt",data(0),data(2))
chiSq("EndIm EndInt",data(3),data(1))
chiSq("StartIm EndInt",data(0),data(1))
chiSq("StartInt EndIm",data(2),data(3))
chiSq("Start End",data(4),data(5))
chiSq("Im Int",data(6),data(7))



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
  println("Scholia Type\t" + indirectVoiceSchol.toInt + "\t" + noIndirectVoiceSchol.toInt + numberOfScholia)
  frequencies
}

def chiSq(label: String, data1: Array[Double], data2: Array[Double]) = {

  val a = data1(0)
  val b = data1(1)
  val c = data2(0)
  val d = data2(1)

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

  val significanceArray = Array(label,math.BigDecimal(chiSq).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble,significance)
  println(significanceArray(0) + "\t" + significanceArray(1) + "\t" + significanceArray(2))

}
