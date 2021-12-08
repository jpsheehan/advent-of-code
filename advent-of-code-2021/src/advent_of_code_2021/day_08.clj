(ns advent-of-code-2021.day-08,
  (:require [clojure.string :as string]))

(defn parse-input-line
  "Returns the signal patterns and the output."
  [line]
  (->> (string/split line #"\|")
       (map (fn [x] (-> x
                        (string/trim)
                        (string/split #" "))))))

(defn parse-input
  "Returns a list of lines containing signal patterns and outputs."
  [input]
  (->> input
       (string/split-lines)
       (map parse-input-line)))

(defn is-1-4-7-or-8?
  "Returns true if the segments make up a 1, 4, 7, or 8."
  [segment]
  (let [x (count segment)]
    (or
      (= x 2)                                               ; 1
      (= x 4)                                               ; 4
      (= x 3)                                               ; 7
      (= x 7)                                               ; 8
      )))

(defn get-part-one
  "Returns the number of instances of 1, 4, 7, or 8 in the results column of the input file."
  [filename]
  (->> filename
       (slurp)
       (parse-input)

       ; get a flat list of words in the results column
       (map second)
       (flatten)

       (filter is-1-4-7-or-8?)
       (count)))
