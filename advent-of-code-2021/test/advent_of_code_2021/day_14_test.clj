(ns advent-of-code-2021.day-14-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-14 :refer :all]))

(deftest get-template-combos-test
  (testing "Check the example"
    (is (= (get-template-combos "NNCB") '("NN" "NC" "CB")))))

(deftest get-part-one-test
  (testing "Check the example for part one"
    (is (= (get-part-one "resources/day_14_example.txt") 1588))))
