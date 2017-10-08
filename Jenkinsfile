stage 'checkout'
node {
  git 'https://github.com/JungleTree/JungleTree.git'
}

stage 'build'
node {
  jdk = tool name: 'java-9-oracle'
  env.JAVA_HOME = "${jdk}"

  sh './gradlew build -x test'
}

stage 'deploy'
node {
  sh './gradlew publish -x test'
}
