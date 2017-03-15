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

  val editedWords = morphAnalyses.map(entry => (formatWord(entry._1),entry._2))

  val idAnalyzed = idColumn.zip(editedWords)
  val tripleId = idAnalyzed.map(row => IdTriple(row._1,row._2._2,row._2._1))
  val validTripleIds = tripleId.filterNot(_.pos.isEmpty).filterNot(_.lemma.isEmpty)
  for (tID <- validTripleIds) {
    println(tID.surface + "\t" + tID.pos + "\t" + tID.lemma)
  }
}

def formatEntry(e: Elem) = {
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

def formatWord (word: String): String = {
  var newWord = ""
  val editedWord = word.toLowerCase.replaceAll("[0-9]","")
  if (word.contains("_")) {
    val array = editedWord.split("_")
    val newString = array.groupBy(w => w).keys.toArray.sorted.mkString("_")
    newWord += newString
  } else {
    newWord += editedWord
  }
  newWord
}
