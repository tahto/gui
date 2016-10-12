(ns gui.fx
  (:require [hara.object :as object])
  (:import [javafx.application Application Platform]
           [javafx.scene.layout BorderPane]
           [javafx.scene.control Button]
           [javafx.scene Scene Group]
           [javafx.stage Screen Stage StageBuilder]))

(defn screens []
  (->> (Screen/getScreens)
       seq
       (map object/to-data)))



 
(defonce init
  (do (javafx.embed.swing.JFXPanel.)
      (Platform/setImplicitExit false)
      true))








(object/read-getters (.getBounds (Screen/getPrimary))
                     )


(.? Screen #"^get" :name)
("getBounds" "getDpi" "getPrimary" "getRenderScale" "getScreenForNative" "getScreens" "getScreensForRectangle" "getVisualBounds")


(object/meta-read Button)

(.* Button)


(defn render [scene])

(comment
  (render
   [:scene
    [:border-pane
     [:button "Hello World"]]]))

(object/to-data (Button.))


(.? javafx.scene.layout.Pane #"^get")

(.? Scene)


(.? javafx.scene.Parent :name)


(.getChildren (BorderPane. ))
[javafx.scene.layout.BorderPane
 [javafx.scene.layout.Pane #{}] [javafx.scene.layout.Region #{}] [javafx.scene.Parent #{}] [javafx.scene.Node #{}] [java.lang.Object #{javafx.css.Styleable javafx.event.EventTarget}]]

[javafx.scene.control.Button
 [javafx.scene.control.ButtonBase #{}]
 [javafx.scene.control.Labeled #{}]
 [javafx.scene.control.Control #{}]
 [javafx.scene.layout.Region #{javafx.scene.control.Skinnable}]
 [javafx.scene.Parent #{}]
 [javafx.scene.Node #{}]
 [java.lang.Object #{javafx.css.Styleable javafx.event.EventTarget}]]



(Platform/runLater
 (fn []
   (doto (Stage.)
     (.setScene (Scene. (Button. "hello")
                        )
                )
     (.setWidth 400)
     (.setHeight 400)
     ;;(.show)
     )))
(Button. "hello")

(.? Object :name)



(.%> Button)
[javafx.scene.control.Button [javafx.scene.control.ButtonBase #{}] [javafx.scene.control.Labeled #{}] [javafx.scene.control.Control #{}] [javafx.scene.layout.Region #{javafx.scene.control.Skinnable}] [javafx.scene.Parent #{}] [javafx.scene.Node #{}] [java.lang.Object #{javafx.css.Styleable javafx.event.EventTarget}]]





(.? javafx.embed.swing.JFXPanel "new")
(#[new :: () -> javafx.embed.swing.JFXPanel])
(def scene
  (Scene. (Group.)))





