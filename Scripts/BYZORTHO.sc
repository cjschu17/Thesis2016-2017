import scala.io.Source

@main
def BYZORTHO (parsedFile: String, byzorthoLibrary: String) {

  val byzorthoLib = scala.io.Source.fromFile(byzorthoLibrary).getLines.toVector.drop(1)
  val byzArray = byzorthoLib.map(_.split(","))
  val byzProposed = byzArray.filter(_(3) == "proposed")
  val byzTuple = byzProposed.map(line => (line(1),line(2)))

  val corpus = scala.io.Source.fromFile(parsedFile).getLines.toVector
  val corpusColumns = corpus.map(_.split("\t"))

  val byzString = corpusColumns.map(row => (row(0),mkByzorthoString(row(1),byzTuple)))

  for (ln <- byzString) {
    println(ln._1 + "\t" + ln._2)
  }


}




def mkByzorthoString(normalizedText: String, byzList: Vector[(String,String)]): String = {

  val spacedPunctuationText = normalizedText.replaceAll(","," ,").replaceAll("\\."," \\.").replaceAll("·"," ·").replaceAll("⁑"," ⁑").replaceAll(":"," :").replaceAll("⁚"," ⁚").replaceAll("‡"," ‡").replaceAll("·"," ·")
  val tokenizedText = spacedPunctuationText.split(" ").filterNot(_.isEmpty)
  val byzorthoedText = tokenizedText.map(consultByzList(_, byzList)).mkString(" ")
  byzorthoedText
}

def consultByzList(token: String, byzList: Vector[(String,String)]): String = {

  val resultList = byzList.filter(_._1 == token)
  var newToken = ""
  if (resultList.size > 0) {
    newToken += resultList(0)._2
  }
  else {
    newToken += token
  }

  newToken

}
