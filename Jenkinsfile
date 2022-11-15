pipeline {
    agent any

    triggers {
        pollSCM('*/3 * * * *')
    }

    environment {
        imagename = "hojin19082/restful_back"
        registryCredential = 'docker-hub'
        dockerImage = ''
    }

    stages {
        // git에서 repository clone
        stage('Prepare') {
          steps {
            echo 'Clonning Repository'
            git url: 'https://github.com/KA-SURFY/Restful_back',
              branch: 'master',
              credentialsId: 'github_cred'
            }
            post {
             success { 
               echo 'Successfully Cloned Repository'
             }
           	 failure {
               error 'This pipeline stops here...'
             }
          }
        }

        // gradle build
        stage('Bulid Gradle') {
          agent any
          steps {
            echo 'Bulid Gradle'
            dir ('.'){
                sh """
                ./gradlew clean build --exclude-task test
                """
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }
        
        // docker build
        stage('Bulid Docker') {
          agent any
          steps {
            echo 'Bulid Docker'
            script {
                dockerImage = docker.build imagename
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        // docker push
        stage('Push Docker') {
          agent any
          steps {
            echo 'Push Docker'
            script {
                docker.withRegistry( '', registryCredential) {
                    dockerImage.push("${currentBuild.number}")  // ex) "1.0"
                }
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        stage('Deploy to dev') {
          steps {
            git credentialsId: 'github_cred',
                    url: 'https://github.com/KA-SURFY/argocd.git',
                    branch: 'master'
            
            sh "sed -i 's/restful_back:.*\$/restful_back:${currentBuild.number}/g' back-deploy/deployment.yaml"
            sh "git add back-deploy/deployment.yaml"
            sh "git commit -m '[UPDATE] restful_back ${currentBuild.number} image versioning'"

          withCredentials([usernamePassword(credentialsId: 'github_cred')]) {
              sh "git remote set-url origin https://github.com/KA-SURFY/argocd"
              sh "git push -u origin master"
            }
          }
        }
    }
}
