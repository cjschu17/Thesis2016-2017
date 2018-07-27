//This script does a lot with personal names in the scholia.
//Usage:  amm SCRIPT authLists/data/names.csv hmt_2cols.tsv

import scala.io.Source
import scala.xml.XML

@main
def urns(urnLibrary: String, srcFile: String) {

  val authList = scala.io.Source.fromFile(urnLibrary).getLines.toVector
  val citeUrns = authList.map(_.split(",")(0))
  val nameEntities = authList.map(_.split(",")(1))
  val urnNameTuple = (citeUrns zip nameEntities).drop(1)

  nameHistogram(urnNameTuple,srcFile)

}


def nameHistogram(urnNames: Vector[(String,String)], srcFile: String) = {

    val srcFileVector = scala.io.Source.fromFile(srcFile).getLines.toVector
    val srcFileArray = srcFileVector.map(s => s.split("\t"))
    val filteredArray = srcFileArray.filter(_(0).contains("5026")).filterNot(_(0).contains("msAL")).filterNot(_(0).contains(".hmt:3")).filterNot(_(0).contains(".hmt:6")).filter(_.size == 2)
    val venAMain = filteredArray.filter(_(0).contains("msA."))
    val venAInt = filteredArray.filter(_(0).contains("msAin"))
    val venAIl = filteredArray.filter(_(0).contains("msAil"))
    val venAExt = filteredArray.filter(_(0).contains("msAe"))
    val venAIm = filteredArray.filter(_(0).contains("msAim"))

    require(filteredArray.size == venAMain.size + venAIm.size + venAIl.size + venAExt.size + venAInt.size)

    val mainHistogram = testing(venAMain,urnNames,"Main")
    val mainNames = mainHistogram.map(row => (row._1,row._2))

    val intHistogram = testing(venAInt,urnNames,"Interior"
    val intNames = intHistogram.map(row => (row._1,row._2))

    val ilHistogram = testing(venAIl,urnNames,"Interlinear"
    val ilNames = ilHistogram.map(row => (row._1,row._2))

    val extHistogram = testing(venAExt,urnNames,"Exterior")
    val extNames = extHistogram.map(row => (row._1,row._2))

    val imHistogram = testing(venAIm,urnNames,"Intermarginal")
    val imNames = imHistogram.map(row => (row._1,row._2))

    val over1im = imHistogram.filter(_._4.dropRight(1).toDouble > 1.0).map(row => (row._1,row._2))
    val over1int = intHistogram.filter(_._4.dropRight(1).toDouble > 1.0).map(row => (row._1,row._2))
    val over1main = mainHistogram.filter(_._4.dropRight(1).toDouble > 1.0).map(row => (row._1,row._2))
    val intersectOver1 = over1im.intersect(over1int).intersect(over1main)
    println("Words which appear in main, Im, & Int, with a frequency within that zone's names greter than 1%")
    for (i <- intersectOver1){
      println(i)
    }

    val imNotInt = imNames.diff(intNames)
    val meaningImNoInt = imNotInt.map(matching(_,imHistogram)).filter(_.size > 0)
    println("Words which appear in the Im, not the Int, but at least more than once")
    println("Urn\tName\tAppearances in the Im")
    for (m <- meaningImNoInt){
      println(m(0)._1 + "\t" + m(0)._2 + "\t" + m(0)._3)
    }
    val intNotIm = intNames.diff(imNames)
    val meaningIntNoIm = intNotIm.map(matching(_,imHistogram)).filter(_.size > 0)
    println("Words which appear in the Int, not the Im, but at least more than once")
    println("Urn\tName\tAppearances in the Im")
    for (m <- meaningIntNoIm){
      println(m(0)._1 + "\t" + m(0)._2 + "\t" + m(0)._3)
    }

    val meaninfulMainExcl = (mainNames.diff(intNames).diff(imNames)).map(matching(_,mainHistogram)).filter(_.size > 0)
    for (m <- meaninfulMainExcl){
      println(m(0)._1 + "\t" + m(0)._2 + "\t" + m(0)._3)
    }

    val allScholiaNames = mainNames ++ mainHistogram.map(row => (row._1,row._2)) ++ intNames ++ extNames ++ ilNames ++ imNames
    val distinctScholiaNames = allScholiaNames.distinct  
  
     mainHistogram.map(row => (row._1,row._2)) ++ mainHistogram.map(row => (row._1,row._2)) ++ intHistogram.map(row => (row._1,row._2)) ++ extHistogram.map(row => (row._1,row._2)) ++ ilHistogram.map(row => (row._1,row._2)) ++ imHistogram.map(row => (row._1,row._2))

    println(mainHistogram)
    println(intHistogram)
    println(ilHistogram)
    println(extHistogram)
    println(imHistogram)
    
    for(u <- urnNames) {
    finalPrint(u,mainHistogram,intHistogram,ilHistogram,extHistogram,imHistogram)
    }
}

def matching(differeData: (String, String), row: Vector[(String, String, Int, String, String)]) = {

  row.filter(_._1 == differeData._1).filter(_._3 > 3)

}

def testing(scholiaType: Vector[Array[String]], urnNames: Vector[(String, String)], titleLabel: String) = {

  val justScholia = scholiaType.map(_(1)).filterNot(_.contains("lemma"))
  val loadXML = justScholia.map(XML.loadString(_))
  val xmlComment = loadXML.filter(_.child.size > 0).map(_.child(0))

  val persNamePerSchol = xmlComment.map(_ \ "persName").filterNot(_.isEmpty)
  val allPersNames = persNamePerSchol.map(n => n.map(e => e \ "@n")).flatten.filterNot(_.isEmpty)
  val totalNamesMentioned = allPersNames.size.toDouble
  val persStrings = allPersNames.map(_.toString).map(_.replaceAll("urn:cite2:hmt:pers.r1:pers75","urn:cite2:hmt:pers.r1:pers493"))
  val persNameFreqs = persStrings.groupBy(w => w).map { case (k,v) => (k,v.size)}
  val sorted = persNameFreqs.toSeq.sortBy(_._2).reverse

  val scholiaString = xmlComment.map(collectText(_,"")).mkString
  val scholiaWords = scholiaString.replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡]+","").split(" ").filterNot(_.isEmpty)
  val wordFrequency = scholiaWords.size.toDouble

  val orderedPersNames = sorted.map(s => s._1).toVector.map(_.replaceAll("cite2","cite").replaceAll("r1:",""))
  val label = orderedPersNames.map(name => (name,urnNames.filter(_._1 == name)))
  val orderedNamePairs = label.filter(_._2.size == 1).map(line => (line._1,line._2(0)._2))
  val orderedFreqs = sorted.map(s => s._2).toVector
  val orderedFreqsDoubles = sorted.map(s => s._2.toDouble).toVector
  val orderedTotalWordFreqs = orderedFreqsDoubles.map(normalize(_,wordFrequency))
  val orderedNameTotalFreqs = orderedFreqsDoubles.map(normalize(_,totalNamesMentioned))

  val totalWordPercentages = orderedTotalWordFreqs.map(freq => (freq.toString + "%"))
  val totalNamePercentages = orderedNameTotalFreqs.map(freq => (freq.toString + "%"))

  val ratio1 = math.BigDecimal((sorted.size / totalNamesMentioned) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  val ratio2 = math.BigDecimal((totalNamesMentioned / wordFrequency) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

  val histogram1 = orderedNamePairs zip orderedFreqs
  val histogram2 = histogram1 zip totalWordPercentages
  val histogram3 = histogram2 zip totalNamePercentages
  val histogram = histogram3.map(row => (row._1._1._1._1,row._1._1._1._2,row._1._1._2,row._2,row._1._2))
  println("-\t-\tMost Frequent Occuring Words for" + titleLabel + "Scholia\t-\t-")
  println("urn\tname\tOccurrences of Name in Scholia Type\tPercentage of Name against Total Names Mentioned\tPercentage of Name against Total Nummber of Words in Scholia Type")
  for (h <- histogram) {
  println(h._1 + "\t" + h._2 + "\t" + h._3 + "\t" + h._4 + "\t" + h._5)
  }
  println(sorted.size + "," + totalNamesMentioned.toInt + "," + ratio1 + "%," + wordFrequency.toInt + "," + ratio2 + "%")

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
  val normalized = (frequency / wordFrequency) * 100
  math.BigDecimal(normalized).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
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
