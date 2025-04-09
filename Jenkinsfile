pipeline {
    agent any

    tools {
        gradle 'Gradle'      // Make sure this matches the Gradle tool name in Jenkins
        jdk 'JDK 21'         // Make sure this matches your installed JDK
    }

    environment {
        SONAR_PROJECT_KEY = "gradle"
        SONAR_PROJECT_NAME = "Spring_data_JPA_homework"
        SONAR_HOST_URL = "http://localhost:9000"
        SONAR_LOGIN = "sqp_cdac00549725385bd13521fc85a2184c6de6c6a1"
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scm
                echo '✅ Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Connect to configured SonarQube server (name must match Jenkins config)
                withSonarQubeEnv('sonarqube') {
                    script {
                        // Run the Gradle sonar task with SonarQube details
                        sh """
                            ./gradlew clean build -x test sonar  \\
                                -Dsonar.projectKey=${SONAR_PROJECT_KEY} \\
                                -Dsonar.projectName="${SONAR_PROJECT_NAME}" \\
                                -Dsonar.host.url=${SONAR_HOST_URL} \\
                                -Dsonar.login=${SONAR_LOGIN}
                        """
                    }
                }
            }
        }
    }
}
