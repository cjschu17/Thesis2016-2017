import scala.io.Source
import scala.xml._

case class IdTriple (surface: String, pos: String, lemma: String)

@main
def main(morphRepliesTable: String) {

  val srcFile = scala.io.Source.fromFile(morphRepliesTable).getLines.toVector
  val morphReplies = srcFile.map(_.split("\t"))
  val noErrors = morphReplies.filter(_.size == 2).filterNot(_(1).contains("Error from parsing service."))
  val idColumn = noErrors.map(_(0))
  val xmlColumn = noErrors.map(_(1))

  val morphAnalyses = xmlColumn.map { e =>
    val root = XML.loadString(e)
    val entries = root \\ "entry"
    val entryData = entries.map( e => e match {
        case el: Elem => formatEntry(el)
        case _ => ""
      })

    val entryArrays = entryData.map(_.toString.split(","))
    var newLexent = ""
    var newPos = ""

    if (entryData.size > 1) {
      for (e <- entryArrays) {
        newLexent += e(0).drop(1) + "_"
        newPos += e(1).dropRight(1) + "_"
      }
    } else if (entryData.size == 1){
      newLexent += entryArrays(0)(0).drop(1)
      newPos += entryArrays(0)(1).dropRight(1)
      }

    val newEntryData = (newLexent,newPos)
    newEntryData
  }


  val idAnalyzed = idColumn.zip(morphAnalyses)
  val tripleId = idAnalyzed.map(row => IdTriple(row._1,row._2._2,row._2._1))
  val validTripleIds = tripleId.filterNot(_.pos.isEmpty).filterNot(_.lemma.isEmpty)
  for (tID <- validTripleIds) {
    println(tID.surface + "\t" + tID.pos + "\t" + tID.lemma)
  }
}

def formatEntry(e: Elem) = {
  val uriGroup = e \ "@uri"
  val uri = textForFirstEntry(uriGroup).replaceFirst("http://data.perseus.org/collections/urn:cite:perseus:grclexent.","")
  val headWordList = e \\ "hdwd"
  val headWord = textForFirstEntry(headWordList)
  val posList = e \\ "pofs"
  val pos = textForFirstEntry(posList)
  val morphID = (headWord  ,  pos)
  morphID
}

def textForFirstEntry (nseq: NodeSeq): String = {
  if (nseq.size > 0) {
    nseq(0).text
  } else ""
}
