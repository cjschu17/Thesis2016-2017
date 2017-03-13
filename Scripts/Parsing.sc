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
  val morphReplies = uniqueWords.map(word => (word,parse(word)))
  for (m <- morphReplies) {
    println(m._1 + "\t" + m._2)
  }

}


def parse (s: String): String = {

  val baseUrl = "https://services.perseids.org/bsp/morphologyservice/analysis/word?lang=grc&engine=morpheusgrc&word="
  val request = baseUrl + s
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
