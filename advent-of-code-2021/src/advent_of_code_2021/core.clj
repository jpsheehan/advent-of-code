(ns advent-of-code-2021.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if (= 3 (count args))
    (let [day (Integer/parseInt (get args 2))]
      (println "it's day" day)
      )
    )

  (println "Hello, World!"))
