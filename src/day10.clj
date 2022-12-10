(ns day10
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split starts-with?]]
            [clojure.test :refer [deftest testing is]]))

(def day 10)

;;; Implementation

(defn input->cycles [input]
  (loop [lines  (split-lines input)
         cycle  0
         x      1
         cycles []]
    (if-let [line (first lines)]
      (if (= line "noop")
        (recur (rest lines) (inc cycle) x (conj cycles x))
        (let [[_ val-str] (re-matches #"addx (-?\d+)" line)
              value       (parse-long val-str)]
          (recur (rest lines) (+ cycle 2) (+ x value) (into cycles [x x]))))
      cycles)))

(defn solve1 [input]
  (let [cycles (input->cycles input)]
    (->> [20 60 100 140 180 220]
         (map (fn [cycle] (* cycle (nth cycles (dec cycle)))))
         (apply + ))))

(defn solve2 [input]
  (let [cycles (input->cycles input)]
    (->> (for [row (range 6)]
           (->> (for [column (range 40)]
                  (let [cycle (+ column (* row 40))
                        x     (nth cycles cycle)]
                    (if (<= (dec column) x (inc column)) "#" ".")))
                (apply str)))
         (interpose \newline)
         (apply str))))

;;; Tests

(def example "addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= 13140 (solve1 example)))
    (is (= 13220 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= "##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######....." (solve2 example)))
    (is (= "###..#..#..##..#..#.#..#.###..####.#..#.
#..#.#..#.#..#.#.#..#..#.#..#.#....#.#..
#..#.#..#.#..#.##...####.###..###..##...
###..#..#.####.#.#..#..#.#..#.#....#.#..
#.#..#..#.#..#.#.#..#..#.#..#.#....#.#..
#..#..##..#..#.#..#.#..#.###..####.#..#." (solve2 input)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
