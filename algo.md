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
- Train binary classifier per metric on polarity words `Map<Metric, Classifier>`

## Evaluation

- Input query `iphone5` 
- Find all articles `List<Article> articles` for `iphone5`
- Extract fragments (piece of text related to metric) from Article per metric: `Map<Metric, List<Fragment>>`
- Find polarity of each fragment using metric classifier: `positive`, `negative`
- Average score on articles per metric and return list of scores: `{:battery 7.2, :display 9.1}`

## Fragment Extraction

- Input: Metric `battery`
- Split `Article` to tokens `List<Token>`
- Loop tokens and if `Token` is one of the keywords for metric take its `20` surrounding tokens as fragment `Fragment`
- Proceed loop to get `List<Fragment>`
