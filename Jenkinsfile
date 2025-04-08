pipeline {
    agent any
    environment {
        SONAR_HOST_URL = "http://localhost:9000"
        // Use credential ID that exists in your Jenkins
        SONAR_TOKEN = credentials('gradle-token')
    }

    stages {
        stage('Setup Environment') {
            steps {
                script {
                    // Verify available tools (debugging)
                    sh 'gradle --version || echo "Gradle not found in PATH"'
                    sh 'java -version || echo "Java not found in PATH"'
                }
            }
        }

        stage('Git Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Analyze') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh """
                        # Use gradle wrapper if available
                        ./gradlew clean build sonarqube \
                            -Dsonar.projectKey=gradle \
                            -Dsonar.projectName="Spring_data_JPA_homework" \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=${SONAR_TOKEN} \
                            --no-daemon
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}