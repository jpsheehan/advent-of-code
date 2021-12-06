(ns advent-of-code-2021.day_02-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day_02 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest part-one
  (testing "Check part one answer is correct"
    (bind-stdin-to-resource "day_02_input.txt"
                            #(is (= (get-part-one) 2322630)))
    ))

(deftest part-two
  (testing "Check part two answer is correct"
    (bind-stdin-to-resource "day_02_input.txt"
                            #(is (= (get-part-two) 2105273490)))
    ))
