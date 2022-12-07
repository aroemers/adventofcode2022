(ns day07
  (:require [utils :refer [input-for-day test-ns submit-answer]]
            [clojure.repl :refer [doc pst]]
            [clojure.set :as set]
            [clojure.string :refer [split-lines split starts-with?]]
            [clojure.test :refer [deftest testing is]]))

(def day 7)

;;; Implementation

(defn dirs [input]
  (loop [lines (split-lines input)
         dirs  {}
         path  []]
    (if-let [line (first lines)]
      (cond (starts-with? line "$ cd")
            (let [dir (subs line 5)]
              (case dir
                "/"  (recur (next lines) dirs [])
                ".." (recur (next lines) dirs (pop path))
                (recur (next lines) dirs (conj path dir))))

            (starts-with? line "$ ls")
            (recur (next lines) dirs path)

            (starts-with? line "dir ")
            (recur (next lines) dirs path)

            :else
            (let [[size file] (split line #" ")
                  size        (parse-long size)]
              (recur (next lines)
                     (reduce (fn [m p] (update m p (fnil + 0) size))
                             dirs
                             (reductions conj [] path))
                     path)))
      dirs)))



(defn solve1 [input]
  (->> input dirs vals (filter #(<= % 100000)) (reduce +)))

(defn solve2 [input]
  (let [dirs (dirs input)
        unused (- 70000000 (dirs []))
        needed (- 30000000 unused)]
    (->> dirs vals (filter #(<= needed %)) (reduce min))))

;;; Tests

(def example "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(def input (input-for-day day))

(deftest solve1-test
  (testing "part 1"
    (is (= 95437 (solve1 example)))
    (is (= 1297683 (solve1 input)))))

(deftest solve2-test
  (testing "part 2"
    (is (= 24933642 (solve2 example)))
    (is (= 5756764 (solve2 input)))))

;;; Submit

(def submit1 (partial submit-answer day 1))
(def submit2 (partial submit-answer day 2))
