import scala.io.Source
import scala.xml._

case class IdTriple (surface: String, pos: String, lemma: String)

@main
def mainFunc(srcFile: String) {

  val byzorthoEdition = scala.io.Source.fromFile(srcFile).getLines.toVector
  val byzorthoColumns = byzorthoEdition.map(s => s.split("\t"))
  val noPuncTuple= byzorthoColumns.map( a => (a(0),a(1).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
  val urnWordArrayTuple = noPuncTuple.map( row => (row._1,row._2.split(" ").filterNot(_.isEmpty)))
  val uniqueWords = urnWordArrayTuple.map(_._2).flatten.groupBy( w => w).map(_._1).toVector
  var baseNumber = 0
  for (word <- uniqueWords) {
    baseNumber += 1
    val morphReplies = (word,parse(word,uniqueWords.size,baseNumber))
  }
  val filterOutErrors = morphReplies.filterNot(_._2.contains("Error from parsing service."))
  val idColumn = filterOutErrors.map(_._1)
  val xmlColumn = filterOutErrors.map(_._2)

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
    } else {
      newLexent += entryArrays(0)(0).drop(1)
      newPos += entryArrays(0)(1).dropRight(1)
      }

    val newEntryData = (newLexent,newPos)
    newEntryData
  }

  val idAnalyzed = idColumn.zip(morphAnalyses)
  val tripleId = idAnalyzed.map(row => IdTriple(row._1,row._2._1,row._2._2))

  val urnToParse = replacement(urnWordArrayTuple,tripleId)

}

def parse (s: String, count: Int, baseNumber: Int): String = {

  val baseUrl = "https://services.perseids.org/bsp/morphologyservice/analysis/word?lang=grc&engine=morpheusgrc&word="
  val request = baseUrl + s
  println("Currently working on: " + s + ", " + baseNumber + " of " + count + ".")
  getMorphReply(request)
}

def  getMorphReply(request: String) : String = {
  var reply : String = ""
  try {
    reply = scala.io.Source.fromURL(request).mkString.replaceAll("\n"," ")
  } catch {
    case _ => reply = "Error from parsing service."
  }
  reply
}


def formatEntry(e: Elem) = {
  val uriGroup = e \ "@uri"
  val uri = textForFirstEntry(uriGroup).replaceFirst("http://data.perseus.org/collections/urn:cite:perseus:grclexent.","")
  val headWordList = e \\ "hdwd"
  val headWord = textForFirstEntry(headWordList)
  val posList = e \\ "pofs"
  val pos = textForFirstEntry(posList)
  val morphID = (uri + "_" + headWord  ,  pos)
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
