import scala.io.Source

val scholiaToIliad = Source.fromFile("../hmt-twiddle/data/scholiaToIliad.tsv").getLines.toVector.map(_.split("\t"))

val distinctLinesWithSchol = scholiaToIliad.groupBy(w => w(1)).toVector
println("Total Lines Of Iliad With Scholia")
println("main\tim\tint\til\text\tTotal")
println(distinctLinesWithSchol.filter(_._2.flatten.mkString(",").contains("6.msA.h")).size + "\t" + distinctLinesWithSchol.filter(_._2.flatten.mkString(",").contains("6.msAim.h")).size + "\t" + distinctLinesWithSchol.filter(_._2.flatten.mkString(",").contains("6.msAint.h")).size + "\t" + distinctLinesWithSchol.filter(_._2.flatten.mkString(",").contains("6.msAil.h")).size + "\t" + distinctLinesWithSchol.filter(_._2.flatten.mkString(",").contains("6.msAext.h")).size + "\t" + distinctLinesWithSchol.size + "\n")


val linesWithOneSchol = distinctLinesWithSchol.filter(urn => urn._2.size == 1).map(_._2).flatten
println("Lines of the Iliad with exactly one scholion")
println("main\tim\tint\til\text")
println(linesWithOneSchol.filter(_.mkString(",").contains("msA.h")).size + "\t" + linesWithOneSchol.filter(_.mkString(",").contains("msAim")).size + "\t" + linesWithOneSchol.filter(_.mkString(",").contains("msAint")).size + "\t" + linesWithOneSchol.filter(_.mkString(",").contains("msAil")).size + "\t" + linesWithOneSchol.filter(_.mkString(",").contains("msAext")).size + "\n")


val scholiaOnSameLine = distinctLinesWithSchol.filter(urn => urn._2.size > 1).map(_._2)

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

val onlySameTypes = labelledTuple.filterNot(_._3.contains("diff"))

println("Lines of the Iliad with more than on scholion, but same zone")
println("main\tim\tint\til\text")
println(onlySameTypes.filter(_._2(0).contains("msA.h")).size + "\t" + onlySameTypes.filter(_._2(0).contains("msAim")).size + "\t" + onlySameTypes.filter(_._2(0).contains("msAint")).size + "\t" + onlySameTypes.filter(_._2(0).contains("msAil")).size + "\t" + onlySameTypes.filter(_._2(0).contains("msAext")).size + "\n")

val onlyDifferingTypes = labelledTuple.filter(_._3.contains("diff"))

val readableTuple = onlyDifferingTypes.map(line => (line._1,line._2.mkString(","))).sortBy(_._1)

for (tup <- readableTuple) {

  println(tup._1 + "\t" + tup._2)

}

val numScholiaOnLine = readableTuple.map(_._2.split(",").size).distinct.sortWith(_ < _)

for (i <- numScholiaOnLine) {
  println("\nLines with " + i.toString + " Scholia\n")
  val scholPerLine = readableTuple.filter(_._2.split(",").size == i)
  for (entry <- scholPerLine) {

    var label = ""
    if (entry._2.contains("6.msA.h") == true) {
      label += "Main & "
    }

    if (entry._2.contains("msAim") == true) {
      label += "Im & "
    }

    if (entry._2.contains("6.msAint.h") == true) {
      label += "Int & "
    }

    if (entry._2.contains("6.msAil.h") == true) {
      label += "Il & "
    }

    if (entry._2.contains("6.msAext.h") == true) {
      label += "Ext & "
    }
    val finalLabel = label.dropRight(3)
    println(entry._1 + "," + finalLabel + "," + entry._2)

  }
}

println("mainAndIm\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("mainAndInt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filter(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("mainAndIl\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("mainAndExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("imAndIl\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("IntAndIm\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("imAndExt\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("extAndInt\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filter(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("intAndIl\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filter(_._2.contains("msAint")).filter(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("ilAndExt\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("mainImInt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("mainImIl\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("mainImExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("mainIntIl\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filter(_._2.contains("msAint")).filter(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("mainIntExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filter(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("mainIlExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("ImIntIl\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filter(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("ImIntExt\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("ImIlExt\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("IntIlExt\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("mainImIntIl\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filter(_._2.contains("msAil")).filterNot(_._2.contains("msAext")).size)

println("mainImIntExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filterNot(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("mainImIlExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filterNot(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("mainIntIlExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filterNot(_._2.contains("msAim")).filter(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("ImIntIlExt\t" +  readableTuple.filterNot(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("mainImIntIlExt\t" +  readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("msAim")).filter(_._2.contains("msAint")).filter(_._2.contains("msAil")).filter(_._2.contains("msAext")).size)

println("allExtMain\t" + readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("6.msAext")).size)

println("allExtIm\t" + readableTuple.filter(_._2.contains("6.msAext")).filter(_._2.contains("6.msAim")).size)

println("allExtInt\t" + readableTuple.filter(_._2.contains("6.msAext")).filter(_._2.contains("6.msAint")).size)

println("allExtIl\t" + readableTuple.filter(_._2.contains("6.msAext")).filter(_._2.contains("6.msAil")).size)


println("allMainIm\t" + readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("6.msAim")).size)

println("allMainInt\t" + readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("6.msAint")).size)

println("allMainIl\t" + readableTuple.filter(_._2.contains("6.msA.h")).filter(_._2.contains("6.msAil")).size)

println("allImInt\t" + readableTuple.filter(_._2.contains("6.msAim")).filter(_._2.contains("6.msAint")).size)

println("allImIl\t" + readableTuple.filter(_._2.contains("6.msAim")).filter(_._2.contains("6.msAil")).size)

println("allIntIl\t" + readableTuple.filter(_._2.contains("6.msAil")).filter(_._2.contains("6.msAint")).size)

println("All Main\t" + readableTuple.filter(_._2.contains("6.msA.h")).size)
println("All Im\t" + readableTuple.filter(_._2.contains("6.msAim")).size)
println("All Int\t" + readableTuple.filter(_._2.contains("6.msAil")).size)
println("All Il\t" + readableTuple.filter(_._2.contains("6.msAint")).size)
println("All Ext\t" + readableTuple.filter(_._2.contains("6.msAext")).size)
