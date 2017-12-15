jpetstoreを題材にしたリファレンス実装
=============

- Step.1: petstore-scala-1-one-by-one
  - [MyBatis 版の jpetstore](https://github.com/mybatis/jpetstore-6) を Scala に移植
  - 比較しやすいよう Mybatis 版の名称や構造を極力保持しつつも、最低限の Scala の作法を守る
    - クラスをイミュータブルにする (`var` を使用しない)
    - ステートフルな ActionBean も取り除く
  - PlayFramework を利用
    - Spring から Play の DI 機構 (Guiceベース) へ
    - JSP/stripes から `Twirl` へ
      - ディレクティブ(page, include, taglib) は全て削除
      - ライセンス文をコメントアウト
      - stripes:link を `routes` に置き換え
    - ステートは、 リクエスト毎に `Ehcache` キャッシュから再構築
  - その他の変更点
    - FlatOrder と Order + OrderAddress とに分離。22カラム問題に対応するため
    - StateHander コードを追加。ActionBean の状態保持相当の動きをさせるため
  - 未完了
    - DBトランザクションは、 `＠Transactional` を除いたまま
- (WIP) Step.2: petstore-scala-2-clean
  - Clean Architecture をベースとしたレイヤー構造の改変
  - 更にプロジェクトを分割して、依存関係を明確にする
- (TODO) Step.3: petstore-scala-3-simple
  - Clean Architecture のものをシンプルにする
