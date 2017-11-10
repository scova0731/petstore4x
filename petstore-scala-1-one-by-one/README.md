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

catalog/Main
beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean"
 -> ../catalog/
beanclass="org.mybatis.jpetstore.web.actions.CartActionBean" event="viewCart" 
  -> ../cart/cart
beanclass="org.mybatis.jpetstore.web.actions.CartActionBean" event="addItemToCart" 
  -> ../cart/add-item?itemId=  (TODO まさかのGETでのカート操作)
beanclass="org.mybatis.jpetstore.web.actions.CartActionBean" event="removeItemFromCart"
  -> ../cart/remove-item?itemId=  (TODO まさかのGETでのカート操作)
beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean" event="signonForm"
  -> ../account/signon-form 
beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean" event="signoff"
  -> ../account/signoff
beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean" event="editAccountForm"
  -> ../account/edit-account-form
beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean" event="viewCategory"
  -> ../catalog/view-category?categoryId=    
    <stripes:param name="categoryId" value="FISH" />
    <stripes:param name="categoryId" value="DOGS" />
    <stripes:param name="categoryId" value="REPTILES" />
    <stripes:param name="categoryId" value="CATS" />
    <stripes:param name="categoryId" value="BIRDS" />
beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean" event="viewProduct"
  -> ../catalog/view-product?productId=
beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean" event="viewItem"
  -> ../catalog/view-item?itemId=
beanclass="org.mybatis.jpetstore.web.actions.OrderActionBean" event="newOrderForm"
  -> ../order/checkout
    
# TODO - sessionScope っぽいもの
"accountBean" = "true"
"accountBean_authenticated" = "true"
"accountBean_account_bannerOption"
"accountBean_account_bannerName"
"accountBean_account_firstName"
"accountBean_account_listOption"

    
# TODO

- ライセンス表記 (LICENSE.md と コード内の表示)
- a hrefをroutesベースに置き換える
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


# Progress
├── account
│   ├─[ ] EditAccountForm.scala.html
│   ├─[ ] IncludeAccountFields.scala.html
│   ├─[ ] NewAccountForm.scala.html
│   └─[x] SignonForm.scala.html
├── cart
│   ├─[x] Cart.scala.html
│   ├─[ ] Checkout.scala.html
│   └─[ ] IncludeMyList.scala.html
├── catalog
│   ├─[x] Category.scala.html
│   ├─[x] Item.scala.html
│   ├─[x] Main.scala.html
│   ├─[x] Product.scala.html
│   └─[x] SearchProducts.scala.html
├── common
│   ├─[x] Error.scala.html
│   ├─[x] IncludeBottom.scala.html
│   └─[x] IncludeTop.scala.html
│       <stripes:messages /> ... 消しちゃったけど、、、エラーでないときにつかう？
└── order
    ├─[x] ConfirmOrder.scala.html
    ├─[ ] ListOrders.scala.html
    ├─[x] NewOrderForm.scala.html
    ├─[x] ShippingForm.scala.html
    └─[ ] ViewOrder.scala.html
        

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
