
---


# The Venetus A MS of the *Iliad*: Layout and Sources



Charlie Schufreider


Advisor: Prof. Neel Smith, Classics Dept.

---

## Transmission of the *Iliad*

- orally composed (2nd millennium B.C.E.)
- **Library of Alexandria : editions (3rd and 2nd c. B.C.E.)**
- **Venetus A manuscript (10th c. C.E.)**
- MS rediscovered by Villoison (late 18th c. C.E.)
- Homer Multitext project (21st c. C.E.)

---

## Venetus A (Marcianus Gr. Z. 454)

Folio 12r

![Folio 12r](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/12r.jpg)

---

## Aristarchus' Critical Signs

Folio 65r

![Folio 65r](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/65rCritSigns.jpg)



---

## Cited Sources

Book 2 Subscription


![Folio 41v](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/Book2Subscription.jpg)



παράκειται τὰ Ἀριστονίκου σημεῖα καὶ τὰ  Διδύμου περι τῆς Ἀρισταρχείου διορθώσεως· τινὰ δὲ καὶ ἐκ τῆς Ἰλιακῆς προσωδίας Ἡρωδιανου καὶ ἐκ  τοῦ Νικάνορος περὶ στιγμης⁑



---

## Subscription

>Lying beside [the text of the *Iliad*] are the following works:
>
> - **The Signs** by Aristonicus
> -  **On the Aristarchean Recension** by Didymus
> -  And there is something from **Iliadic Prosody** by Herodian and
> - **On Punctuation** by Nicanor.

---



## Layout of Scholia in five zones

![Folio12Again](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/DSE.jpg)


---

## Why five zones?

Does layout relate to sources?


---

## Methodology


- Distant reading over close reading
  - "macro" over "micro" analysis

 - Scholia are a huge corpus (148,978 words in 18 books)

---

## LDA Topic Modeling

Latent Dirichlect Allocation:

- repeating patterns of co-occurring words
- requires a heavily prepared corpus:
    - eliminate common words
    - reduce words to a single form


---

## 15-Topic Model, from ToPan

![Topic 9: most significant words](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/TranslatedToPan9.jpg)

---

## Proportion of All Scholia by Zone

| Zone          |  Frequency in zone |
|:----------------------|:-----------------------|
| Main scholia          | 3597 (45.3%)           |
| **Intermarginal scholia** | **1219 (15.4%)**           |
| Interior scholia      | 819 (10.3%)            |
| Interlinear scholia   | 2067 (26.0%)           |
| Exterior scholia      | 233 (2.9%)             |

---


## Proportion of Topic 9 by Zone

| Zone              | Frequency of zones in topic |
|:--------------------------|:--------------------------------------------|
| Main scholia              | 45.0 (32.37%)                               |
| **Intermarginal scholia** | **65.0 (46.76%)**                           |
| Interior scholia          | 28.0 (20.14%)                               |
| Interlinear scholia       | 0.0 (0.0%)                                  |
| Exterior scholia          | 1.0 (0.72%)                                 |

---

## Types of Discourse

- Direct discourse
- Indirect discourse:
    - Quoted text
    - Quoted language

---

### Types of Discourse by Scholia Zone

Type Of Scholia|Words in Quoted Text|Words in Quoted Language|Total Words in Indirect Discourse
---|---|---|---
Main|6383.(52.67%)|5737 (47.33%)|12120
Intermarginal|88 (7.4%)|1101 (92.6%)|1189
Interior|53 (8.44%)|575 (91.56%)|628
Interlinear|4 (8.89%)|41 (91.11%)|45
Exterior|	0 (0.0%)|52 (100.0%)|52
Non-Main |145 (7.58%)|1769.0 (92.42%)|1914

---

Zones being bompared|Z-score|Significance (p < 0.05 when Z > 1.96))
---|---|---
Main vs. all other zones|36.75|Statisically Significant

---

## Intermarginal and Interior or Start and End of Line?

Folios 12v and 13r

![Im and Int](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/12v13rImInt.jpg)

---
Folios 12v and 13r

![Start and End](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/12v13rSE.jpg)
---

## Length of Scholia By Words

| Type of Scholia       | Number of Scholia | Median Length (Wds) | Mean Length in Wds | Standard Deviation |
|:----------------------|:------------------|:--------------------|:-------------------|:-------------------|
| Start of Line Scholia | 1255              | 7                   | 7.57               | 4.87               |
| End of Line Scholia   | 783               | 6                   | 6.92               | 4.56               |
| ---                   | ---               | ---                 | ---                | ---                |
| Intermarginal Scholia | 1220              | 6                   | 7.1                | 3.97               |
| Interior Scholia      | 819               | 7                   | 7.66               | 5.74               |

---

## T-test test for Difference in Mean Scholion Length

| Types of Scholia           | T-value | Statistically Significant (for p < 0.05 & d.f. = infinity  t > 1.645) |
|:---------------------------|:--------|:----------------------------------------------------------------------|
| Start and End              | 3.03    | Statistically Significant                                             |
| Intermarginal and Interior | 2.44    | Statistically Significant                                             |

----

## Discourse Measurements


---

## Text Content (Topic 9 - Aristarchus Topic)

| Scholia Zone              | Frequency of Scholia Type within this Topic |
|:--------------------------|:--------------------------------------------|
| Main scholia              | 45.0 (32.37%)                               |
| **Intermarginal scholia** | **65.0 (46.76%)**                           |
| Interior scholia          | 28.0 (20.14%)                               |
| **Start scholia**         | **73.0 (52.52%)**                           |
| End scholia               | 20.0 (14.39%)                               |
| Interlinear scholia       | 0.0 (0.0%)                                  |
| Exterior scholia          | 1.0 (0.72%)                                 |

---

## Initial Conclusions

- Distant reading to:
    - break away from assumptions or biases
    - recognize latent patterns

- Layout:
    - distinct distribution of content in each zone
    - tendency to annotate *beginnings* of lines
       - hints at overlapping content in interior/intermarginal zones?



---

## Thank you
