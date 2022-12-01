(ns day01
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split-lines]]
            [clojure.test :refer [deftest testing is]]))

;;; Implementation

(defn elves [input]
  (->> input
       split-lines
       (partition-by empty?)
       (take-nth 2)
       (map #(map parse-long %))))

(defn solve1 [input]
  (let [sums (->> input elves (map #(apply + %)))]
    (apply max sums)))

(defn solve2 [input]
  (let [sums (->> input elves (map #(apply + %)))]
    (->> sums sort reverse (take 3) (apply +))))

;;; Tests

(def example "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

(def input (slurp (io/resource "inputs/day01.txt")))

(deftest solve1-test
  (testing "part 1"
    (is (= 24000 (solve1 example)))
    (is (= 66719 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 45000 (solve2 example)))
    (is (= 198551 (solve2 input)))))
