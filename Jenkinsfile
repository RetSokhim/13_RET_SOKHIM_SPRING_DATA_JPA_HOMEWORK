pipeline {
    agent any

    tools {
        gradle 'Gradle'
        jdk 'JDK 21'
    }

    environment {
        SONAR_HOST_URL = "http://localhost:9000" // ✅ FIXED: Use correct URL
        SONAR_LOGIN = "sqp_3718835b2bf31c52c01ba7f84724d77cf9e1b997"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo '✅ Code checkout complete'
            }
        }

        stage('Build, Test, and Analyze') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    script {
                        sh """
                            ./gradlew clean test jacocoTestReport sonar \
                                -Dsonar.projectKey=gradle2 \
                                -Dsonar.projectName=gradle2 \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_LOGIN} \
                                -Dsonar.coverage.jacoco.xmlReportPaths=build/reports/jacoco/test/jacocoTestReport.xml
                        """
                    }
                    echo '✅ Build and analysis complete'
                }
            }
        }
    }
}
