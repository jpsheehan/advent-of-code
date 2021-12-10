(ns advent-of-code-2021.day-10-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day-10 :refer :all]))

(deftest get-part-one-test
  (testing "Check that the example for part one is correct"
    (is (= (get-part-one "resources/day_10_example.txt") 26397)))
  (testing "Check that the answer for part one is correct"
    (is (= (get-part-one "resources/day_10_input.txt") 321237))))

(deftest get-score-part-two-test
  (testing "Check the example is correct"
    (is (= (get-score-part-two (map lookup-points-for-character-part-two (list \] \) \} \>))) 294))))

(deftest get-score-part-two-test
  (testing "Check that the example for part two is correct"
    (is (= (get-part-two "resources/day_10_example.txt") 288957)))
  (testing "Check that the answer for part two is correct"
    (is (= (get-part-two "resources/day_10_input.txt") 2360030859))))