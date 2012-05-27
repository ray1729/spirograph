(ns spirograph.tryme
  (:use quil.core))

(defn make-spirograph-fn
  "Returns a function that computes the position of the pencil at time
  `t` for a spirograph with outer circle of radius `R`. The parameter
  0 <= `k` <= 1 specifies the size of the inner circle with respect to
  the outer one, and 0 <= `l` <= 1 how far the pencil is from the
  cetre of the inner circle. See
  http://en.wikipedia.org/wiki/Spirograph#Mathematical_basis for a
  full explanation."
  [R k l]
  (fn
    [t]
    (let [x (* R (+ (* (- 1 k) (cos t))
                    (* l k (cos (* (/ (- 1 k) k) t)))))
          y (* R (- (* (- 1 k) (sin t))
                    (* l k (sin (* (/ (- 1 k) k) t)))))]
      [x y])))

(defn my-translate
  "Returns a function that displaces a point within the display
  window. TODO: can quil.core/translate be used instead?"
  [delta-x delta-y]
  (fn [[x y]]
    [(+ x delta-x) (+ y delta-y)]))

(defn spirograph
  "Draw a spirograph in a window of width `w` and height `h`, with
   outer circle of radius `R` and parameters `k` and `l`"
  ([k l]
     (spirograph 500 500 249 k l))
  ([w h R k l]
     (let [f (comp (my-translate (/ w 2) (/ h  2)) (make-spirograph-fn R k l))
           p (atom (f 0))]
       (sketch
        :title "Spirograph"
        :setup (fn [] (smooth) (frame-rate 100))
        :draw (fn [] (let [next-p (f (/ (frame-count) 5))]
                      (line @p next-p)
                      (reset! p next-p)))
        :size [w h]))))

(comment
  (spirograph 0.505 0.6)
  (spirograph 0.33 0.9)
  (spirograph 0.636 0.8)
  (spirograph 1.23 0.5))
