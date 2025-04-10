pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Make sure "Gradle" is defined in Jenkins global tools
        jdk 'JDK 21'         // Make sure "JDK 21" is defined in Jenkins global tools
    }

    environment {
        SONAR_HOST_URL = "http://sonarqube-202511104738-sonarqube-1:9000"
        SONAR_TOKEN = "sqp_3718835b2bf31c52c01ba7f84724d77cf9e1b997"
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scm
                echo '✅ Git Checkout Completed'
            }
        }

        stage('Test & SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    script {
                        sh """
                            ./gradlew clean test jacocoTestReport sonar \\
                                -Dsonar.projectKey=gradle2 \\
                                -Dsonar.projectName="gradle2" \\
                                -Dsonar.host.url=${SONAR_HOST_URL} \\
                                -Dsonar.token=${SONAR_TOKEN} \\
                                -Dsonar.coverage.jacoco.xmlReportPaths=build/reports/jacoco/test/jacocoTestReport.xml
                        """
                    }
                    echo '✅ SonarQube Analysis Completed'
                }
            }
        }
    }
}
