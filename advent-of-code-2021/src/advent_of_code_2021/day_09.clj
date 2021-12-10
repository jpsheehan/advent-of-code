(ns advent-of-code-2021.day-09
  (:require [clojure.string :as string]
            [clojure.set]))

(defn parse-input-line
  "Gets the height map of a single line of input."
  [line]
  (->> line
       (string/trim)
       (seq)
       (map str)
       (map #(Integer/parseInt %))))

(defn parse-input
  "Gets the height map from an input."
  [input]
  (->> input
       (string/split-lines)
       (map parse-input-line)
       (map #(apply vector %))
       (apply vector)))

(defn safely-get-height
  "Returns the height of a value or 10 if no value exists."
  [height-map [x y]]
  (if (or (< x 0) (< y 0) (>= x (count (first height-map))) (>= y (count height-map)))
    10
    (nth (nth height-map y) x)))

(defn is-low-point?
  "Returns true if the point is lower than its adjacent points."
  [height-map [x y]]
  (let [height (safely-get-height height-map [x y])]
    (and
      (< height (safely-get-height height-map [(dec x) y]))
      (< height (safely-get-height height-map [(inc x) y]))
      (< height (safely-get-height height-map [x (dec y)]))
      (< height (safely-get-height height-map [x (inc y)])))))

(defn get-low-points
  "Gets a list of low points in the height map."
  ([height-map]
   (let [width (count (first height-map))
         height (count height-map)]
     (for [x (range width)
           y (range height)
           :let [point [x y]]
           :when (is-low-point? height-map point)]
       (safely-get-height height-map point)))))

(defn get-risk-level
  "Gets the risk level based on the height."
  [height]
  (inc height))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (get-low-points)
       (map get-risk-level)
       (reduce +)))

(defn get-basin-points
  "Returns a list of points that exist in basins."
  [height-map]
  (let [width (count (first height-map))
        height (count height-map)]
    (for [x (range width)
          y (range height)
          :let [point [x y]]
          :when (not= 9 (safely-get-height height-map point))]
      point)))

(defn abs
  "Returns the absolute value of x."
  [x]
  (if (neg? x) (* x -1) x))

(defn diff
  "Returns the unsigned difference between two values."
  [x y]
  (abs (- x y)))

(defn is-adjacent?
  "Returns true if two points are adjacent."
  [[x0 y0] [x1 y1]]
  (or (and (= x0 x1) (= 1 (diff y0 y1))) (and (= y0 y1) (= 1 (diff x0 x1)))))

(defn find-connected-points
  "Finds points from the remaining set that are connected adjacently to any points in the connected set.
  If no such points can be found, returns the empty set."
  [connected remaining]
  (->> remaining
       (filter #(some (fn [connected-point] (is-adjacent? % connected-point)) connected))
       (set)))

(defn flood-fill
  "Returns a set of points that are connected adjacently."
  [points]
  (loop [connected #{ (first points) }
         others (set (rest points))]
    (let [points (find-connected-points connected others)]
      (if (empty? points)
        connected
        (recur (clojure.set/union connected points) (clojure.set/difference others points))))))

(defn get-basins
  "Returns a list of basins. Each basin is a list of points contained in the basin."
  [height-map]
  (loop [unvisited-points (set (get-basin-points height-map))
         visited-points #{}
         basins '()]
    (if (empty? unvisited-points)
      basins
      (let [new-basin (flood-fill unvisited-points)
            new-visited-points (clojure.set/join visited-points new-basin)
            new-unvisited-points (clojure.set/difference unvisited-points new-basin)
            new-basins (conj basins new-basin)]
        (recur new-unvisited-points new-visited-points new-basins)))))

(defn get-part-two
  [filename]
  (->> filename
       (slurp)
       (parse-input)

       ; get a list of points in each basin
       (get-basins)
       (map count)

       ; get the product of the three largest basins
       (sort)
       (take-last 3)
       (reduce *)))