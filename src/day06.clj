(ns day06
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split]]
            [clojure.test :refer [deftest testing is]]))

(def day 6)

;;; Implementation

(defn solve [input signal-size]
  (loop [remaining (drop signal-size input)
         index     signal-size
         signal    (take signal-size input)]
    (if (= (count (distinct signal)) signal-size)
      index
      (recur (rest remaining)
             (inc index)
             (concat (rest signal) [(first remaining)])))))

(defn solve1 [input]
  (solve input 4))

(defn solve2 [input]
  (solve input 14))

;;; Tests

(def example "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= 7 (solve1 example)))
    (is (= 1896 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 19 (solve2 example)))
    (is (= 3452 (solve2 input)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
