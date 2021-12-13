(ns advent-of-code-2021.day-12
  (:require [clojure.string :as string]))

(defn is-start? [cave] (= cave "start"))
(defn is-end? [cave] (= cave "end"))
(defn is-small-cave? [cave] (= (string/lower-case cave) cave))
(defn is-big-cave? [cave] (= (string/upper-case cave) cave))

(defn parse-input-line
  [line]
  (-> line
      (string/trim-newline)
      (string/split #"-")))

(defn parse-input
  [input]
  (->> input
       (string/split-lines)
       (map parse-input-line)))

(defn is-next-hop-valid?
  [path [from to]]
  (let [current (last path)]
    (and
      (= current from))
      (not (and (is-small-cave? to) (contains? path to)))))

;(defn traverse-path
;  [edges]
;  (loop [path-in-progress []
;         completed-paths []]
;    (let [possible-edges (filter (partial is-next-hop-valid? path) edges)]
;      (if (empty? possible-edges)
;        path
;        (map #(recur (cons % path)) possible-edges)))))
;
;(defn find-paths
;  [edges]
;  (->> edges
;       (filter is-start?)
;       (map traverse-path)
;       (into set)))

(defn get-part-one
  [filename]
  (->> filename
       (slurp)
       (parse-input)
       (find-paths)
       (count)))