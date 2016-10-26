##Theory and Methodology

- How the data is originally structured (aka XML)
- Trying to take a text in xml format and analyze it (text only)
- In order to do this, must extract text
- Can't work with raw text (inflected language, BYZORTHO)
- For English, use stemmer; for Greek, use parser
- methods



--------------------





---------------------
For any research such as this, where textual analysis is heavily reliant upon digital technologies, the necessary first step is to make the text of interest machine-readable. For example, a concatenation of every non-white space character in the first line of Poe's "The Raven" can be read by a human, albeit with some difficulty. On the other hand, this string of characters, "`Onceuponamidnightdreary,whileIpondered,weakandweary,`", is read by a computer as just that: a seemingly random collection of characters. Relying on digital techonologies to identify the meaningful word breaks in this example would certainly prove difficult, rendering the above string essentially useless for any textual analysis. While it seems unlikely that one might have such a concatenated text, when texts are being culled from various webpages and outside sources for analysis, the transformation of the text from its original source can often result in an unexpectedly poor and useless format. Thus importing an already-fashioned text requires a fair amount of careful prep work, or text wrangling, in order for it to suit one's analysis.

Luckily, in the case of this work on Iliadic scholia, the text of the scholia has already been written in a format that lends itself easily to digital analysis. This is because, as mentioned previously in chapter 1, the Homer Multitext project has been working for the last nine years to create digital editions of entire Iliad manuscripts. The scholia to the Venetus A manuscript have been transcribed in an XML format with separate editions for each book and type of scholia. By writing in XML, or Extensible Markup Language, the editors of the Homer Multitext project are able not only to record the visible letters on the manuscript page, but also to apply "markup" in order to supply more information about the text. Specifically, the Homer Multitext project follows the guidelines of the Text Encoding Initiative (TEI), a community of digital humanists who have established standards for editing texts. These guidelines inform the members of the Homer Multitext project on how to create digital editions which include additional information that is easily understood by other scholars. This extra information can convey how the scholia are ordered on the page, to which line of the poem a scholion refers, and whether a string of text in the scholion is a quotation from elsewhere in the *Iliad*. These and other more specific TEI elements will be explained as they become relevant to the research of the scholia. However, this section will focus on the structural aspects of the TEI guidelines as they relate to text wrangling.

The Homer Multitext project uses the same basic structural format for editing every scholion. That is, within one TEI element `<div>` there are three parallel subdivisions which each carry information about the scholion. The first subdivision contains just the lemma of the scholion, the second contains a uniform citation for the line of the *Iliad* on which the scholion is commenting, and the final subdivision contains the actual textual content of the scholion itself. Such a logical structure is essential for the creation of a digital scholarly edition. However, this additional structural information hinders textual analysis. While something like `<div type="text"><l>Wrath, sing, oh goddess</l></div>` is a valid and logical XML transcription of the firt few words of the *Iliad*, clearly text like "<div>" and "<l>" should not be included in an analysis. If one were working with a small amount of scholia, it may make sense just to manually extract all the text, excluding the lemmata and citations. The 8,000 scholia of this dataset, however, renders manual extraction impractical. In order to avoid this labor-intensive process, we wrote a program using scala, a computer programming language, which was able to perform this extraction automatically. 
