petstore4s / clean-architecture version
===============================================




# バリエーション

* レイヤー分離前
* レイヤー分離とテスト
* DB変更とプレゼンター分離
* マイクロサービス
* D D D的なアレンジ
* もう一回ミニマルにする
* C lojure

- petstore-scala-one-by-one

- clean-architecture
- clean-architecture-bks
Boku ga Kangaeru Saitekina (Not saikyo) na solution
- clean-architecture-kzs
or KuZuShi
- hexagonal
- onion
- ddd

# Keywords

- Controller (some of InterfaceAdapter)
- Presenter (some of InterfaceAdapter)
- Service (UseCase, Story ...)
- Repository (Store, )
- Gateway and Infrastructure (some of InterfaceAdapter)

# Decision

- Put view controller ...

# References

-



# TODO - どうにかする

[warn] Found version conflict(s) in library dependencies; some are suspected to be binary incompatible:
[warn] 	* com.google.guava:guava:22.0 is selected over 19.0
[warn] 	    +- com.typesafe.play:play_2.12:2.6.6                  (depends on 22.0)
[warn] 	    +- com.google.inject:guice:4.1.0                      (depends on 19.0)
[warn] 	* com.typesafe.akka:akka-stream_2.12:2.5.4 is selected over 2.4.19
[warn] 	    +- com.typesafe.play:play-streams_2.12:2.6.6          (depends on 2.5.4)
[warn] 	    +- com.typesafe.akka:akka-http-core_2.12:10.0.10      (depends on 2.4.19)
[warn] 	* com.typesafe.akka:akka-actor_2.12:2.5.4 is selected over 2.4.19
[warn] 	    +- com.typesafe.akka:akka-slf4j_2.12:2.5.4            (depends on 2.5.4)
[warn] 	    +- com.typesafe.play:play_2.12:2.6.6                  (depends on 2.5.4)
[warn] 	    +- com.typesafe.akka:akka-stream_2.12:2.5.4           (depends on 2.5.4)
[warn] 	    +- com.typesafe.akka:akka-parsing_2.12:10.0.10        (depends on 2.4.19)
