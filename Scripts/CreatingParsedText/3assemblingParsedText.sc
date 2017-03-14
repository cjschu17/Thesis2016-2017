import scala.io.Source

case class IdTriple (surface: String, pos: String, lemma: String)

@main
def main(byzEdition: String, indexOfParses: String) {

  val byzorthoEdition = scala.io.Source.fromFile(byzEdition).getLines.toVector
  val byzorthoColumns = byzorthoEdition.map(s => s.split("\t"))
  val noPuncTuple= byzorthoColumns.map( a => (a(0),a(1).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
  val urnWordArrayTuple = noPuncTuple.map( row => (row._1,row._2.split(" ").filterNot(_.isEmpty)))

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
