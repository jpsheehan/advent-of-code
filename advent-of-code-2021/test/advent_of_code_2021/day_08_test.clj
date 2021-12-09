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

(deftest solve-line-test
  (testing "Check that we can solve a single line"
    (is (= (solve-line (parse-input-line "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")) 5353))))

(deftest get-part-two-example-test
  (testing "Check that the example for part two is correct"
    (is (= (get-part-two "resources/day_08_example.txt") 61229))))

(deftest get-part-two-test
  (testing "Check that the answer for part two is correct"
    (is (= (get-part-two "resources/day_08_input.txt") 946346))))