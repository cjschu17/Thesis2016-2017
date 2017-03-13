import scala.io.Source
import scala.xml._

case class IdTriple (surface: String, pos: String, lemma: String)

@main
def main(srcString: String, byzorthoVersion: String) {

  val srcFile = scala.io.Source.fromFile(srcString).getLines.toVector
  val srcFile2 = scala.io.Source.fromFile(byzorthoVersion).getLines.toVector
  val byzorthoColumns = srcFile2.map(s => s.split("\t"))
  val noPuncTuple= byzorthoColumns.map( a => (a(0),a(1).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
  val urnWordArrayTuple = noPuncTuple.map( row => (row._1,row._2.split(" ").filterNot(_.isEmpty)))
  val morphReplies = srcFile.map(_.replaceAll(",<rdf:RDF","\t<rdf:RDF").split("\t"))
  val noErrors = morphReplies.filter(_.size == 2).filterNot(_(1).contains("Error from parsing service."))
  val noErrors2 = noErrors.map(s => (s(0),s(1)))
  val idColumn = noErrors2.map(_._1)
  val xmlColumn = noErrors2.map(_._2)

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

  val validReplies = morphAnalyses.filterNot(_._1.isEmpty)

  val idAnalyzed = idColumn.zip(validReplies)
  val tripleId = idAnalyzed.map(row => IdTriple(row._1,row._2._2,row._2._1))

  val urnToParse = replacement(urnWordArrayTuple,tripleId)

  val newlyParsedText = urnToParse.map(_.split("\t")).filter(_.size == 2).groupBy(w => w(0)).map{ case (k,v) => (k,v.map(e => e(1)))}
  val sortedParsedText = newlyParsedText.toSeq.sortBy(_._1).toVector



  for (c <- sortedParsedText) {
    println(c._1 + "\t" + c._2)
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

def replacement(wdLists: Vector[(String, Array[String])],  tripleList: Vector[IdTriple]) = {
  var line = ""


  for (wdList <- wdLists) {
    val urn = wdList._1
    val words = wdList._2
    for (word <- words) {

      for (answer <- tripleForString(word,tripleList) ) {
          line += urn + "\t" + answer.lemma + "\n"
          println(answer.lemma)
      }
    }
  }

  line.split("\n").toVector

}

def tripleForString(s: String, triples: Vector[IdTriple] ): Vector[IdTriple] = {
    val resultList = triples.filter(_.surface == s)
    if (resultList.size > 0) {
      resultList
    } else {
      val oneEntry = IdTriple(s,"unknown",s)
      Vector(oneEntry)
    }
}
