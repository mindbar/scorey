# Algo

## Classifier

- Define metrics `List<Metric>`:
  - `battery`
  - `display`
  - `price`
- Build relations between metric and keywords `Map<Metric, List<Keyword>>`
  - `battery` -> `battery capacity`, `charge time`
  - `display` -> `screen`, `monitor`
- Assign polarity words to each metric per category (train set):
   - `battery:positive` -> `high-capacity`, `long time`
   - `display:negative` -> `not clear`, `noisy`
- Rule-based approach to handle `Map<Metric, Classifier>`

## Evaluation

- Input query `iphone5` 
- Find all articles `List<Article> articles` for `iphone5`
- Extract fragments (piece of text related to metric) from Article per metric: `Map<Metric, List<Fragment>>`
- Find polarity of each fragment using metric classifier: `positive`, `negative`
- Average score on articles per metric and return list of scores: `{:battery 7.2, :display 9.1}`

## Fragment Extraction

- Input: Metric `battery`
- Split `Article` into the tokens `List<Token>`
- Loop tokens and if `Token` is one of the keywords for metric take this and next sentence as a `Fragment`
- Proceed loop to get `List<Fragment>`

## Data

- Reviews
  - [Cnet Reviews](http://www.cnet.com) 
  - [The Verge](http://www.theverge.com/reviews)
  - [Techcrunch](http://techcrunch.com/reviews/)
  - [Engadget](http://www.engadget.com/)
- Market comments



## Improvements

- Extract fragments more clever, parse sentence into the tree, take specific phrase
- PosTagging
- Weights for the words (*screen: clear, crystal clear, super crystal clear*)
- Exception rules
- Stemming, lemmatization
- Handle comparisons! (*Nexus5 screen is better than iPhone5*)
- Handle negatives! (*had no issues with the screen*)

## Points

- Aggregated score is more accurate than based on some limited set
- Articles drill give ability to find *very* bad and *very* good articles.
- Maybe 1 negative article among 100 positives describe some really negative point *for you*

