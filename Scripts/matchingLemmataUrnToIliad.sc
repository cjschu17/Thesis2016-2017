import scala.io.Source
import scala.xml.XML

@main
def lemmataToIliad(file: String, file2: String) {

  val srcFile = scala.io.Source.fromFile(file).getLines.toVector
  val scholiaToIliad = scala.io.Source.fromFile(file2).getLines.toVector.map(_.split("\t"))

  val lemmataUrns = srcFile.filter(_.contains("urn"))
  val lemmataUrnVector = lemmataUrns.map(CtsUrn(_))
  val scholiaVector = lemmataUrnVector.map(_.collapseBy(1).toString)

  val scholiaOfInterest = corpus ~~ lemmataUrnVector

  val textContent = scholiaOfInterest.contents
  val text = textContent.map(XML.loadString(_).text)

  val matchingPairs = scholiaVector.map(matchingLines(_,scholiaToIliad).flatten.toArray)
  val iliadUrns = matchingPairs.map(_(1))

  val tuple = scholiaVector zip text zip iliadUrns
  val formattedTuple = tuple.map(e => (e._1._1,e._1._2,e._2))

  for (l <- formattedTuple) {

    println(l._1 + ",\"" + l._2 + "\"," + l._3 + ",")

  }
}


def matchingLines(scholiaUrn: String, scholiaToIliad: Vector[Array[String]]) = {

  val scholMatch = scholiaToIliad.filter(_(0) == scholiaUrn)
  scholMatch

}
