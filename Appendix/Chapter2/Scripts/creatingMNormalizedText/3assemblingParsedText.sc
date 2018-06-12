//This script aligns a text of interest with an index of morphological parses in order to globally replace the words of the 
//text with their lemmata. The script requires two inputs. The first is the file containing the text to be normalized, 
//formatted in two columns with the first column containing CTS URNs for the text and the second column containing the 
//text itself. This first input should be the same input as the text that was used for the frist script in the m-normalization
//process. The second input is  the three-columned index of morphological parses that was created by the second step of the 
//m-normalization process, wherein the first column contains the word analyzed by the Morpheus parser, the second column 
//contains the parts of the speech the word could be recognized as, and the third contains the possible lemmata of the word 
//as determined by Morpheus. The result of this script is another version of the text, p-normalized, which is identical in 
//format to the text that was used as the input. So it will contain two columns where the first column contains the CTS URN 
//for a scholion and the second column contains the text of the scholion but with every word replaced with its lemma or lemmata. 

import scala.io.Source

case class IdTriple (surface: String, pos: String, lemma: String)

@main
def main(byzEdition: String, indexOfParses: String) {

  val byzorthoEdition = scala.io.Source.fromFile(byzEdition).getLines.toVector
  val byzorthoColumns = byzorthoEdition.map(s => s.split("\t"))
  val noPuncTuple= byzorthoColumns.map( a => (a(0),a(1).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
  val urnWordArrayTuple = noPuncTuple.map( row => (row._1,row._2.split(" ").toLowerCase.filterNot(_.isEmpty)))

  val indexVector = scala.io.Source.fromFile(indexOfParses).getLines.toVector
  val indexArray = indexVector.map(_.split("\t"))
  val tripleId = indexArray.map(row => IdTriple(row(0),row(1),row(2)))

  val urnToParse = replacement(urnWordArrayTuple,tripleId)

  val newlyParsedText = urnToParse.map(_.split("\t")).filter(_.size == 2).groupBy(w => w(0)).map{ case (k,v) => (k,v.map(e => e(1)))}
  val sortedParsedText = newlyParsedText.toSeq.sortBy(_._1).toVector
  val finalParsedText = sortedParsedText.map(schol => (schol._1,schol._2.mkString(" ")))


  for (c <- finalParsedText) {
    println(c._1 + "\t" + c._2)
  }

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
