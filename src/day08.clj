(ns day08
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split starts-with?]]
            [clojure.test :refer [deftest testing is]]))

(def day 8)

;;; Implementation

(defn input->grid [input]
  (vec (for [line (split-lines input)]
                    (vec (for [char line]
                           (- (long char) 48))))))

(defn up [grid row column]
  (map #(nth % column) (take row grid)))

(defn down [grid row column]
  (map #(nth % column) (drop (inc row) grid)))

(defn left [grid row column]
  (take column (get grid row)))

(defn right [grid row column]
  (drop (inc column) (get grid row)))

(defn solve1 [input]
  (let [grid (input->grid input)
        size (count grid)

        visible
        (for [row (range size)]
          (for [column (range size)]
            (or (= row 0) (= row (dec size)) (= column 0) (= column (dec size))
                (let [height (get-in grid [row column])
                      lower? #(< % height)]
                  (or (every? lower? (left grid row column))
                      (every? lower? (right grid row column))
                      (every? lower? (up grid row column))
                      (every? lower? (down grid row column)))))))]
    (count (mapcat #(filter identity %) visible))))

(defn visible [height trees]
  (let [[lower other] (split-with #(< % height) trees)]
    (+ (count lower) (if (seq other) 1 0))))

(defn solve2 [input]
  (let [grid (input->grid input)
        size (count grid)

        scores
        (for [row (range 1 (dec size))]
          (for [column (range 1 (dec size))]
            (let [height (get-in grid [row column])]
              (* (visible height (reverse (up grid row column)))
                 (visible height (reverse (left grid row column)))
                 (visible height (right grid row column))
                 (visible height (down grid row column))))))]
    (reduce max (mapcat identity scores))))

;;; Tests

(def example "30373
25512
65332
33549
35390")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= 21 (solve1 example)))
    (is (= 1647 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 8 (solve2 example)))
    (is (= 392080 (solve2 input)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
