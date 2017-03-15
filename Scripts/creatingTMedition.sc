import scala.io.Source

val parsedEdition = Source.fromFile("scholia-parsed.tsv").getLines.toVector

val parsedByWords = parsedEdition.map(_.split("\t")).map(e => (e(0),e(1).split(" ")))

val stopWordList = Source.fromFile("stopWords.txt").getLines.toVector

val tmEdition = parsedByWords.map(schol => (schol._1,function(schol._2,stopWordList)))

for(schol <- tmEdition) {

  println(schol._1 + "\t" + schol._2)
  
}

def function(scholion: Array[String], swList: Vector[String]): String =  {

  val newScholion = scholion.map(word => function2(word,swList)).filterNot(_.isEmpty).mkString(" ")
  newScholion

}

def function2(word: String, swList: Vector[String]): String = {

  var newWord = ""
  val result = swList.filter(_ == word)

  if (result.size == 0) {
    newWord += word
  }
  newWord
}
