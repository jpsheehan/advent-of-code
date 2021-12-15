(ns advent-of-code-2021.day-14
  (:require [clojure.string :as str]))

(defn parse-rule-line
  [line]
  (let [[pair output] (str/split line #" -> ")]
    [pair output]))

(defn parse-input-line
  [line]
  (let [line (str/trim line)]
    (if (empty? line)
      nil
      (if (str/includes? line " -> ")
        (parse-rule-line line)
        line))))

(defn parse-input
  [input]
  (let [[template & rules] (->> input
                                (str/split-lines)
                                (map parse-input-line)
                                (filter #(not (nil? %))))]
    [template (apply hash-map (flatten rules))]))

(defn get-template-combos
  [template]
  (for [i (range (dec (count template)))]
    (str/join (take 2 (drop i template)))))

(defn expand-once
  [template rules]
  (->> template
       (get-template-combos)
       (map #(str (first %) (get rules %) (second %)))
       (reduce str/join)))

(defn expand
  [template rules steps]
  (loop [template template
         steps steps]
    (if (zero? steps)
      template
      (recur (expand-once template rules) (dec steps)))))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (apply (fn [template rules] (expand template rules 100)))
       (println)))