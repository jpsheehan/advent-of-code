(ns advent-of-code-2021.day-11-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-11 :refer :all]))

(deftest parse-input-test
  (testing "Check that parsing input works"
    (is (= (parse-input "12345\n54321") '(( 1 2 3 4 5) (5 4 3 2 1))))))

(deftest get-part-one-test
  (testing "Check the example answer for part one"
    (is (= (get-part-one "resources/day_11_input.txt") 1656))))