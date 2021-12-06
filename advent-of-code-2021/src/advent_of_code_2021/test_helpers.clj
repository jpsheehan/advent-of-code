(ns advent-of-code-2021.test-helpers)

(defn bind-stdin-to-file
  "Binds the program's standard input to a file on disk."
  [file-path func]
  (with-open [file (clojure.java.io/reader file-path)]
    (binding [*in* file]
      (func)))
  )

(defn bind-stdin-to-resource
  "Binds the program's standard input to a file inside the 'resources' directory."
  [file-name func]
  (let [file-path (str "resources/" file-name)]
    (bind-stdin-to-file file-path func)))