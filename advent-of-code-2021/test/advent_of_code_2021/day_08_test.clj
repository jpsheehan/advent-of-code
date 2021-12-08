(ns advent-of-code-2021.day_08-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-08 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest parse-input-line-test
  (testing "Check that lines can be parsed correctly"
    (is (= (parse-input-line "ab cd ef ga | aa bb ccd") '(("ab" "cd" "ef" "ga") ("aa" "bb" "ccd"))))))

(deftest get-part-one-example-test
  (testing "Check that the example for part one is correct"
    (is (= (get-part-one "resources/day_08_example.txt") 26))))

(deftest get-part-one-test
  (testing "Check that the answer for part one is correct"
    (is (= (get-part-one "resources/day_08_input.txt") 239))))