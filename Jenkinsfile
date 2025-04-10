pipeline {
    agent any
    environment {
        SONARQUBE = 'sonarqube' // Name of your SonarQube server in Jenkins config
    }
    stages {
        stage('Build') {
            steps {
                script {
                    sh './gradlew build'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
                        sh './gradlew sonarqube -Dsonar.projectKey=gradle2' // Use the correct project key
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Build and SonarQube analysis completed successfully.'
        }
        failure {
            echo 'There was an issue with the build or SonarQube analysis.'
        }
    }
}
