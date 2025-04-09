pipeline {
    agent any

    tools {
        gradle 'Gradle'
        jdk 'JDK 21'
    }

    environment {
        SONAR_HOST_URL = "http://localhost:9000"
        SONAR_LOGIN = credentials('sonarqube-token') // 🔒 Store your token in Jenkins Credentials
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scm
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    script {
                        sh '''./gradlew sonar -x test \
                          -Dsonar.projectKey=gradle \
                          -Dsonar.projectName="Spring_data_JPA_homework" \
                          -Dsonar.host.url=$SONAR_HOST_URL \
                          -Dsonar.login=$SONAR_LOGIN'''
                    }
                }
            }
        }

        stage('Wait for Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Project') {
            steps {
                echo 'SonarQube passed! Now building the project...'
                sh './gradlew clean build -x test'
            }
        }
    }
}
