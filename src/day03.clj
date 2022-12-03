(ns day03
  (:require [utils :refer [input-for-day test-ns]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split]]
            [clojure.test :refer [deftest testing is]]))

;;; Implementation

(def score (zipmap "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                   (range 1 54)))

(defn solve1 [input]
  (->> input
       split-lines
       (map (fn [line] (split-at (/ (count line) 2) line)))
       (map (fn [[p1 p2]] (set/intersection (set p1) (set p2))))
       (map (fn [overlap] (score (first overlap))))
       (reduce +)))

(defn solve2 [input]
  (->> input
       split-lines
       (map set)
       (partition 3)
       (map (fn [sets] (apply set/intersection sets)))
       (map (fn [overlap] (score (first overlap))))
       (reduce +)))

;;; Tests

(def example "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(def input (input-for-day 3))

(deftest solve1-test
  (testing "part 1"
    (is (= 157 (solve1 example)))
    (is (= 7716 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 70 (solve2 example)))
    (is (= 2973 (solve2 input)))))
