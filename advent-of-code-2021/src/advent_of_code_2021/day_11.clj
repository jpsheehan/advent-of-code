(ns advent-of-code-2021.day-11
  (:require [clojure.string :as string]))

(defn pp [x] (println x) x)

(defn parse-input-line
  [line]
  (->> line
       (seq)
       (map str)
       (map #(Integer/parseInt %))))

(defn parse-input
  [input]
  (->> input
       (string/split-lines)
       (map string/trim)
       (map parse-input-line)))

(defn decorate-input-with-flash-info
  [rows]
  (map (fn [fishes] (map #(list % :no-flash) fishes)) rows))

(defn increase-energy-level
  [[energy-level flash-info]]
  [(inc energy-level) flash-info])

(defn has-flashed?
  [[_ flash-info]]
  (= flash-info :flash))

(defn needs-to-flash?
  [[energy-level flash-info]]
  (and (= flash-info :no-flash) (> energy-level 9)))

(defn reset-energy-levels
  [octopus]
  (if (has-flashed? octopus)
    [0 :no-flash]
    octopus))

(defn get-octopus
  [[x y] rows]
  (nth (nth rows y) x))

(defn find-flash-point
  [rows]
  (first (for [y (range (count rows))
               x (range (count (first rows)))
               :let [point [x y]]
               :when (needs-to-flash? (get-octopus point rows))]
           point)))

(defn apply-fn-to-point
  [target-point f rows]
  (let [height (count rows)
        width (count (first rows))]
    (partition width
               (for [y (range height)
                     x (range width)
                     :let [point [x y]
                           octopus (get-octopus point rows)]]
                 (if (= target-point point)
                   (f octopus)
                   octopus)))))

(defn safely-increment-point
  [point rows]
  (apply-fn-to-point point (fn [[energy-level flash-info]] [(inc energy-level) flash-info]) rows))

(defn mark-as-flashed
  [point rows]
  (apply-fn-to-point point (fn [[energy-level _]] [energy-level :flash]) rows))

(defn apply-flash-point
  [[x y] rows]
  (->> rows
       (safely-increment-point [x y])
       (safely-increment-point [(inc x) y])
       (safely-increment-point [(dec x) y])
       (safely-increment-point [x (inc y)])
       (safely-increment-point [x (dec y)])
       (safely-increment-point [(inc x) (inc y)])
       (safely-increment-point [(inc x) (dec y)])
       (safely-increment-point [(dec x) (inc y)])
       (safely-increment-point [(dec x) (dec y)])
       (mark-as-flashed [x y])))

(defn do-any-octopus-need-to-flash?
  [rows]
  (if (nil? (some needs-to-flash? (reduce concat rows)))
    false
    true))

(defn compute-flashes
  "Mark octopus that flash. Returns the new state."
  [rows]
  (loop [rows rows]
    (if (not (do-any-octopus-need-to-flash? rows))
      rows
      (let [flash-point (find-flash-point rows)]
        (if (nil? flash-point)
          rows
          (recur (apply-flash-point flash-point (doall rows))))))))

(defn reset-flashes
  "Resets any octopus that flash. Returns the new state and the number of flashes that occurred."
  [rows]
  (let [num-flashes (count (filter has-flashed? (reduce concat rows)))
        new-rows (map (fn [row] (map reset-energy-levels row)) rows)]
    (list num-flashes new-rows)))

(defn step
  [rows]
  (->> rows
       ; "First, the energy level of each octopus increases by 1."
       (map (fn [row] (map increase-energy-level row)))

       ; "Then, any octopus with an energy level greater than 9 flashes."
       (compute-flashes)

       ; "Finally, any octopus that flashed during this step has its energy level set to 0"
       (reset-flashes)))

(defn steps
  [num-steps rows]
  (loop [rows rows
         num-steps num-steps
         num-flashes 0]
    (if (zero? num-steps)
      num-flashes
      (let [[this-num-flashes rows] (step rows)]
        (recur (seq rows) (dec num-steps) (+ num-flashes this-num-flashes))))))

(defn step-until-sync
  [rows]
  (let [num-octopus (* (count rows) (count (first rows)))]
    (loop [rows rows
           num-steps 1]
      (let [[num-flashes rows] (step rows)]
        (if (= num-flashes num-octopus)
          num-steps
          (recur rows (inc num-steps)))))))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (decorate-input-with-flash-info)
       (steps 100)))

(defn get-part-two
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (decorate-input-with-flash-info)
       (step-until-sync)))