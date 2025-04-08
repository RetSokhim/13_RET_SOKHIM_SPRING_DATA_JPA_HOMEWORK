pipeline {
    agent any

    tools {
        gradle 'gradle-8.13'  // Use exact Gradle installation name configured in Jenkins
        jdk 'jdk-21'          // Use exact JDK installation name configured in Jenkins
    }

    environment {
        SONAR_HOST_URL = "http://localhost:9000"
        SONAR_TOKEN = credentials('gradle-token')  // Use credential ID from Jenkins
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scm
                echo 'Git Checkout Completed'
            }
        }

        stage('Build & SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh """
                        ./gradlew clean build sonarqube \
                            -Dsonar.projectKey=gradle \
                            -Dsonar.projectName="Spring_data_JPA_homework" \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=${SONAR_TOKEN} \
                            -Dsonar.java.jdkHome=${JAVA_HOME}
                    """
                }
                echo 'SonarQube Analysis Completed'
            }
        }
    }

    post {
        always {
            cleanWs()  // Clean workspace after build
        }
    }
}