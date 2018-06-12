//This script was designed to normalize an edition of the Venetus A scholia according to modern orthographic standards, 
//as opposed to those used by the Byzantine scribes who created the manuscript. The script requires two inputs. The first input 
//is some 2-column version of a Greek text, wherein the first column contains that CTS URN identifiers of the text and the 
//second column contains the text intended to be normalized. The second input is the Homer Multitext's compilation of the 
//various orthographic variants found in the Venetus A. The compilation is a five-columned .tsv file, where the first file 
//contains a CTS URN identifier for each specific orthographic variant, the second contains the orthographic variant as it 
//appears in the manuscript, the third contains the modern equivalent of the variant, the fourth contains the status of the 
//normalization's propsosal ("propsoed," "accepted," etc.), and fifth contains any extraneous comments. The result of this 
//script is another version of the text, o-normalized, which is identical in format to the text that was used as the input. 
//So it will contain two columns where the first column contains the CTS URN for a scholion and the second column contains the
//text of the scholion but with every word replaced with its o-normalized form. 


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
