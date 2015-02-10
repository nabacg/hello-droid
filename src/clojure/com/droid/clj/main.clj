(ns com.droid.clj.main
    (:require [neko.activity :refer [defactivity set-content-view!]]
              [neko.debug :refer [*a]]
              [neko.threading :refer [on-ui]]))

(comment  (def state (atom {:label "TEXT FROM ATOM"})))
(comment  :onClick (fn [_] (swap! state (update :label "NEW TEXT FROM ATOM"))))
(comment  [:text-view {:text (:label @state)}])
(declare ^android.widget.LinearLayout mylayout)

(defn set-elmt [elmt s]
  (.setText (elmt (.getTag mylayout))  s))

(defn get-elmt [elmt]
  (-> (elmt (.getTag mylayout))
      (.getText)
      str))

(defn update-ui []
  (set-elmt ::first (get-elmt ::third))
  (set-elmt ::second "AND ANOTHER"))



(defactivity com.droid.clj.StartActivity
  :key :main
  :on-create
  (fn [this bundle]
    (on-ui
      (set-content-view! (*a)
                         [:linear-layout
                          {:orientation :vertical
                           :def `mylayout
                           :id-holder true}
                          [:text-view {:text "Custom Hello from Clojure!"
                                       :id ::first}]
                          [:text-view {:text "Hopefully it works"
                                       :id ::second}]
                          [:edit-text {:hint "test"
                                       :id ::third}]
         [:button {:text "OK"
                   :on-click (fn [_] (update-ui))}]]))))
