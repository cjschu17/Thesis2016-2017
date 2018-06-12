//The purpose of this script is to send each Greek word within a specified text through the Morpheus morphological parser. 
//This script requires only one input. That input is some 2-column version of a Greek text, wherein the first column contains 
//that CTS URN identifiers of the text and the second column contains the corresponding text intended to be normalized. The 
//result is the creation of a two-column file wherein the first column contains a word that was analyzed through the Morpheus 
//parser and the second column contains the complete morphological analysis by the Morpheus parser. 

import scala.io.Source
import scala.xml._

case class IdTriple (surface: String, pos: String, lemma: String)

@main
def mainFunc(srcFile: String) {

  val byzorthoEdition = scala.io.Source.fromFile(srcFile).getLines.toVector
  val byzorthoColumns = byzorthoEdition.map(s => s.split("\t"))
  val noPuncTuple= byzorthoColumns.map( a => (a(0),a(1).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
  val urnWordArrayTuple = noPuncTuple.map( row => (row._1,row._2.split(" ").filterNot(_.isEmpty)))
  val uniqueWords = urnWordArrayTuple.map(_._2).flatten.groupBy( w => w).map(_._1.toLowerCase).toVector
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
