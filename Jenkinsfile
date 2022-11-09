pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh './gradlew compileJava'
      }
    }

    stage('Build') {
      steps {
        sh './gradlew clean bootJar'
      }
    }

  }
}