(ns gui.fx.type
  (:require [hara.object :as object]
            [hara.reflect :as reflect]
            [hara.object.map-like :as map-like]
            [hara.data.diff :as diff]
            [clojure.string :as string])
  (:import (javafx.stage DirectoryChooser
                         FileChooser
                         Popup
                         PopupWindow
                         Screen
                         Stage
                         Window
                         WindowEvent)
           [javafx.scene Scene Group]
           (javafx.scene.layout BorderPane
                                FlowPane
                                GridPane
                                StackPane
                                TilePane)
           (javafx.scene.control Accordion
                                 Button
                                 CheckBox
                                 Label)
           (javafx.scene.text Font)
           (javafx.scene.paint Color)
           (javafx.geometry BoundingBox	
                            Bounds	
                            Dimension2D	
                            Insets	
                            Point2D	
                            Point3D	
                            Rectangle2D)))

(defn changes
  [component default]
  (->> (diff/diff
        (object/to-data component)
        default)
       (:>)
       (reduce-kv (fn [out ks v]
                    (assoc-in out ks v))
                  {})))

(defn empty-constructor [cls]
  (reflect/query-class cls ["new" 0 :#]))

(defn default-state [cls]
  (if-let [constr (empty-constructor cls)]
    (object/to-data (constr))
    {}))

(defmacro extend-components
  [& classes]
  (vec (mapcat (fn [cls]
                 (let [default-sym (symbol (str "DEFAULT_" (.toUpperCase (.replaceAll (.getName (resolve cls))
                                                                                      "\\." "_"))))]
                   `[(declare ^:dynamic ~default-sym)
                     (object/map-like
                      ~cls  {:tag ~(-> (string/split (.getName (resolve cls))
                                                     #"\.")
                                       last
                                       (.toLowerCase))

                             :write   {:empty (empty-constructor ~cls)}
                             
                             :exclude [:class
                                       :css-meta-data
                                       :control-css-meta-data
                                       :local-to-scene-transform
                                       :local-to-parent-transform
                                       :event-dispatcher]
                             :display (fn [c#] (changes c# (deref (var ~default-sym))))
                             })
                     (def ^:dynamic ~default-sym (default-state ~cls))]))
               classes)))

(comment (extend-components Accordion Button CheckBox Label))

(object/map-like
 Font      {:tag "font"})

(object/map-like
 Color      {:tag "color"
             :write {:methods :all
                     :from-map (fn [{:keys [blue red green opacity]}]
                                 (Color. red green blue (or opacity 0)))}})

(object/map-like
 ;; javafx.scene.layout
 
 BorderPane     {:tag "pane"}
 FlowPane       {:tag "pane"}
 GridPane       {:tag "pane"}
 StackPane      {:tag "pane"}
 TilePane       {:tag "pane"})

(object/map-like
 ;; javafx.stage
 
 DirectoryChooser  {:tag "choose"}
 FileChooser       {:tag "choose"}
 Popup             {:tag "window"}
 PopupWindow       {:tag "window"}
 Screen            {:tag "screen"}
 Stage             {:tag "stage"}
 Window            {:tag "window"}
 WindowEvent       {:tag "event"})

(object/map-like
 ;; javafx.geometry
 
 BoundingBox {:tag "box"}
 Bounds	     {:tag "box"}
 Dimension2D {:tag "box"}
 Insets	     {:tag "box"}
 Point2D     {:tag "point"}
 Point3D     {:tag "point"}	
 Rectangle2D {:tag "box"})
