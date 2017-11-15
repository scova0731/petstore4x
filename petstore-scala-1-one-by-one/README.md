scala-reference-architecture/clean-architecture
===============================================

※ あとで書き直す


# シナリオ

- ビューを含まない。伝統的なWebアプリケーションや

# Keywords

- Controller (some of InterfaceAdapter)
- Presenter (some of InterfaceAdapter)
- Service (UseCase, Story ...)
- Repository (Store, )
- Gateway and Infrastructure (some of InterfaceAdapter)

# Decision

- Put view controller ...

# References

- `./mvnw cargo:run -P tomcat85`
  
  
# したこと(あとで整理)

- JSP to Twirl
  - ディレクティブ(page, include, taglib) は全て削除
  - ライセンス文をコメントアウト
  - stripes:link を静的リンクに置き換え (beanclass属性は残した)


# TODO

- Order中心にテストする 
- ライセンス表記 (LICENSE.md と コード内の表示)
- 外向けのREADMEにする
    
# TODO - どうにかする


http://www.mybatis.org/guice/index.html
MyBatisModuleを再構成する

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

[warn] Found version conflict(s) in library dependencies; some are suspected to be binary incompatible:
[warn] 	* org.codehaus.plexus:plexus-utils:3.0.17 is selected over {2.1, 1.5.5}
[warn] 	    +- org.apache.maven:maven-settings:3.2.2              (depends on 3.0.17)
[warn] 	    +- org.apache.maven:maven-repository-metadata:3.2.2   (depends on 3.0.17)
[warn] 	    +- org.apache.maven:maven-aether-provider:3.2.2       (depends on 3.0.17)
[warn] 	    +- org.apache.maven:maven-model:3.2.2                 (depends on 3.0.17)
[warn] 	    +- org.apache.maven:maven-core:3.2.2                  (depends on 3.0.17)
[warn] 	    +- org.apache.maven:maven-artifact:3.2.2              (depends on 3.0.17)
[warn] 	    +- org.apache.maven:maven-settings-builder:3.2.2      (depends on 3.0.17)
[warn] 	    +- org.apache.maven:maven-model-builder:3.2.2         (depends on 3.0.17)
[warn] 	    +- org.sonatype.plexus:plexus-sec-dispatcher:1.3      (depends on 1.5.5)
[warn] 	    +- org.eclipse.sisu:org.eclipse.sisu.plexus:0.0.0.M5  (depends on 2.1)
[warn] 	* com.google.guava:guava:23.0 is selected over {10.0.1, 15.0, 18.0}
[warn] 	    +- io.methvin:directory-watcher:0.1.3                 (depends on 23.0)
[warn] 	    +- org.eclipse.sisu:org.eclipse.sisu.plexus:0.0.0.M5  (depends on 10.0.1)
[warn] 	    +- com.fasterxml.jackson.datatype:jackson-datatype-guava:2.6.0 (depends on 10.0.1)
[warn] 	    +- com.spotify:docker-client:3.5.13                   (depends on 10.0.1)
[warn] Run 'evicted' to see detailed eviction warnings


        

# 改めてインストール
```
$ brew install sbt
$ sbt new playframework/play-scala-seed.g8


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
