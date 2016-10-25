##Theory and Methodology

- How the data is originally structured (aka XML)
- Trying to take a text in xml format and analyze it (text only)
- In order to do this, must extract text
- Can't work with raw text (inflected language, BYZORTHO)
- For English, use stemmer; for Greek, use parser
- methods



--------------------




Any textual analysis involving digital technologies must begin with ensuring that the text of interest is formatted in such a way that it lends itself to analysis. In other words, the text must be machine-readable. While a concatenation of every character in Dickens's *A Tale of Two Cities* can be read by a human, albeit with some difficulty, the string of characters, "`Itwasthebestoftimes,itwastheworstoftimes`" is read by a computer as just that: a seemingly random collection of characters with few ways to derive meaning from it. Thus an outside source, perhaps a text culled from a webpage, often requires a fair amount of prep work, or text wrangling, in order for it to be fit for analysis.

Luckily, in the case of Iliadic scholia, the text of scholia has already been written in a format that easily lends itself to digital analysis. This is because the Homer Multitext project has been working for the last nine years to create digital editions 
