
---


# The Venetus A MS of the *Iliad*: Layout and Sources



Charlie Schufreider


Advisor: Prof. Neel Smith, Classics Dept.

---

## Venetus A (Marcianus Gr. Z. 454)

Folio 12r

![Folio 12r](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/12r.jpg)

---

## Transmission of the *Iliad*

- orally composed (2nd millennium B.C.E.)
- **Library of Alexandria : editions (3rd and 2nd c. B.C.E.)**
- **Venetus A manuscript (10th c. C.E.)**
- Thesis Work (21st c. C.E.)



---



##  Layout of Venetus A

![Folio 12r again](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/DSE2.JPG)

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



##  Why five distinct zones?

![Folio12Again](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/DSE2.JPG)

---

## Methodology


- Distant reading over close reading
  - "macro" over "micro" analysis
  - account for features of discourse
- Incorporate layout zone in analysis
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

## Distribution of Scholia

| Zone                            | Total distrib. in Ven. A | Distrib. within Topic 9 |
|:--------------------------------|:---------------------|:------------------------|
| Main scholia (yellow)           | 3597 (45.3%)         | 45.0 (32.37%)           |
| **Intermarginal scholia (red)** | **1219 (15.4%)**     | **65.0 (46.76%)**       |
| Interior scholia (blue)         | 819 (10.3%)          | 28.0 (20.14%)           |

---

## Intermarginal and Interior or Start and End of Line?

Folios 12v and 13r

![Im and Int](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/12v13rImInt.jpg)

---

Folios 12v and 13r

![Start and End](https://raw.githubusercontent.com/cjschu17/Thesis2016-2017/master/images/12v13rSE.jpg)

---

## Number of Scholia

| Type of Scholia | Number of Scholia |
|:----------------|:------------------|
| Start of Line   | 1255              |
| End of Line     | 783               |
| ---             | ---               |
| Intermarginal   | 1220              |
| Interior        | 819               |


---

## Types of Discourse

- Direct discourse
- Indirect discourse

---

## Discourse Measurements

| Scholia zone | Words in direct discourse | Words in indirect discourse | Total words in zone |
|:-------------|:--------------------------|:----------------------------|:--------------------|
| Start Im     | 3849 (84.02%)             | 732 (**15.98%**)            | 4581                |
| End Im       | 2638 (85.23%)             | 457 (**14.77%**)            | 3095                |
| Start Int    | 3385 (88.27%)             | 450 (**11.73%**)            | 3835                |
| End Int      | 1668 (90.36%)             | 178 (**9.64%**)             | 1846                |



---

| Zones Being Compared | Z-Score | Significance (p < 0.01)       |
|:---------------------|:--------|:------------------------------|
| start Im & start Int | 5.58    | Statisically Significant      |
| start Im & end Im    | 1.44    | Not Statistically Significant |
| end Int & start Int  | 2.35    | Not Statistically Significant |
| end Int  end Im      | 5.21    | Statisically Significant      |



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

- Professor Neel Smith, advisor
- Melody Wauke, collaborator
- Professor Mary Ebbott, reader
- Professor Thomas Köntges, developer of ToPan
- Professor Eric Ruggieri, statistics consult
- Professor Susan Sweeny and CHP
- Classics Department



---
