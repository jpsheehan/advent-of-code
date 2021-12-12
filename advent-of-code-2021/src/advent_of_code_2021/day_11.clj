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

(defn reset-energy-levels
  [octopus]
  (if (has-flashed? octopus)
    [0 :no-flash]
    [(first octopus) :no-flash]))

(defn step
  [rows]
  (->> rows
       ; "First, the energy level of each octopus increases by 1."
       (map (fn [row] (map increase-energy-level row)))

       ; "Then, any octopus with an energy level greater than 9 flashes."
       ; TODO

       ; "Finally, any octopus that flashed during this step has its energy level set to 0"
       (map (fn [row] (map reset-energy-levels row)))))

(defn steps
  [rows num-steps]
  (loop [rows rows
         num-steps num-steps
         num-flashes 0]
    (if (zero? num-steps)
      num-flashes
      (let [[rows num-flashes] (step rows)]
        (recur rows (dec num-steps) num-flashes)))))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (decorate-input-with-flash-info)
       (steps 100)))