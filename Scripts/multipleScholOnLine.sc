import scala.io.Source

val scholiaToIliad = Source.fromFile("../hmt-twiddle/data/scholiaToIliad.tsv").getLines.toVector.map(_.split("\t"))

val noIlExt = scholiaToIliad.filterNot(_(0).contains("msAil")).filterNot(_(0).contains("msAext"))

val scholiaOnSameLine = noIlExt.groupBy(w => w(1)).toVector.filter(urn => urn._2.size > 1).map(_._2)

val linesWithMultipleSchol = scholiaOnSameLine.map(_(0)(1))

val scholiaUrns = scholiaOnSameLine.map(vec => vec.map(_(0)))

val iliadToScholTuple = linesWithMultipleSchol zip scholiaUrns

def analyzingScholTypes(iliadToSchol: (String, Vector[String])) = {


  val iliadUrn = iliadToSchol._1
  val scholUrns = iliadToSchol._2
  val scholSplit = scholUrns.map(_.split("\\."))
  val scholNum = scholUrns.size
  var label = ""
  for (i <- 1 to (scholNum - 1)) {

    if (scholSplit(0)(1) != scholSplit(i)(1)){
      label += "diff"
    } else {
      label += "same"
    }
    label
  }


  val newTuple = (iliadUrn,scholUrns,label)
  newTuple
}

val labelledTuple = iliadToScholTuple.map(analyzingScholTypes(_))

val onlyDifferingTypes = labelledTuple.filter(_._3.contains("diff"))
val readableTuple = onlyDifferingTypes.map(line => (line._1,line._2.mkString(",")))

val mainAndIm = readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).size
val mainAndInt = readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filter(_._2.contains("msAint")).size
val IntAndIm = readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).size
val allthree = readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).size


for (line <- readableTuple) {

  println(line._1 + "\t" + line._2)

}
