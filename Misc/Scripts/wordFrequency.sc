//This script is designed to give a historgram of all the words in the second column
// of a 2-column text file, separated by a tab.

//USAGE: amm wordFrequency.sc [File Location]

import scala.io.Source

@main
def wordCount(fileName: String) {

  val importedFile = Source.fromFile(fileName).getLines.toVector

  val words = importedFile.map(_.split("\t")(1).split(" ")).flatten

  val historgram = words.groupBy(w => w).map{ case (k,v) => (k,v.size)}.toSeq.sortBy(_._2)

  for (entry <- historgram) {

    println(entry._1 + "\t" + entry._2)

  }
}
