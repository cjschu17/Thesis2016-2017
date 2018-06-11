The above script, [creatingByzorthoEdition.sc](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter2/Scripts/creatingONormalizedText/creatingByzorthoEdition.sc), was designed with the purpose of normalizing an edition of the Venetus A scholia according to modern orthographic standards, as opposed to those used by the Byzantine scribes who created the manuscript.

The script requires two inputs:

The first input is some 2-column version of a Greek text, wherein the first column contains that CTS URN identifiers of the text and the second column contains the text intended to be normalized. For this thesis, I used this script to normalize the p-normalized version of the text found [here](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/VersionsOfScholia/p-normalized.tsv).

The second input is the Homer Multitext's compilation of the various orthographic variants found in the Venetus A. This can be found [here](https://github.com/homermultitext/byzortho/blob/master/orthoequivs.csv). The compilation is a five-columned .tsv file, where the first file contains a CTS URN identifier for each specific orthographic variant, the second contains the orthographic variant as it appears in the manuscript, the third contains the modern equivalent of the variant, the fourth contains the status of the normalization's propsosal ("propsoed," "accepted," etc.), and fifth contains any extraneous comments.