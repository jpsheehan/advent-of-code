(ns advent-of-code-2021.day_01-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day_01 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest part-one
  (testing "Check part one answer is correct"
    (bind-stdin-to-resource "day_01_input.txt"
                            #(is (= (get-part-one) 1692)))
    ))

(deftest part-two
  (testing "Check part two answer is correct"
    (bind-stdin-to-resource "day_01_input.txt"
                            #(is (= (get-part-two) 1724)))
    ))
