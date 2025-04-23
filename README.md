# 「Cloud Native Spring in Action」の catalog-service （Kotlin）

ローカルでビルドしたイメージを実行:

$ docker run --rm --name cnkia-catalog-service -p 8080:8080 cnkia-catalog-service:0.0.1-SNAPSHOT

ローカルでビルドしたイメージをminikubeにロード:

$ minikube image load cnkia-catalog-service:0.0.1-SNAPSHOT

上のコマンドが下記のようなエラーになる場合:

Failed to load image: save to dir: caching images: caching image "/home/xxx/.minikube/cache/images/amd64/cnkia-catalog-service_0.0.1-SNAPSHOT": write: unable to calculate manifest: blob shaxyz... not found

以下のコマンドで代替することで回避可能:

$ docker image save -o cnkia-catalog-service.tar cnkia-catalog-service:0.0.1-SNAPSHOT
$ minikube image load ./cnkia-catalog-service.tar

minikubeを起動:

$ minikube start --driver=docker

minikubeにロードしたイメージをKubernetesクラスタ上にDeploymentを作成して実行:

$ kubectl create deployment cnkia-catalog-service --image=cnkia-catalog-service:0.0.1-SNAPSHOT

DeploymentをServiceとして8080番ポート介して公開:

$ kubectl expose deployment cnkia-catalog-service --name cnkia-catalog-service --port 8080

クラスタに公開したServiceの8080番ポートをローカルマシンの8082番ポートにマッピング:

$ kubectl port-forward service/cnkia-catalog-service 8081:8080
