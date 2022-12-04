(ns day04
  (:require [utils :refer [input-for-day test-ns]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split]]
            [clojure.test :refer [deftest testing is]]))

;;; Implementation

(defn- make-pairs [input]
  (->> input
       split-lines
       (map (fn [line]
              (map (fn [elf]
                     (map (fn [n]
                            (parse-long n))
                          (split elf #"-")))
                   (split line #","))))))

(defn solve1 [input]
  (let [pairs (make-pairs input)]
    (reduce (fn [a [[from1 to1] [from2 to2]]]
              (if (or (and (<= from1 from2)
                           (<= to2 to1))
                      (and (<= from2 from1)
                           (<= to1 to2)))
                (inc a)
                a))
            0
            pairs)))

(defn solve2 [input]
  (let [pairs (make-pairs input)]
    (reduce (fn [a [[from1 to1] [from2 to2]]]
              (if (or (<= from1 from2 to1)
                      (<= from2 from1 to2))
                (inc a)
                a))
            0
            pairs)))

;;; Tests

(def example "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(def input (input-for-day 4))

(deftest solve1-test
  (testing "part 1"
    (is (= 2 (solve1 example)))
    (is (= 569 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 4 (solve2 example)))
    (is (= 936 (solve2 input)))))
