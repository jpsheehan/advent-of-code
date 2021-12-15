(ns advent-of-code-2021.day-13-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-13 :refer :all]))

(deftest get-part-one-test
  (testing "The example answer for part one"
    (is (= (get-part-one "resources/day_13_example.txt") 17)))
  (testing "The real answer for part one"
    (is (= (get-part-one "resources/day_13_input.txt") 737))))