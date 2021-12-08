(ns advent-of-code-2021.day_07-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-07 :refer :all]
            [advent-of-code-2021.test-helpers :refer :all]))

(deftest get-part-one-example-test
  (testing "Check that the example for part one is correct"
    (is (= (get-part-one "resources/day_07_example.txt") 37))))

(deftest get-part-one-test
  (testing "Check that the answer for part one is correct"
    (is (= (get-part-one "resources/day_07_input.txt") 326132))))

(deftest fuel-usage-part-two-test
  (testing "Check that the result matches the website"
    (is (= (fuel-usage-part-two 16 5) 66))
    (is (= (fuel-usage-part-two 5 16) 66))))

(deftest get-part-two-example-test
  (testing "Check that the example for part two is correct"
    (is (= (get-part-two "resources/day_07_example.txt") 168))))

(deftest get-part-two-test
  (testing "Check that the answer for part two is correct"
    (is (= (get-part-two "resources/day_07_input.txt") 88612508))))