Presented here are all the data related to the production and analysis of a topic model of the Venetus A scholia. The topic model was run using the ToPan software developed by Thomas Köntges at the University of Leipzig.

The [StopWords](https://github.com/cjschu17/Thesis2016-2017/tree/master/Appendix/Chapter3/Data/TopicModelData/StopWords) folder contains data related to the creation of the stop word list, needed to make a version of the scholia optimized for topic modelling. The "topics" of a topic model are nothing more than repeating patterns of co-occurring words. Therefore, frequently-occurring words, like the definite article, occur so frequently that the topic modelling software perceives patterns of co-occurence, i.e. topics, that do not make sense. Thus, the removal of these frequently-occurring words, i.e. stop words, from the dataset is absolutely important.

Next, the [ToPanVisualizations](https://github.com/cjschu17/Thesis2016-2017/tree/master/Appendix/Chapter3/Data/TopicModelData/ToPanVisualizations) folder contains screenshots of the visualization the ToPan software's topic model of the Venetus A scholia. It also includes the initial analysis of each of the 15 topics, by means of a label applied to identify what each topics means or how each functions.

The [ThetaTables](https://github.com/cjschu17/Thesis2016-2017/tree/master/Appendix/Chapter3/Data/TopicModelData/ThetaTables) folder contains data related to the next stage of topic model analysis. The ToPan software, in running its topic model assesses, what percentage of each topic comprise a single scholion. This data is presented in the form of a theta-score which can only have a value from 0.0 to 1.0. If a particular scholion has a very high theta-score (like 1.0) is connection to a certain topic, this would indicate that this scholion is comprised of words entirely from that topic. Conversely, a scholion having a low-theta score (0.0) in relation to a particular topic would indicate that none of the words in that topic are derived from that topic. In this folder there is a comprehensive table of the theta-score of every single scholion with every single topic. This data was then pared down to only exhibit those scholia which have a significantly strong with each topic. Thus there is a table listing all the scholia which are significantly composed of words from a topic model's first topic, then another table listing all the scholia whoch are significant composed of words from a topic model's second topic, and so on.

The final stage in this analysis was to identify within those scholia which were significantly composed of words from each topic what zone of the Venetus A scholia they were more likely to come from. For example, the file [topic01.tsv](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter3/Data/TopicModelData/ThetaTables/SignificantThetaScoresByTopic/topic01.tsv) contains all the scholia which are significantly composed of words from the first topic of a particular model. However, what I wanted to know was whether there was any difference in how the various topics were distributed among the various zones of the scholia. So was this topic 1 mostly contained in the main scholia, the intermarginal scholia, interior, exterior, or interlinear. The file [newToPanResult.txt](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter3/Data/TopicModelData/newToPanResult.txt) contains such information about each of the topics that were created by the first run of topic model at 2,000 iterations. Similar to [newToPanResult.txt](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter3/Data/TopicModelData/newToPanResult.txt), the file [noIlExtToPanResult.txt](https://github.com/cjschu17/Thesis2016-2017/blob/master/Appendix/Chapter3/Data/TopicModelData/noIlExtToPanResult.txt) presents the exact same data relating how each topic is distributed among the various zones of the manuscript, but it excludes the exterior and interlinear scholia from any and all calculations.