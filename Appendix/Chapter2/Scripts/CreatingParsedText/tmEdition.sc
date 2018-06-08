import scala.io.Source

val parsedEdition = Source.fromFile("scholia-parsed.tsv").getLines.toVector
val noLemma = parsedEdition.filterNot(_.contains("lemma"))
val swList = Source.fromFile("stopWords.txt").getLines.toVector
val TMedition = noLemma.map(_.split("\t")).map(schol => (schol(0),schol(1).split(" ").map(function(_,swList)).filterNot(_.isEmpty).mkString(" ")))
val noUnderscores = TMedition.map(schol => (schol._1,schol._2.replaceAll("_","X")))


for (schol <- noUnderscores){
  println(schol._1 + "\t" + schol._2)
}


def function(word: String, swList: Vector[String]): String = {

  var newWord = ""
  val results = swList.filter(_ == word)
  if (results.size == 0 ){
    newWord += word
  }
  newWord
}
