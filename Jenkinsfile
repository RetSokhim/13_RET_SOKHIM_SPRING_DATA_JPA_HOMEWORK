pipeline {
    agent any
    environment {
        SONARQUBE = 'sonarqube' // Name of your SonarQube server in Jenkins config
        SONAR_TOKEN = credentials('gradle2') // Fetch the token from Jenkins credentials
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
                        sh './gradlew sonar -Dsonar.projectKey=gradle2 -Dsonar.token=$SONAR_TOKEN' // Use the token from environment
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
