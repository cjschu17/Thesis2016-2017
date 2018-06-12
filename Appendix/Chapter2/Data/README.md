The data associated with Chapter 2 involve the creation of the morphologically normalized (m-normalized) version of the text. The creation of the paleographically normalized (p-normalized) orthographically normalized (o-normalized) did not result in the creation of any new data. Furthermore, the complete versions of the p-normalized, o-normalized, and m-normalized versions can be found [here](https://github.com/cjschu17/Thesis2016-2017/tree/master/Appendix/VersionsOfScholia).

There are three data tables associated with Chapter 2:

[MorpheusReplies.tsv](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter2/Data/morpheusReplies.tsv) is the final product of the script [1Parsing.sc](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter2/Scripts/creatingMNormalizedText/1Parsing.sc). The purpose of that script is to send each Greek word within a specified text through the Morpheus morphological parser, so the resulting data table will be comprised of two-columns. The first column the first column contains a word that was analyzed through the Morpheus parser and the second column contains the complete morphological analysis by the Morpheus parser.

[IndexOfLemmata.tsv](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter2/Data/indexOfLemmata.tsv) is the final product of the script [2creatingIndexOfParses.sc](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter2/Scripts/creatingMNormalizedText/2creatingIndexOfParses.sc). The purpose of that script is to simplify the dense morphological analysis that the Morpheus parser creates when it analyzes each word to include only parts-of-speech and any possible lemmata. The resulting data table is comprised of three columns file with the first column containing the word which was analyzed, the second column containing the parts of speech that this word could identified as, and the third column containing all the possibile lemmata for each given word. 

[multipleParses.tsv](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter2/Data/multipleParses.tsv) is identical to [IndexOfLemmata.tsv](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter2/Data/indexOfLemmata.tsv), except that multipleParses.tsv has been filtered to only include rows in which a single word is associated with more than one lemma.



