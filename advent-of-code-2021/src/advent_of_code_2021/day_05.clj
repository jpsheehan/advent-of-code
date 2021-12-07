(ns advent-of-code-2021.day-05
  (:require [clojure.string :as string]))

(defn unit
  "Returns -1 if the value is negative, 1 if the value is positive or 0."
  [value]
  (cond
    (< value 0) -1
    (> value 0) 1
    (= value 0) 0))

(defn get-segment-points
  "Returns a list of all the points in a line segment."
  [[[x0 y0] [x1 y1]]]
  (cond
    ; vertical line
    (= x0 x1)
    (->> (range (min y0 y1) (inc (max y0 y1)))
         (map #(list x0 %)))

    ; horizontal line
    (= y0 y1)
    (->> (range (min x0 x1) (inc (max x0 x1)))
         (map #(list % y0)))

    ; assume that this must be a diagonal line
    ; this would actually work in the general case as well
    true
    (let [dx (- x1 x0)
          dy (- y1 y0)]
      (for [i (range (inc (Math/abs dx)))] [(+ x0 (* i (unit dx))) (+ y0 (* i (unit dy)))]))))

(defn parse-point
  "Returns a xy coordinate pair as a list of integers."
  [point-string]
  (->> (string/split point-string #",")
       (map #(Integer/parseInt %))))

(defn parse-input-line
  "Returns a list two points that define a segment in the format '([x0 y0] [x1 y1])"
  [line]
  (->> (string/split line #" -> ")
       (map parse-point)))

(defn parse-input-string
  "Returns a list of segment points in the format '('([x0 y0] [x1 y1]) ... )"
  [input-string]
  (->> input-string
       (string/split-lines)
       (map parse-input-line)))

(defn valid-segment?
  "Returns true if the segment is horizontal or vertical."
  [[[x0 y0] [x1 y1]]]
  (or (= x0 x1) (= y0 y1)))

(defn get-part-one
  "Returns the answer to part one."
  [filename]
  (->> filename
       (slurp)
       (parse-input-string)
       (filter valid-segment?)
       (map get-segment-points)

       ; pull the points out of each segment into a flat list of points
       (flatten)
       (partition 2)

       ; find all points that occur twice or more
       (frequencies)
       (filter #(>= (val %) 2))

       ; return the count
       (count)))

(defn get-part-two
  "Returns the answer to part two."
  [filename]
  (->> filename
       (slurp)
       (parse-input-string)
       (map get-segment-points)

       ; pull the points out of each segment into a flat list of points
       (flatten)
       (partition 2)

       ; find all points that occur twice or more
       (frequencies)
       (filter #(>= (val %) 2))

       ; return the count
       (count)))