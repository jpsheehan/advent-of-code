(ns advent-of-code-2021.day-08,
  (:require [clojure.string :as string]
            [clojure.set]))

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

(defn solve-line
  "Solves the line, returning a list containing the solved segments and the results."
  [[segments results]]
  (let [segments (map set segments)
        results (map set results)

        ; these first few are trivial
        one (first (filter #(= 2 (count %)) segments))
        seven (first (filter #(= 3 (count %)) segments))
        four (first (filter #(= 4 (count %)) segments))
        eight (first (filter #(= 7 (count %)) segments))

        ; zero, six, and nine have 6 segments each
        ; six also shares one of its segments with 1
        six (first (filter #(and (= 6 (count %)) (= 1 (count (clojure.set/intersection % one)))) segments))
        ; nine completely overlaps four
        nine (first (filter #(and (= 6 (count %)) (clojure.set/subset? four %)) segments))
        ; zero is the one remaining that is not six or nine
        zero (first (filter #(and (= 6 (count %)) (not= six %) (not= nine %)) segments))

        ; two, three, and five have 5 segments each
        ; five is the intersection of nine and six
        five (clojure.set/intersection nine six)
        ; one is a subset of three
        three (first (filter #(and (= 5 (count %)) (clojure.set/subset? one %)) segments))
        ; two is not three or five
        two (first (filter #(and (= 5 (count %)) (not= three %) (not= five %)) segments))

        digits [zero one two three four five six seven eight nine]]
    (->> results
         (map #(.indexOf digits %))
         (map str)
         (clojure.string/join)
         (Integer/parseInt))))

(defn get-part-two
  "Solves each line and sums the results column."
  [filename]
  (->> filename
       (slurp)
       (parse-input)

       ; solve each line and return the sum of the results
       (map solve-line)
       (reduce +)))