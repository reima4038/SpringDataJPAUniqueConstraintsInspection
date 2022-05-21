# SpringDataJPAUniqueConstraintsInspection

## 目的

DDDのEntityはIDによってオブジェクトの一意性を表す。
本検証ではEntityのIDが同一であれば、インスタンスを作り直しても同じものとみなされるかを検証する。

### 補足

Entityの更新は通常、FindしたEntityの値を更新するだけでもDBに反映される。
更新時にインスタンスをnewする必要はないのだが、そのようなコードの挙動を確かめたく、本検証を実施する。

## 環境

* SpringBoot: 2.7.0
* SpringDataJPA: 2.7.0
* h2: 2.1.212
* Java 16

## 手順

本リポジトリのコードを使って、EntityのInsertを一回、Updateを二回かける。
1. Insert: 新規に作成したEntityを登録する。
2. Update: RepositoryからfindしたEntityのフィールド値を書き換え、更新する。
3. Update: RepositoryからfindしたEntityのIDを取り出し、新しく作ったEntityのインスタンスに詰め替え、更新する。
※Insert, UpdateどちらもSpringDataJPAのsaveメソッドを利用する。

IDによって同一であるかを見なしているのであれば、3でも問題なく更新できるはず。

## 結果

1,2,3ともにテーブルを正常に更新し、一意制約違反などエラーは起きなかった。
今回はH2でのみ確認したため、MySQLなど他のDBでも同様かは検証を要する。

### 課題： Persistableインターフェース

[この記事](https://hosochin.com/2020/08/16/post-589/)によると、SpringDataJPAはsave時に、SelectしてからInsert,Updateを判断する。
PersistableインターフェースをEntityで実装し、オーバーライドしたisNew()メソッドの返り値をtrueとすると、このSelectが省略される。
そのため、同一キー存在する場合は一意制約でNGとなる。

……が、本リポジトリで検証した際は、isNew()をtrueで返しても、Selectされてしまった。
-> 悪い例として実装していたevilUpdateメソッドが呼ばれていなかった。
<img width="812" alt="スクリーンショット 2022-05-21 18 50 26" src="https://user-images.githubusercontent.com/1913126/169647521-75d7f613-df0f-4c25-8e0f-6a93afb81d94.png">

結果としては以下の通り。
* isNew 未実装: update, evilUpdate どちらも更新可。
* isNew 実装 true: update は更新可、 evilUpdate は更新不可。
* isNew 実装 false: update, evilUpdate どちらも更新可。
※ update: findしたEntityに値を変更してsave, evilUpdate: 既存のIdを新規のEntityに設定してsave.

