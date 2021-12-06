(ns advent-of-code-2021.day_01)
(require '[advent-of-code-2021.utils :all true :as utils])

(defn read-numbers
  "Reads numbers from a file. It expects there to be one number on each line."
  []
  (utils/transform-input (fn [line] (Integer/parseInt line))))

(defn get-sliding-window-increases
  "Gets the number of increases using a sliding window."
  [window-size
   all-numbers]
  (loop [previous-window (take window-size all-numbers)
         others (rest all-numbers)
         total 0]
    (if
      (empty? others)
      total
      (let [this-window (take window-size others)
            this-sum (utils/sum this-window)
            previous-sum (utils/sum previous-window)
            nextTotal (if (> this-sum previous-sum) (inc total) total)]
        (recur this-window (rest others) nextTotal)
        )))
  )

(defn get-part-one []
  (let [all-numbers (read-numbers)]
    (get-sliding-window-increases 1 all-numbers)))

(defn get-part-two []
  (let [all-numbers (read-numbers)]
    (get-sliding-window-increases 3 all-numbers)))
