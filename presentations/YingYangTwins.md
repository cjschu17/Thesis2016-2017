
---


# Implementation of the HMT digital edition


Melody Wauke & Charlie Schufreider

---

## Venetus A reader’s guide

![Folio 12r again](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/DSE2.JPG)

---

## Interlinear Scholia

- Dindorf (1875)

> *Praeter haec… scholia textui superscripta sunt glossemata quae interlinearia appellari solent*

> Beyond these scholia are the glosses written above the text which are accustomed to be called ‘interlinears’

### Paraphrase Tradition
Nik Churik (2015) [Paraphrasing Homer: The Relation between the Psellos Paraphrase and the Commentary Tradition](https://raw.githubusercontent.com/ncchur/thesis-writing/master/finalPaper)


---

## Exterior Scholia

 - T. W. Allen (1899): “On the Composition of Some Greek Manuscripts” 
   - one scribe checking the Venetus A text against the MS's sources and making corrections when necessary
   
---

## Main, Intermarginal, Interior according to Dindorf

- Main vs. Im & Int:
  - Of the same origin and kind, latter are excerpts from former or very similar scholia elsewhere
- Im vs. Int:
  - No difference!
 
 > Medium inter scholia marginalia et textum locum tenent scholia brevissima quae intermarginalia dicimus
 
 > In between the marginal (main) scholia and the text very short scholia, which we call 'intermarginal,' hold a middle place.
 
 ---
 
 ## Length in Words of Each Scholia Zone
 
 Type of Scholia|Scholia Count|Median Words|Mean Words|Standard Deviation
---|---|---|---|---
Main Scholia|3601|24|33.78|31.06
Intermarginal Scholia|1220|6|7.1|3.97
Interior Scholia|819|7|7.66|5.74
Interlinear Scholia|2067|2|2.31|1.88
Exterior Scholia|233|1|4.19|28.56

---

## LDA Topic Modeling

Latent Dirichlect Allocation:

- repeating patterns of co-occurring words
- requires a heavily prepared corpus:
    - eliminate common words, e.g.

---

## Grammar/Punctuation Topic

![Topic 5: Punctuation/Grammar](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/Data/TopicModelData/ToPan-4-6-17/Run1/Images/Topic05.png)

---

## Genealogy Topic

![Topc 2: Gods/Genealogy](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/Data/TopicModelData/ToPan-4-6-17/Run1/Images/Topic02.png)

---


## 15-Topic Model, from ToPan

![Topic 9: most significant words](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/TranslatedToPan9.jpg)


---

## Theta scores

 - measure strength of association between scholion and topic

![Theta Table](https://raw.githubusercontent.com/cjschu17/drdwPortfolio/master/images/DRDW-presentation/Screen%20Shot%202016-12-08%20at%201.03.37%20AM.png)

---

## Distribution of Scholia

| Zone                            | Total distrib. in Ven. A | Distrib. within Topic 9 |
|:--------------------------------|:---------------------|:------------------------|
| Main scholia            | 3597 (45.3%)         | 45.0 (32.37%)           |
| **Intermarginal scholia** | **1219 (15.4%)**     | **65.0 (46.76%)**       |
| Interior scholia         | 819 (10.3%)          | 28.0 (20.14%)           |

---

## Distinct Patterns of Language => Distinct Sources


---

## Sources for the Venetus A scholia

- Limited information on source material
- Manuscript cites four specific sources from 1st and 2nd centuries C.E.

---

## Recovering Aristarchan material

- Identify cluster of features which suggest source (Aristarchan, post-Aristarchan)
- Create a model for identifying Aristarchan scholia

---

## The data set

- 18 books of scholia (~8000) from Homer Multitext's digital editions
- index of all critical signs

---

## Suggestions of Aristarchan *language*



- Critical Signs come from Aristarchus' editions
- Explanatory scholia (ὅτι...) transmitted with signs are also Aristarchan
- Only Zenodotus "writes" (ζηνόδοτος γράφει...) vs. παρὰ Ζηνοδότῳ
![Critical Signs](https://raw.githubusercontent.com/mwauke/seniorThesis/master/Screen%20Shot%202017-04-21%20at%201.06.04%20AM.png)

---

## Significant topics for analysis

---


### Topic 6: Aristarchan language

![Topic 6](https://raw.githubusercontent.com/mwauke/seniorThesis/master/Topic06.png)

---

### Topic 9: Language used when discussing Aristarchus

![Topic 9](https://raw.githubusercontent.com/mwauke/seniorThesis/master/Topic09.png)

---


## Features for analysis

### Aristarchan features
- Critical sign
- γράφει (active)
- Initial ὅτι
- Higher topic 6 score

### Post-Aristarchan features

- παρὰ Ζηνοδότῳ
- Aristarchus' name
- Post-Aristarchan name
- Higher topic 9 score


---


## Manually classify a training set of 100 scholia

- Aristarchan
- Aristarchan paraphrase
- post-Aristarchan
- indeterminate

---

## Decision tree model trained from manually labeled set

- 5 levels deep
- 25 nodes

----

    If (feature 2 <= 0.0)
      If (feature 4 <= 0.0)
        If (feature 5 <= 0.0)

         If (feature 4 <= 0.0)
        If (feature 5 <= 0.0)


         If (feature 7 <= 0.00606060606060606)
          If (feature 7 <= 0.00103626943005181)
           Predict: 3.0
          Else (feature 7 > 0.00103626943005181)
           Predict: 4.0
         Else (feature 7 > 0.00606060606060606)
          If (feature 6 <= 0.00122699386503067)
           Predict: 2.0
          Else (feature 6 > 0.00122699386503067)

      ...

----


## Experimenting with the feature set

- including zone of scholion *lowered* success rate

---


## Applying the model

- evaluate model: success against training set (up to 90%)
- can then apply to remaining 7900+ scholia


---


## Results


- Main scholia contain the majority of Aristarchan and post-Aristarchan language
- Intermarginal and Interior scholia contain more Aristarchan paraphrases
- Zones are *not* the best feature for indicating source

---

## Future directions

- expand training set
- apply to expanded edition of Venetus A
- try alternate machine learning algorithms
- content analysis of automatically identified classes?

---


## Conclusions

- Analyze scholia as a collection of various features
- Working Aristarchan identifier:
    - Possible to recover material directly from Aristarchus' editions (2nd century BCE)

