# On the Format of the Scholia to the Iliad in the Venetus A Manuscript

 Charlie Schufreider

Advisor: Prof. Neel Smith, Dept. Classics

---
## Brief History of the *Iliad* and Its Scholarship

- *Iliad* first orally composed, and multiple versions abound (2nd millennium B.C.E.)
- **Editors at Alexandrian Library produce a standardize text (3rd and 2nd c. B.C.E.)**
- **Venetus A manuscript created (10th c. C.E.)**
- Venetus A rediscovered by Villoison (late 18th c. C.E.)
- Homer Multitext project (21st c. C.E.)

---

### Venetus A (Marcianus Graecus Z. 454)
#### Folio 12r
![Folio 12r](http://i68.tinypic.com/5cim9d.jpg)

---
## Aristarchus' Critical Signs (65r)
![Folio 65r](https://github.com/cjschu17/Thesis2016-2017/blob/master/images/65rCritSigns.jpg)

---
### Book 2 Subscription (41v)
![Subscription](https://github.com/cjschu17/Thesis2016-2017/blob/master/images/Book2Subscription.jpg)

παράκειται τὰ Ἀριστονίκου σημεῖα καὶ τὰ  Διδύμου περι τῆς Ἀρισταρχείου διορθώσεως· τινὰ δὲ καὶ ἐκ τῆς Ἰλιακῆς προσωδίας Ἡρωδιανου καὶ ἐκ  τοῦ Νικάνορος περὶ στιγμης⁑
 
Lying beside [the text of the *Iliad*] are the following works. *The Signs* by Aristonicus, *On the Aristarchean Recension* by Didymus. And there is something from *Iliadic Prosody* by Herodian and *On Punctuation* by Nicanor.

---

**By understanding the layout of and sources for the Venetus A two things can be accomplished:**
1. bridge the thousand year gap in scholarship between Alexandrians and Venetus A creation
2. by refining what information hails from the Alexandrians, shed light on pre-Alexandrian scholarship

---
## Five-zoned Layout of the Scholia

![Folio12Again](https://github.com/cjschu17/Thesis2016-2017/blob/master/images/DSE.jpg)

- Yellow: Main
- Blue: Intermarginal
- Green: Interior
- Gray: Interlinear
- Red: Exterior
---
## Methodology

- Distant reading over close reading
  - "macro" over "micro" analysis
 
 - Scholia is a huge corpus (148,978 words)
 
 ---
 ## LDA Topic Modeling (Latent Dirichlect Allocation)
 
 - uncovering the repeating patterns of co-occurring words within a text
 - requires a heavily prepared corpus
   - elimination of common words 
     - e.g. "the", "he", "am", etc.
   - reduction of multiple forms of a word into a single form 
     - e.g. reducing "singing", "sang", "sung" to "sing"
     
---
## 15-Topic Topic Model, from ToPan

![Topic9](https://github.com/cjschu17/Thesis2016-2017/blob/master/images/TranslatedToPan9.jpg)

---
## Topic 9 Broken Down by Zones of Schola

Scholia Zone | Frequency of Scholia Type within this Topic
--- | ---
Main scholia | 45.0 (32.37%)
**Intermarginal scholia** | **65.0 (46.76%)**
Interior scholia | 28.0 (20.14%)
Interlinear scholia | 0.0 (0.0%)
Exterior scholia | 1.0 (0.72%)

## Normal Frequency of Scholia Zones 
Scholia Zone | Scholia Zone Frequency
--- | ---
Main scholia | 3597 (45.3%)
Intermarginal scholia | 1219 (15.4%)
Interior scholia | 819 (10.3%)
Interlinear scholia | 2067 (26.0%)
Exterior scholia | 233 (2.9%)

---
## Types of Discourse in the Scholia

- Direct Discourse
- Indirect Discourse
  - Quoted Text
    - A quote from elsewhere in the *Iliad* or any other source
  - Quoted Language
    - A quote from the *Iliad* line being commented on, or using a word as a word
      - "Running" is the gerund form of the verb "run."
---
### Types of Discourse by Scholia Zone

Scholia Type|Number of Words|**Number of Direct Voice Words**|Number of Quoted Text Words|Number of Quoted Language Words|Number of Non-Direct Voice Words
---|---|---|---|---|---
**Main**|6830|108689 (89.66%)|**6384 (5.27%)**|6153 (5.08%)|12537 (10.34%)
Intermarginal|8170|6929 (84.81%)|88 (1.08%)|1153 (14.11%)|1241 (15.19%)
Interior|6002|5361 (89.32%)|53 (0.88%)|588 (9.8%)|641 (10.68%)
Interlinear|4800|4755 (99.06%)|4 (0.08%)|41 (0.85%)|45 (0.94%)
Exterior|466|414 (88.84%)|0 (0.0%)|52 (11.16%)|52 (11.16%)

---

Scholia Type|Number of Words|Number of Direct Voice Words|Number of Quoted Text Words|Number of Quoted Language Words|**Number of Non-Direct Voice Words**
---|---|---|---|---|---
Main|6830|108689 (89.66%)|6384 (5.27%)|6153 (5.08%)|12537 (10.34%)
**Intermarginal**|8170|6929 (84.81%)|88 (1.08%)|1153 (14.11%)|**1241 (15.19%)**
**Interior**|6002|5361 (89.32%)|53 (0.88%)|588 (9.8%)|**641 (10.68%)**
Interlinear|4800|4755 (99.06%)|4 (0.08%)|41 (0.85%)|45 (0.94%)
Exterior|466|414 (88.84%)|0 (0.0%)|52 (11.16%)|52 (11.16%)

---

## Intermarginal and Interior or...

![12v13r](https://github.com/cjschu17/Thesis2016-2017/blob/master/images/12v13r.jpg)

---

## Length of Scholia By Words

Type of Scholia|Number of Scholia|Median Length (Wds)|Mean Length in Wds|Standard Deviation
--- | --- | ---| --- | ---
Start of Line Scholia|1255|7|7.57|4.87
End of Line Scholia|783|6|6.92|4.56
--- | --- | --- | --- |---
Intermarginal Scholia|1220|6|7.1|3.97
Interior Scholia|819|7|7.66|5.74

Types of Scholia|T-value|Statistically Significant (for p < 0.05 & d.f. = infinity  t > 1.645)
---|---|---
Start and End|3.03|Statistically Significant
Intermarginal and Interior|2.44|Statistically Significant

----
## Discourse Measurements

Scholia Type|Number of Scholia With Non-Direct Speech|Number of Scholia with no Non-Direct Speech|Number Of Scholia
--- | --- | --- | ---
Start Scholia|547|574|1121
End Scholia|286|426|712
Total Start and End|833|1000|1833
---|---|---|---
Im Scholia|533|560|1093
Int Scholia|300|440|740
Total Start and End|833|1000|1833

Scholia Type|ChiSquare	Significance|(p < 0.05, if χ2 > 3.841 for df = 1)
---|---|---
Start and End|13.07|Statisically Significant
Intermarginal and Interior|12.04|Statisically Significant
---
## Text Content (Topic 9 - Aristarchus Topic)

Scholia Zone | Frequency of Scholia Type within this Topic
--- | ---
Main scholia | 45.0 (32.37%)
**Intermarginal scholia** | **65.0 (46.76%)**
Interior scholia | 28.0 (20.14%)
**Start scholia**|**73.0 (52.52%)**
End scholia|20.0 (14.39%)
Interlinear scholia | 0.0 (0.0%)
Exterior scholia | 1.0 (0.72%)
