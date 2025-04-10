pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Ensure Gradle tool is configured in Jenkins
        jdk 'JDK 21'         // Ensure JDK 21 is configured in Jenkins
    }

    environment {
        SONAR_HOST_URL = "http://localhost:9000"   // Your local SonarQube instance
        SONAR_LOGIN = "sqp_3718835b2bf31c52c01ba7f84724d77cf9e1b997" // Your token
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scm
                echo '✅ Git Checkout Completed'
            }
        }

        stage('Build, Test, and SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {  // This must match your Jenkins SonarQube config name
                    script {
                        sh """
                            ./gradlew clean test jacocoTestReport sonarqube \
                                -Dsonar.projectKey=gradle2 \
                                -Dsonar.projectName="gradle2" \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_LOGIN} \
                                -Dsonar.coverage.jacoco.xmlReportPaths=build/reports/jacoco/test/jacocoTestReport.xml
                        """
                    }
                    echo '✅ SonarQube Analysis Completed'
                }
            }
        }
    }
}
