(ns spirograph.core
  (:use quil.core))

(def h 400)
(def w 400)
(def r0 120)
(def r1 40)
(def w0 1)
(def w1 (* -0.5 w0 (/ r1 r0)))

(defn setup
  []
  (smooth)
  (stroke-weight 1)
  (set-state! :p (atom [(/ w 2) (+ (/ h 2) r0 r1)]))
  (frame-rate 50))

(defn draw
  []
  (let [c (frame-count)
        x (+ (/ w 2)
             (* r0 (sin (* w0 c)))
             (* r1 (sin (* w1 c))))
        y (+ (/ h 2)
             (* r0 (cos (* w0 c)))
             (* r1 (cos (* w1 c))))]
    (line @(state :p) [x y])
    (reset! (state :p) [x y])))

(defsketch spirograph
  :title "Spirograph"
  :setup setup
  :draw draw
  :size [w h])
