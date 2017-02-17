import scala.io.Source
import scala.xml.XML

@main
def urns(urnLibrary: String, srcFile: String) {

  val authList = scala.io.Source.fromFile(urnLibrary).getLines.toVector
  val citeUrns = authList.map(_.split(",")(0))
  val nameEntities = authList.map(_.split(",")(1))
  val urnNameTuple = citeUrns zip nameEntities

  urnNameTuple.map(nameHistogram(_,srcFile))

}


def nameHistogram(urnNames: (String,String), srcFile: String) = {

    val srcFileVector = scala.io.Source.fromFile(srcFile).getLines.toVector
    val srcFileArray = srcFileVector.map(s => s.split("\t"))
    val filteredArray = srcFileArray.filterNot(_(0).contains("msAL")).filter(_.size == 2)

    val venAMain = filteredArray.filter(_(0).contains("msA."))
    val venAInt = filteredArray.filter(_(0).contains("msAin"))
    val venAIl = filteredArray.filter(_(0).contains("msAil"))
    val venAExt = filteredArray.filter(_(0).contains("msAe"))
    val venAIm = filteredArray.filter(_(0).contains("msAim"))

    require(filteredArray.size == venAMain.size + venAIm.size + venAIl.size + venAExt.size + venAInt.size)

    val mainHistogram = testing(venAMain)
    val intHistogram = testing(venAInt)
    val ilHistogram = testing(venAIl)
    val extHistogram = testing(venAExt)
    val imHistogram = testing(venAIm)

    //println(mainHistogram)
    //println(intHistogram)
    //println(ilHistogram)
    //println(extHistogram)
    //println(imHistogram)


    finalPrint(urnNames,mainHistogram,intHistogram,ilHistogram,extHistogram,imHistogram)
}

def testing(scholiaType: Vector[Array[String]]) = {

  val justScholia = scholiaType.map(_(1))
  val loadXML = justScholia.map(XML.loadString(_))
  val twoChildXML = loadXML.filter(_.child.size == 2)
  val xmlComments = twoChildXML.map(_.child(1))
  val filteredComments = xmlComments.filter(_.child.size == 1)
  val persNamePerSchol = filteredComments.map(_.child(0) \ "persName").filterNot(_.isEmpty)
  val allPersNames = persNamePerSchol.map(n => n.map(e => e \ "@n")).flatten.filterNot(_.isEmpty)
  val persStrings = allPersNames.map(_.toString)
  val persNameFreqs = persStrings.groupBy(w => w).map { case (k,v) => (k,v.size)}
  val sorted = persNameFreqs.toSeq.sortBy(_._2)

  val scholiaString = xmlComments.map(collectText(_,"")).mkString
  val scholiaWords = scholiaString.replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+","").split(" ").filterNot(_.isEmpty)
  val wordFrequency = scholiaWords.size.toDouble

  val orderedPersNames = sorted.map(s => s._1).toVector
  val orderedFreqs = sorted.map(s => s._2.toDouble).toVector
  val orderedNormalizedFreqs = orderedFreqs.map(normalize(_,wordFrequency))

  val histogram = orderedPersNames zip orderedNormalizedFreqs
  histogram

}

def collectText(n: xml.Node, s: String): String ={
  var txt = s
  n match {
    case t: xml.Text => {txt = txt + t.text}
    case el: xml.Elem => {
      for (ch <- el.child) {
        txt += collectText(ch, s)}
      }
  }
  txt
}

def normalize(frequency: Double, wordFrequency: Double): Double = {
  val normalized = frequency / wordFrequency
  normalized
}


def finalPrint(urnNames: (String,String),mainHistogram: Vector[(String, Double)],intHistogram: Vector[(String, Double)],ilHistogram: Vector[(String, Double)],extHistogram: Vector[(String, Double)],imHistogram: Vector[(String, Double)]) = {

    val mainResult = mainHistogram.filter(_._1 == urnNames._1)
    val intResult = intHistogram.filter(_._1 == urnNames._1)
    val ilResult = ilHistogram.filter(_._1 == urnNames._1)
    val extResult = extHistogram.filter(_._1 == urnNames._1)
    val imResult = imHistogram.filter(_._1 == urnNames._1)

    var mainFreq = 0.0
    var intFreq = 0.0
    var ilFreq = 0.0
    var extFreq = 0.0
    var imFreq = 0.0


    if (mainResult.size > 0 ) {
      mainFreq = mainResult(0)._2
    }

    if (intResult.size > 0 ) {
      intFreq = intResult(0)._2
    }

    if (ilResult.size > 0 ) {
      ilFreq = ilResult(0)._2
    }

    if (extResult.size > 0 ) {
      extFreq = extResult(0)._2
    }

    if (imResult.size > 0 ) {
      imFreq = imResult(0)._2
    }

    println("URN:\t" + urnNames._1 + "\t" + urnNames._2 + "\nType of Scholion\tFrequency Of Appearance\nMain Scholia\t" + mainFreq + "\nInterior Scholia\t" + intFreq + "\nInterlinear Scholia\t" + ilFreq + "\nExterior Scholia\t" + extFreq + "\nIntermarginal Scholia\t" + imFreq + "\n")

  }
