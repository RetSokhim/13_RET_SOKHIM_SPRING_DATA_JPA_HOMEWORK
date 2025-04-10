pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Ensure Gradle tool is correctly configured in Jenkins
        jdk 'JDK 21'         // Ensure JDK 21 is correctly configured in Jenkins
    }

    environment {
        SONAR_HOST_URL = "http://localhost:9000"   // Ensure SonarQube is running on this URL
        SONAR_LOGIN = "sqp_3718835b2bf31c52c01ba7f84724d77cf9e1b997" // Your SonarQube token
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
                withSonarQubeEnv('sonarqube') {  // Ensure SonarQube is configured in Jenkins
                    withCredentials([string(credentialsId: 'gradle2', variable: 'GRADLE_TOKEN')]) {
                        script {
                            // Run the Gradle build and SonarQube analysis
                            sh """
                                ./gradlew clean build -x test \
                                    -Dsonar.projectKey=gradle2 \
                                    -Dsonar.projectName="gradle2" \
                                    -Dsonar.host.url=${SONAR_HOST_URL} \
                                    -Dsonar.login=${SONAR_LOGIN}
                            """
                        }
                        echo 'SonarQube Analysis Completed'
                    }
                }
            }
        }
    }
}
