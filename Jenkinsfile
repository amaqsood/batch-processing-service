pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven_3.9.6"
    }

    stages {
        stage('Build Maven') {
            steps {
                // Get some code from a GitHub repository
               checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/amaqsood/batch-processing-service']])

                // Run Maven on a Unix agent.
                //sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                //bat "mvn clean install"
                 bat "mvn -Dmaven.test.failure.ignore=true clean install"
            }
        }
        stage('Build Docker'){
            steps {
                script{
                    bat 'docker build -t amaqsood/batch-processing-service .'
                }
            }

        }
        stage('Push image to hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'jenkins-docker-pwd-id', variable: 'docker-image-push-id')]) {
                        // some block
                        bat 'docker login -u amaqsood -p ${docker-image-push-id}'
                        bat 'docker push amaqsood/batch-processing-service'
                    }
                }
            }
        }
    }
}
