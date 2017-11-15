jpetstoreを題材にしたリファレンス実装
=============

※ WIP です！

- petstore-scala-1-one-by-one
  - MyBatis版のjpetstore をScalaに移植
  - なるべく名称や構造を保持しつつも、Scalaの道から外れない程度に改変する
    - ドメインモデルをイミュータブルにする
    - ActionBean に状態を持たない
  - Play を利用
    - Spring から Play の DI 機構 (Guiceベース) へ
    - JSP/stripes から Twirl へ
  - 大きな変更点
    − FlatOrder と Order + OrderAddress とに分離。22カラム問題に対応するため
    − StateHander コードを追加。ActionBeanのステートフルネス相当の動きをさせるため
  - よくわかってないところ
    − MyBatisのトランザクション
- petstore-scala-2-clean
  - Clean Architecture をベースとした構造の改変
  - それっぽい名称
  - 依存関係を矯正するためにプロジェクトを分割
- petstore-scala-2-clean-and-simplified
  - Clean Architecture の崩し
