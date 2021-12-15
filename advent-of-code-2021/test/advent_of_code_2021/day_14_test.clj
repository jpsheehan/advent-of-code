(ns advent-of-code-2021.day-14-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-14 :refer :all]))

(def example-input (parse-input (slurp "resources/day_14_example.txt")))

(deftest get-template-combos-test
  (testing "Check the example"
    (is (= (get-template-combos "NNCB") '("NN" "NC" "CB")))))

(deftest expand-once-test
  (let [[template rules] example-input]
    (testing "Check the first step of the example"
      (is (= (expand-once template rules) "NCNBCHB")))))

(deftest expand-test
  (let [[template rules] example-input]
    (testing "Check 2 expansions of the example input"
      (is (= (expand template rules 2) "NBCCNBBBCBHCB")))
    (testing "Check 4 expansions of the example input"
      (is (= (expand template rules 4) "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")))))

(deftest get-part-one-test
  (testing "Check the example for part one"
    (is (= (get-part-one "resources/day_14_example.txt") 1588))))
